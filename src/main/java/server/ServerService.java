package server;


import message.MyMessage;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import static server.MainServer.serverServicesList;


public class ServerService extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ServerService.class.getName());

    MyMessage myMessage = new MyMessage(12, "terefere");
    private String login = "";

    private final Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    BufferedReader reader;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;


    ServerService(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.objectOutputStream = new ObjectOutputStream(this.outputStream);
        this.objectInputStream = new ObjectInputStream(this.inputStream);

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


        String sender;

        while (true) {
            MyMessage myMessage = (MyMessage) objectInputStream.readObject();
            sender = myMessage.getSender();
            loginCheck(sender);
            ServerService service = findReceiver(myMessage.getReceiver());
            service.sendMessage(myMessage);



        }



    }

    private void sendMessage(MyMessage myMessage) throws IOException {
        LOGGER.log(Level.INFO, "send message function");
        System.out.println(myMessage.getMessage());
        for (ServerService service : serverServicesList) {
            if (service.getLogin().equals(myMessage.getReceiver())) {

                objectOutputStream.writeObject(myMessage);

                LOGGER.log(Level.INFO, "send message function - done");
            }
        }


    }

    private ServerService findReceiver(String name) throws IOException {
        for (ServerService service : serverServicesList) {
            if (name.equals(myMessage.getReceiver())) {
                ServerService serverService = service;
                return serverService;
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

