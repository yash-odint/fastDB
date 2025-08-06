package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class FastDBServer {
    private static final Logger logger = Logger.getLogger(FastDBServer.class.getName());

    private final String host;
    private final int port;

    public FastDBServer(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started on port "+port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
