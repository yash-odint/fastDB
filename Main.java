import server.RedisServer;

public class Main {
    public static void main(String[] args) {
        RedisServer server = new RedisServer("127.0.0.1", 6379);
        server.start();
    }
}
