package server;


import message.MyMessage;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import static server.MainServer.serverServicesList;


public class ServerService extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ServerService.class.getName());

    //    MyMessage myMessage = new MyMessage(12, "terefere");
    private String login = "";

    private final Socket clientSocket;

    //    private InputStream inputStream;
    private OutputStream outputStream;

    BufferedReader reader;
    ObjectOutputStream objectOutputStream;


    ServerService(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;

//        this.reader = new BufferedReader(new InputStreamReader(inputStream));
//        this.objectOutputStream = new ObjectOutputStream(this.outputStream);

    }


    @Override
    public void run() {
        try {
            clientHandling();
        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clientHandling() throws IOException, InterruptedException, ClassNotFoundException {
        this.outputStream = clientSocket.getOutputStream();
        InputStream inputStream = clientSocket.getInputStream();

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        String sender;

        while (clientSocket.isConnected()) {
            MyMessage myMessage = (MyMessage) objectInputStream.readObject();
            sender = myMessage.getSender();
            loginCheck(sender);
            ServerService service = findReceiver(myMessage.getReceiver());
            if (service != null)
                service.sendMessage(myMessage);


        }

        clientSocket.close();

    }

    private void sendMessage(MyMessage myMessage) throws IOException {
        LOGGER.log(Level.INFO, "send message function " + this.login);

        for (ServerService service : serverServicesList) {
            if (service.getLogin().equals(myMessage.getReceiver())) {
                LOGGER.log(Level.INFO, "message object check " + myMessage);
                objectOutputStream.writeObject(myMessage);

                LOGGER.log(Level.INFO, "send message function - done");
            }
        }


    }

    private ServerService findReceiver(String name) throws IOException {
        for (ServerService service : serverServicesList) {
            if ((service.getLogin().equals(name))) {
                return service;

            }
        }
        LOGGER.log(Level.WARNING, "Service Receiver not exist");
        return null;
    }


    private void loginCheck(String sender) throws IOException {
        if (existFunction(sender)) {
            LOGGER.log(Level.INFO, "User exists");

        } else {
            setLogin(sender);
            serverServicesList.add(this);
            LOGGER.log(Level.INFO, "User logged");

        }
    }


    private boolean existFunction(String name) {
        for (ServerService service : serverServicesList) {
            if (service.getLogin().equals(name))
                return true;
        }
        return false;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

