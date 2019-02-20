package Network;

import Packets.GameServerPacket;
import Packets.ServerDetailsPacket;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private String ip;
    private int port;
    private ListenThread listenThread;
    private List<GameServer> games;
    private List<SocketThread> clientsSockets;
    private List<Client> clients;
    private ServerDetailsPacket serverDetailsPacket;


    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
        games = new ArrayList<>();
        clients = new ArrayList<>();
    }

    public int getPort() {
        return port;
    }

    public void setListenThread(ListenThread listenThread) {
        this.listenThread = listenThread;
        this.clientsSockets = listenThread.getSocketThreads();
    }

    public List<Client> getClients() {
        return clients;
    }

    public void addGame(GameServer game) {
        games.add(game);
    }

    public List<GameServer> getGames() {
        return games;
    }

    public ServerDetailsPacket getServerDetailsPacket() {
        List<GameServerPacket> gameServerPackets = new ArrayList<>();
        for (GameServer gameServer : getGames()) {
            GameServerPacket gsp = new GameServerPacket(gameServer.getGameID(),
                    gameServer.getName(), gameServer.getMaxPlayerNumber(), gameServer.getPlayers(),
                    gameServer.getRows(), gameServer.getColumns(), gameServer.getLevelNumber());
            gameServerPackets.add(gsp);
        }
        ServerDetailsPacket sdp = new ServerDetailsPacket(gameServerPackets);
        return sdp;
    }
}
