package server;

import message.MyMessage;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientHandler extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());
    private final Consumer<MyMessage> consumer;
    private final Predicate<String> predicate;
    private String login = "";
    private final Socket clientSocket;
    private OutputStream outputStream;
    ObjectOutputStream objectOutputStream;


    ClientHandler(Socket clientSocket, Consumer<MyMessage> consumer, Predicate<String> predicate) throws IOException {
        this.clientSocket = clientSocket;
        this.consumer = consumer;
        this.predicate = predicate;

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
        this.objectOutputStream = new ObjectOutputStream(outputStream);
        consumer.accept(myMessage);


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
            LOGGER.log(Level.INFO, "Receiver exists");
            sendMessage(message);
        } else {
            LOGGER.log(Level.INFO, "Receiver not exist");
        }
    }

    private boolean existFunction(String name) {
        return predicate.test(name);
    }

    public String getLogin() {
        return login;
    }

    private void setLogin(String login) {
        this.login = login;
    }
}

