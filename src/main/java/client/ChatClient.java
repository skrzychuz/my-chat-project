package client;

import message.MyMessage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ChatClient {

    public ChatClient(MyMessage message) {
        System.out.println("Establishing connection. Please wait ...");

        ObjectOutputStream objectOutputStream = null;
        try {
            Socket socket = new Socket("127.0.0.1", 8885);
            System.out.println("Connected: " + socket);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
            while (true) {

            }

        } catch (UnknownHostException uhe) {
            System.err.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.err.println("Unexpected exception: " + ioe.getMessage());
        }

    }

    public static void main(String args[]) {
        List<MyMessage> myMessageList = new ArrayList<>();
        MyMessage msg1 = new MyMessage(14, "John", "JackR", "terefere");
        MyMessage msg2 = new MyMessage(15, "JackR", "John", "ram pam pam");
        MyMessage msg3 = new MyMessage(16, "Stan", "John", "la la lala");
        myMessageList.add(msg1);
        myMessageList.add(msg2);
        myMessageList.add(msg3);

        new ChatClient(myMessageList.get(1));

    }


}