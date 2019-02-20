package Network;

import Models.Bomb;
import Models.BomberMan;
import Packets.*;
import utils.Direction;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SocketThread extends Thread {
    private Socket clientSocket;
    private Server server;
    private boolean isGaming;
    private boolean isViewing;
    private Client client;
    private int gameID;
    private BomberMan bomberMan;
    private GameServer game;
    private List<Bomb> newBombs;
    private long lastMessagesUpdate;
    private boolean newLevel;

    private BufferedOutputStream bufferedOutputStream;
    private BufferedInputStream bufferedInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public SocketThread(int id, Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        client = new Client(server, id, this);

        this.newBombs = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            bufferedOutputStream = new BufferedOutputStream(this.clientSocket.getOutputStream());
            bufferedInputStream = new BufferedInputStream(this.clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
            objectOutputStream.write(client.getId());
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(bufferedInputStream);
            client.setUsername((String) objectInputStream.readObject());

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        while (!isInterrupted() && clientSocket.isConnected()) {
            try {
                objectOutputStream.writeObject(server.getServerDetailsPacket());
                objectOutputStream.flush();

                RequestPacket requestPacket = (RequestPacket) objectInputStream.readObject();
                if (requestPacket.getRequestID() != RequestPacket.requestID.NONE) {
                    switch (requestPacket.getRequestID()) {
                        case PLAY:
                            gameID = requestPacket.getPlayGameID();
                            game = server.getGames().get(gameID);
                            if (game.getMaxPlayerNumber() > game.getPlayers()) {
                                objectOutputStream.write(1);
                                game.getClientsSocketThreads().add(this);
                                client.setGame(game);
                                lastMessagesUpdate = new Date().getTime();
                                isGaming = true;
                                this.bomberMan = server.getGames().get(requestPacket.getPlayGameID()).addBomberMan(client.getUsername());
                                objectOutputStream.write(bomberMan.getId());
                                MapPacket firstMapPacket = new MapPacket(game.getMap(), false);
                                objectOutputStream.writeObject(firstMapPacket);
                                objectOutputStream.flush();

                                while (isGaming) {
                                    runGame();
                                    if (isGaming == false) {
                                        // to client understand that game was removed
                                        objectOutputStream.writeObject(null);
                                        objectOutputStream.flush();
                                    }
                                }
                            } else {
                                objectOutputStream.write(0);
                                objectOutputStream.flush();
                            }

                            break;
                        case VIEW:
                            isViewing = true;

                            gameID = requestPacket.getViewGameID();
                            game = server.getGames().get(gameID);
                            game.getClientsSocketThreads().add(this);
                            client.setGame(game);
                            lastMessagesUpdate = new Date().getTime();
                            MapPacket firstMapPacket = new MapPacket(game.getMap(), false);
                            objectOutputStream.writeObject(firstMapPacket);
                            objectOutputStream.flush();

                            while (isViewing) {
                                runView();
                                if (isViewing == false) {
                                    // to client understand that game was removed
                                    objectOutputStream.writeObject(null);
                                    objectOutputStream.flush();
                                }
                            }

                            break;
                        case NEWGAME:
                            GamePacket gamePacket = requestPacket.getNewGamePacket();
                            int gameID = server.getGames().size();
                            String name = gamePacket.getName();
                            int maxPlayer = gamePacket.getMaxPlayerNumber();
                            int rows = gamePacket.getRows();
                            int columns = gamePacket.getColumns();
                            int bombLimit = gamePacket.getBombLimit();
                            int bombRange = gamePacket.getBombRange();
                            boolean controlBomb = gamePacket.isControlBomb();
                            int speed = gamePacket.getSpeed();
                            int topInset = gamePacket.getTopInset();

                            GameServer gameServer = new GameServer(gameID, name, maxPlayer,
                                    rows, columns, bombLimit, bombRange, controlBomb,
                                    speed, topInset);
                            server.addGame(gameServer);
                            break;
                    }
                }

                Thread.sleep(400);
            } catch (Exception e) {
                if (game != null) {
                    game.getBomberMans().remove(this.bomberMan);
                    game.getClientsSocketThreads().remove(this);
                }
                server.getClients().remove(this.client);

                break;
            }
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Client getClient() {
        return client;
    }

    public List<Bomb> getNewBombs() {
        return newBombs;
    }

    public void setNewLevel(boolean newLevel) {
        this.newLevel = newLevel;
    }

    public void setGaming(boolean gaming) {
        isGaming = gaming;
    }

    public void setViewing(boolean viewing) {
        isViewing = viewing;
    }

    private void runGame() {
        try {
            MapPacket mapPacket = new MapPacket(game.getMap(), false);
            if (newLevel) {
                mapPacket = new MapPacket(game.getMap(), true);
                newLevel = false;
            }

            objectOutputStream.reset();
            objectOutputStream.writeObject(mapPacket);
            objectOutputStream.flush();


            // sendBombs
            List<BombPacket> bombPackets = new ArrayList<>();
            for (Bomb bomb : getNewBombs()) {
                BombPacket bombPacket = new BombPacket(bomb.getX(), bomb.getY(), bomb.getBomberMan().getId());
                bombPackets.add(bombPacket);
            }
            objectOutputStream.reset();
            objectOutputStream.writeObject(bombPackets);
            objectOutputStream.flush();
            getNewBombs().clear();


            // get Activity packet
            ActivityPacket activityPacket = (ActivityPacket) objectInputStream.readObject();
            if (!this.bomberMan.isFailed()) {
                switch (activityPacket.getActivity()) {
                    case LEFT:
                        this.bomberMan.move(Direction.LEFT);
                        break;
                    case UP:
                        this.bomberMan.move(Direction.UP);
                        break;
                    case DOWN:
                        this.bomberMan.move(Direction.DOWN);
                        break;
                    case RIGHT:
                        this.bomberMan.move(Direction.RIGHT);
                        break;
                    case BOMB:
                        if (this.bomberMan.getBombLimit() > this.bomberMan.getBobms().size()) {
                            Bomb bomb = new Bomb(this.bomberMan, game.getMap());
                            this.bomberMan.getBobms().add(bomb);
                            game.getMap().addBomb(bomb);
                        }
                        break;
                    case EXPLODE:
                        if (this.bomberMan.isBombControl() && this.bomberMan.getBobms().size() > 0)
                            this.bomberMan.getBobms().get(0).setExploded(true);
                        break;
                    case NONE:
                        break;
                }
            }

            game.getMap().getGamePanel().repaint();


            // get Message from Client
            List<MessagePacket> messagePackets = (List<MessagePacket>) objectInputStream.readObject();
            game.getMessagePackets().addAll(messagePackets);
            if (game.getChatRoom() != null && new Date().getTime() - lastMessagesUpdate > 600) {
                game.getChatRoom().update();
                lastMessagesUpdate = new Date().getTime();
            }


            // send Messages to Client
            objectOutputStream.reset();
            List<MessagePacket> messages = new ArrayList<>(game.getMessagePackets());
            objectOutputStream.writeObject(messages);
            objectOutputStream.flush();

            // pointFrame
            if (game.getPointsFrame().isVisible()) {
                game.getPointsFrame().update(game.getBomberMans());
            }

            // check client exited
            if (objectInputStream.read() == 255) {
                isGaming = false;
                game.getClientsSocketThreads().remove(this);
                game.getBomberMans().remove(this.bomberMan);
            }
            Thread.sleep(10);
        } catch (Exception e) {
            isGaming = false;
            game.getClientsSocketThreads().remove(this);
            game.getBomberMans().remove(this.bomberMan);
        }
    }

    private void runView() {
        try {
            MapPacket mapPacket = new MapPacket(game.getMap(), false);
            if (newLevel) {
                mapPacket = new MapPacket(game.getMap(), true);
                newLevel = false;
            }

            objectOutputStream.reset();
            objectOutputStream.writeObject(mapPacket);
            objectOutputStream.flush();


            // sendBombs
            List<BombPacket> bombPackets = new ArrayList<>();
            for (Bomb bomb : getNewBombs()) {
                BombPacket bombPacket = new BombPacket(bomb.getX(), bomb.getY(), bomb.getBomberMan().getId());
                bombPackets.add(bombPacket);
            }
            objectOutputStream.reset();
            objectOutputStream.writeObject(bombPackets);
            objectOutputStream.flush();
            getNewBombs().clear();


            // get Message from Client
            List<MessagePacket> messagePackets = (List<MessagePacket>) objectInputStream.readObject();
            game.getMessagePackets().addAll(messagePackets);
            if (game.getChatRoom() != null && new Date().getTime() - lastMessagesUpdate > 600) {
                game.getChatRoom().update();
                lastMessagesUpdate = new Date().getTime();
            }


            // send Messages to Client
            objectOutputStream.reset();
            List<MessagePacket> messages = new ArrayList<>(game.getMessagePackets());
            objectOutputStream.writeObject(messages);
            objectOutputStream.flush();


            // pointFrame
            if (game.getPointsFrame().isVisible()) {
                game.getPointsFrame().update(game.getBomberMans());
            }

            // check client exited
            if (objectInputStream.read() == 255) {
                isViewing = false;
                game.getClientsSocketThreads().remove(this);
            }

            Thread.sleep(10);
        } catch (Exception e) {
            isViewing = false;
        }
    }
}

