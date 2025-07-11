package server;

import java.net.ServerSocket;
import java.net.Socket;

public class RedisServer {
    private final String host;
    private final int port;

    public RedisServer(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port "+port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
