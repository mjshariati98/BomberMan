package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ListenThread extends Thread {
    private Server server;
    private int serverPort;
    private ServerSocket serverSocket;
    private List<SocketThread> socketThreads;

    private int id = 1;

    public ListenThread(Server server) {
        this.server = server;
        this.serverPort = server.getPort();
        socketThreads = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!isInterrupted() && !serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                SocketThread socketThread = new SocketThread(id++, clientSocket, server);
                socketThreads.add(socketThread);
                socketThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (SocketThread thread : socketThreads) {
            thread.interrupt();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SocketThread> getSocketThreads() {
        return socketThreads;
    }
}
