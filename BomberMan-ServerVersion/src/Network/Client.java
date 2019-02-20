package Network;

public class Client {
    private Server server;
    private SocketThread socketThread;
    private int id;
    private String username;
    private String password;
    private GameServer game;

    public Client(Server server, int id, SocketThread socketThread) {
        this.server = server;
        server.getClients().add(this);
        this.id = id;
        this.socketThread = socketThread;
    }

    public int getId() {
        return id;
    }

    public GameServer getGame() {
        return game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGame(GameServer game) {
        this.game = game;
    }

    public SocketThread getSocketThread() {
        return socketThread;
    }
}
