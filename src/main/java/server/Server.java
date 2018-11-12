package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

class Server {


    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private ArrayList<ServerService> serverServicesList = new ArrayList<>();
    private final int serverPort;
    Supplier<List<ServerService>> supplier;


    public Server(int port) {
        this.serverPort = port;
    }


    public void listen() {

        supplier = () -> serverServicesList;

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);


            while (true) {
                LOGGER.log(Level.INFO, "Waiting for client...");
                Socket clientSocket = serverSocket.accept();
                LOGGER.log(Level.INFO, "Client accepted");
                ServerService serverService = new ServerService(clientSocket, supplier);
                serverServicesList.add(serverService);


                serverService.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}