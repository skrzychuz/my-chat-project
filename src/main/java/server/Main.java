package server;

public class Main {

    public static void main(String[] args) {
        int port = 8885;
        Server server = new Server(port);
        server.listen();
    }
}

