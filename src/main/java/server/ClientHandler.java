package server;

import message.MyMessage;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientHandler extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());
    private final Supplier<List<ClientHandler>> supllier;
    private String login = "";
    private final Socket clientSocket;
    private OutputStream outputStream;


    ClientHandler(Socket clientSocket, Supplier<List<ClientHandler>> supplier) {
        this.clientSocket = clientSocket;
        this.supllier = supplier;

    }

    @Override
    public void run() {
        try {
            clientHandling();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clientHandling() throws IOException, ClassNotFoundException {
        this.outputStream = clientSocket.getOutputStream();
        InputStream inputStream = clientSocket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        while (clientSocket.isConnected()) {
            MyMessage myMessage = (MyMessage) objectInputStream.readObject();
            loginCheck(myMessage.getSender());
            receiverCheck(myMessage);
        }
        clientSocket.close();
    }

    private void sendMessage(MyMessage myMessage) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        LOGGER.log(Level.INFO, "send message from " + this.login + " to " + myMessage.getReceiver());

        for (ClientHandler service : supllier.get()) {
            if (service.getLogin().equals(myMessage.getReceiver())) {
                objectOutputStream.writeObject(myMessage);
                LOGGER.log(Level.INFO, "message sent to " + service.login);
            }
        }
    }

    private void loginCheck(String sender) {
        if (existFunction(sender)) {
            LOGGER.log(Level.INFO, "User exists");

        } else {
            setLogin(sender);
            LOGGER.log(Level.INFO, "User logged");

        }
    }

    private void receiverCheck(MyMessage message) throws IOException {
        if (existFunction(message.getReceiver())) {
            LOGGER.log(Level.INFO, "User exists");
            sendMessage(message);
        } else {
            LOGGER.log(Level.INFO, "Receiver not exist");
        }
    }

    private boolean existFunction(String name) {

        for (ClientHandler service : supllier.get()) {
            if (service.getLogin().equals(name))
                return true;
        }
        return false;
    }

    private String getLogin() {
        return login;
    }

    private void setLogin(String login) {
        this.login = login;
    }
}

