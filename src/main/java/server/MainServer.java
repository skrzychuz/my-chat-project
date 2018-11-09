package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainServer {
    private static final Logger LOGGER = Logger.getLogger(MainServer.class.getName());

    private static ArrayList<ServerService> serverServicesList = new ArrayList<>();

    public static List<ServerService> getserverServicesList() {
        return serverServicesList;
    }

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8885);

            while (true) {
                LOGGER.log(Level.INFO, "Waiting for client...");
                Socket clientSocket = serverSocket.accept();
                LOGGER.log(Level.INFO, "Client accepted");
                ServerService serverService = new ServerService(clientSocket);
                serverServicesList.add(serverService);
                serverService.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}