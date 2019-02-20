package Network;

import Configs.GameConfiguration;
import Models.*;
import Packets.*;
import views.ChatRoom;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientThread extends Thread {
    private int id;
    private String username;
    private int bomberManID;
    private Socket clientSocket;
    private ClientFrame clientFrame;
    private BufferedOutputStream bufferedOutputStream;
    private BufferedInputStream bufferedInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private boolean isGaming;
    private boolean isViewing;
    private ServerDetailsPacket serverDetailsPacket;
    private LevelManager levelManager;
    private BomberMan bomberMan;
    private ChatRoom chatRoom;
    private long lastMessagesUpdate;


    public ClientThread(Socket clientSocket, ClientFrame clientFrame, String username) {
        this.clientSocket = clientSocket;
        this.clientFrame = clientFrame;
        this.username = username;
    }

    @Override
    public void run() {
        MapPacket firstMapPacket = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(clientSocket.getOutputStream());
            bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
            objectOutputStream.writeObject(username);
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(bufferedInputStream);
            id = objectInputStream.read();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


        while (!isInterrupted() && clientSocket.isConnected()) {
            try {
                ServerDetailsPacket serverDetailsPacket = (ServerDetailsPacket) objectInputStream.readObject();
                setServerDetailsPacket(serverDetailsPacket);

                objectOutputStream.writeObject(clientFrame.getRequestPacket());
                objectOutputStream.flush();

                switch (clientFrame.getRequestPacket().getRequestID()) {
                    case PLAY:
                        int checkMaxPlayer = objectInputStream.read();
                        if (checkMaxPlayer == 1) {
                            isGaming = true;
                            this.bomberManID = objectInputStream.read();

                            firstMapPacket = (MapPacket) objectInputStream.readObject();

                            levelManager = new LevelManager(firstMapPacket, bomberManID);
                            levelManager.getMap().setItems(firstMapPacket);
                            this.bomberMan = levelManager.getGameFrame().getBomberMan();

                            levelManager.getGameFrame().setVisible(true);
                            chatRoom = new ChatRoom(this, levelManager.getGameFrame().getInsets().top);
                            levelManager.getGameFrame().setChatRoom(chatRoom);
                            lastMessagesUpdate = new Date().getTime();
                            while (isGaming)
                                runGame();
                        }
                        break;
                    case VIEW:
                        isViewing = true;
                        firstMapPacket = (MapPacket) objectInputStream.readObject();
                        levelManager = new LevelManager(firstMapPacket);
                        levelManager.getMap().setItems(firstMapPacket);

                        levelManager.getGameFrame().setVisible(true);
                        chatRoom = new ChatRoom(this, levelManager.getGameFrame().getInsets().top);
                        levelManager.getGameFrame().setChatRoom(chatRoom);
                        lastMessagesUpdate = new Date().getTime();

                        while (isViewing) {
                            runView();
                        }
                        break;
                }
                clientFrame.resetRequestPacket();

                Thread.sleep(400);


            } catch (Exception e) {
//                e.printStackTrace();
                clientFrame.getClientPanel().setVisible(false);
                clientFrame.getNewGamePanel().setVisible(false);
                clientFrame.getRunningGamesPanel().setVisible(false);
                clientFrame.getIpPanel().setVisible(true);
                break;
            }
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public ServerDetailsPacket getServerDetailsPacket() {
        return serverDetailsPacket;
    }

    public void setServerDetailsPacket(ServerDetailsPacket serverDetailsPacket) {
        this.serverDetailsPacket = serverDetailsPacket;
    }

    private void runGame() {
        try {

            // Save previous Cell Types for Bomb Cells !
            Cell.CellTypes[][] previousCellTypes = new Cell.CellTypes[levelManager.getMap().getColumns()][levelManager.getMap().getRows()];
            for (int i = 0; i <levelManager.getMap().getColumns() ; i++) {
                for (int j = 0; j <levelManager.getMap().getRows() ; j++) {
                    previousCellTypes[i][j] = levelManager.getMap().getCells()[i][j].getType();
                }
            }

            // Get MapPacket ...
            MapPacket mapPacket = (MapPacket) objectInputStream.readObject();
            levelManager.getMap().setCells(mapPacket);
            levelManager.getMap().setItems(mapPacket);

            // check new level
            if (mapPacket.isNewLevel()) {
                levelManager.newMonsters(mapPacket);
                levelManager.getGameFrame().setTitle("Level " + mapPacket.getLevelNumber());
                BomberMan myBomberMan = levelManager.getGameFrame().getBomberMan();
                levelManager.getGameFrame().getFrontPanel()
                        .setBoundsPosition(myBomberMan.getX(), myBomberMan.getY());
            }


            // remove dead monsters
            List<Monster> deadMonsters = new ArrayList<>();
            if (levelManager.getMap().getMonsters().size() != mapPacket.getMonsterPackets().size()) {
                for (Monster monster : levelManager.getMap().getMonsters()) {
                    boolean isAlive = false;
                    for (MonsterPacket monsterPacket : mapPacket.getMonsterPackets()) {
                        if (monster.getId() == monsterPacket.getId()) {
                            isAlive = true;
                            break;
                        }
                    }
                    if (!isAlive) {
                        deadMonsters.add(monster);
                    }
                }
            }
            levelManager.getMap().getMonsters().removeAll(deadMonsters);


            //move monsters
            for (MonsterPacket monsterPacket : mapPacket.getMonsterPackets()) {
                for (Monster monster : levelManager.getMap().getMonsters()) {
                    if (monster.getId() == monsterPacket.getId()) {
                        monster.move(monsterPacket.getX(), monsterPacket.getY());
                        monster.setX(monsterPacket.getX());
                        monster.setY(monsterPacket.getY());
                    }
                }
            }


            // new added bombermans
            for (BombermanPacket bombermanPacket : mapPacket.getBombermanPackets()) {
                if (levelManager.getMap().getBomberManByID(bombermanPacket.getId()) == null) {
                    BomberMan newBomberMan =
                            new BomberMan(levelManager.getMap(), bombermanPacket.getId(),
                                    bombermanPacket.getName());
                    levelManager.getGameFrame().getOtherBomberMans().add(newBomberMan);
                }
            }


            List<BomberMan> allBomberMans = new ArrayList<>(levelManager.getGameFrame().getOtherBomberMans());
            allBomberMans.add(levelManager.getGameFrame().getBomberMan());

            // set all bomberMans details
            for (BombermanPacket bombermanPacket : mapPacket.getBombermanPackets()) {
                BomberMan bomberMan = levelManager.getMap().getBomberManByID(bombermanPacket.getId());

                bomberMan.setStep(bombermanPacket.getSpeed());
                bomberMan.setBombLimit(bombermanPacket.getBombLimit());
                bomberMan.setBombRange(bombermanPacket.getBombRange());
                bomberMan.setBombControl(bombermanPacket.isBombControl());
                bomberMan.setScore(bombermanPacket.getScore());
                bomberMan.setRespawning(bombermanPacket.isRespawning());
                bomberMan.setFailed(bombermanPacket.isFailed());
                bomberMan.setGhostAbility(bombermanPacket.isGhostAbility());
            }

            if (this.bomberMan.isRespawning()) {
                levelManager.getGameFrame().getFrontPanel().setBoundsPosition(
                        bomberMan.getX(), bomberMan.getY());
            }


            // move all bomberMans
            for (BombermanPacket bombermanPacket : mapPacket.getBombermanPackets()) {
                for (BomberMan bomberMan : allBomberMans) {
                    if (bomberMan.getId() == bombermanPacket.getId()) {
                        bomberMan.setDirection(bombermanPacket.getDirection());
                        bomberMan.move(bombermanPacket.getX(), bombermanPacket.getY());
                        break;
                    }
                }
            }


            // set position of all bomberMans
            for (BombermanPacket bombermanPacket : mapPacket.getBombermanPackets()) {
                for (BomberMan bomberMan : allBomberMans) {
                    if (bomberMan.getId() == bombermanPacket.getId()) {
                        bomberMan.setX(bombermanPacket.getX());
                        bomberMan.setY(bombermanPacket.getY());
                        break;
                    }
                }
            }


            // Bombs
            List<BombPacket> bombPackets = (List<BombPacket>) objectInputStream.readObject();
            for (BombPacket bombPacket : bombPackets) {
                Bomb bomb = new Bomb(levelManager.getMap().getBomberManByID(bombPacket.getBomberManID()),
                        bombPacket.getX(), bombPacket.getY(), levelManager.getMap());
                bomb.setPreviousCellType(previousCellTypes
                        [bomb.getX()/GameConfiguration.CELL_WIDTH][bomb.getY()/GameConfiguration.CELL_HEIGHT]);
            }

            levelManager.getGameFrame().getScoreBoard().update();
            levelManager.getGameFrame().getGamePanel().repaint();


            // Check Control Bomb
            for (BombermanPacket bomberManPacket : mapPacket.getBombermanPackets()) {
                for (BomberMan bomberMan : allBomberMans) {
                    if (bomberManPacket.getId() == bomberMan.getId() &&
                            bomberMan.getBobms().size() > bomberManPacket.getBombSize()) {
                        bomberMan.getBobms().get(0).setExploded(true);

                    }
                }
            }


            // send acivity packet
            objectOutputStream.reset();
            objectOutputStream.writeObject(levelManager.getGameFrame().getActivityPacket());
            objectOutputStream.flush();
            levelManager.getGameFrame().getActivityPacket().setActivity(ActivityPacket.Activity.NONE);


            // send Messages to server
            objectOutputStream.reset();
            objectOutputStream.writeObject(chatRoom.getSendingMessages());
            objectOutputStream.flush();
            chatRoom.getSendingMessages().clear();


            // get Messages from server
            List<MessagePacket> messagePackets = (List<MessagePacket>) objectInputStream.readObject();
            chatRoom.setMessagesPacketList(messagePackets);
            if (new Date().getTime() - lastMessagesUpdate > 600) {
                chatRoom.update();
                lastMessagesUpdate = new Date().getTime();
            }


            // check close the game
            if (!levelManager.getGameFrame().isVisible()) {
                isGaming = false;
                objectOutputStream.write(255);
                objectOutputStream.flush();
                objectInputStream.readObject();
            } else {
                objectOutputStream.write(1);
                objectOutputStream.flush();
            }

            Thread.sleep(10);

        } catch (Exception e) {
            // if game was removed from server
            isGaming = false;
            levelManager.getGameFrame().dispose();
        }
    }

    private void runView() {
        try {
            MapPacket mapPacket = (MapPacket) objectInputStream.readObject();
            levelManager.getMap().setCells(mapPacket);
            levelManager.getMap().setItems(mapPacket);


            // check new level
            if (mapPacket.isNewLevel()) {
                levelManager.newMonsters(mapPacket);
                levelManager.getGameFrame().setTitle("Level " + mapPacket.getLevelNumber());
            }


            // remove dead monsters
            List<Monster> deadMonsters = new ArrayList<>();
            if (levelManager.getMap().getMonsters().size() != mapPacket.getMonsterPackets().size()) {
                for (Monster monster : levelManager.getMap().getMonsters()) {
                    boolean isAlive = false;
                    for (MonsterPacket monsterPacket : mapPacket.getMonsterPackets()) {
                        if (monster.getId() == monsterPacket.getId()) {
                            isAlive = true;
                            break;
                        }
                    }
                    if (!isAlive) {
                        deadMonsters.add(monster);
                    }
                }
            }
            levelManager.getMap().getMonsters().removeAll(deadMonsters);


            //move monsters
            for (MonsterPacket monsterPacket : mapPacket.getMonsterPackets()) {
                for (Monster monster : levelManager.getMap().getMonsters()) {
                    if (monster.getId() == monsterPacket.getId()) {
                        monster.move(monsterPacket.getX(), monsterPacket.getY());
                        monster.setX(monsterPacket.getX());
                        monster.setY(monsterPacket.getY());
                    }
                }
            }


            // new added bombermans
            for (BombermanPacket bombermanPacket : mapPacket.getBombermanPackets()) {
                if (levelManager.getMap().getBomberManByID(bombermanPacket.getId()) == null) {
                    BomberMan newBomberMan =
                            new BomberMan(levelManager.getMap(), bombermanPacket.getId(),
                                    bombermanPacket.getName());
                    levelManager.getGameFrame().getOtherBomberMans().add(newBomberMan);
                }
            }


            List<BomberMan> allBomberMans = new ArrayList<>(levelManager.getGameFrame().getOtherBomberMans());

            // set all bomberMans details
            for (BombermanPacket bombermanPacket : mapPacket.getBombermanPackets()) {
                BomberMan bomberMan = levelManager.getMap().getBomberManByID(bombermanPacket.getId());

                bomberMan.setStep(bombermanPacket.getSpeed());
                bomberMan.setBombLimit(bombermanPacket.getBombLimit());
                bomberMan.setBombRange(bombermanPacket.getBombRange());
                bomberMan.setBombControl(bombermanPacket.isBombControl());
                bomberMan.setScore(bombermanPacket.getScore());
                bomberMan.setRespawning(bombermanPacket.isRespawning());
                bomberMan.setFailed(bombermanPacket.isFailed());
                bomberMan.setGhostAbility(bombermanPacket.isGhostAbility());
            }


            // move all bomberMans
            for (BombermanPacket bombermanPacket : mapPacket.getBombermanPackets()) {
                for (BomberMan bomberMan : allBomberMans) {
                    if (bomberMan.getId() == bombermanPacket.getId()) {
                        bomberMan.setDirection(bombermanPacket.getDirection());
                        bomberMan.move(bombermanPacket.getX(), bombermanPacket.getY());
                        break;
                    }
                }
            }


            // set position of all bomberMans
            for (BombermanPacket bombermanPacket : mapPacket.getBombermanPackets()) {
                for (BomberMan bomberMan : allBomberMans) {
                    if (bomberMan.getId() == bombermanPacket.getId()) {
                        bomberMan.setX(bombermanPacket.getX());
                        bomberMan.setY(bombermanPacket.getY());
                        break;
                    }
                }
            }


            // Bombs
            List<BombPacket> bombPackets = (List<BombPacket>) objectInputStream.readObject();
            for (BombPacket bombPacket : bombPackets) {
                Bomb bomb = new Bomb(levelManager.getMap().getBomberManByID(bombPacket.getBomberManID()),
                        bombPacket.getX(), bombPacket.getY(), levelManager.getMap());
            }

            levelManager.getGameFrame().getGamePanel().repaint();


            // Check Control Bomb
            for (BombermanPacket bomberManPacket : mapPacket.getBombermanPackets()) {
                for (BomberMan bomberMan : allBomberMans) {
                    if (bomberManPacket.getId() == bomberMan.getId() &&
                            bomberMan.getBobms().size() > bomberManPacket.getBombSize()) {
                        bomberMan.getBobms().get(0).setExploded(true);

                    }
                }
            }


            // send Messages to server
            objectOutputStream.reset();
            objectOutputStream.writeObject(chatRoom.getSendingMessages());
            objectOutputStream.flush();
            chatRoom.getSendingMessages().clear();


            // get Messages from server
            List<MessagePacket> messagePackets = (List<MessagePacket>) objectInputStream.readObject();
            chatRoom.setMessagesPacketList(messagePackets);
            if (new Date().getTime() - lastMessagesUpdate > 600) {
                chatRoom.update();
                lastMessagesUpdate = new Date().getTime();
            }


            // check close the game
            if (!levelManager.getGameFrame().isVisible()) {
                isViewing = false;
                objectOutputStream.write(255);
                objectOutputStream.flush();
                objectInputStream.readObject();
            } else {
                objectOutputStream.write(1);
                objectOutputStream.flush();
            }
            Thread.sleep(10);

        } catch (Exception e) {
            isViewing = false;
            levelManager.getGameFrame().dispose();
        }
    }
}
