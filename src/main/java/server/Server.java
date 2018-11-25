package server;

import message.MyMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

class Server {


    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private ArrayList<ClientHandler> serverServicesList = new ArrayList<>();
    private final int serverPort;

    Server(int port) {
        this.serverPort = port;
    }


    public void listen() {


        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);


            while (true) {
                LOGGER.log(Level.INFO, "Waiting for client...");
                Socket clientSocket = serverSocket.accept();
                LOGGER.log(Level.INFO, "Client accepted");
                ClientHandler serverService = new ClientHandler(clientSocket, consumer, predicate);
                serverServicesList.add(serverService);


                serverService.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Consumer<MyMessage> consumer = msg -> {
        for (ClientHandler service : serverServicesList) {
            if (service.getLogin().equals(msg.getReceiver())) {

                try {
                    service.objectOutputStream.writeObject(msg);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (NullPointerException npe) {
                    npe.getMessage();
                }

                LOGGER.log(Level.INFO, "message sent to " + service.getLogin());
            }
        }
    };

    private Predicate<String> predicate = name -> {
        for (ClientHandler service : serverServicesList) {
            if (service.getLogin().equals(name)) {
                return true;
            }
        }
        return false;
    };

}