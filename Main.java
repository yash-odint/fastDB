import server.FastDBServer;

public class Main {
    public static void main(String[] args) {
        FastDBServer server = new FastDBServer("127.0.0.1", 6379);
        server.start();
    }
}
