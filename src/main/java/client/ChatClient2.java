package client;

import message.MyMessage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient2 {

    public ChatClient2() {
        System.out.println("Establishing connection. Please wait ...");
        ObjectOutputStream objectOutputStream = null;
        try {
            Socket socket = new Socket("127.0.0.1", 8885);
            System.out.println("Connected: " + socket);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        } catch (UnknownHostException uhe) {
            System.err.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.err.println("Unexpected exception: " + ioe.getMessage());
        }

        try {

            MyMessage myMessage = new MyMessage(14, "Johnyy", "John", "ititittititi");
            objectOutputStream.writeObject(myMessage);
            while (true) {

            }


        } catch (IOException ioe) {
            System.out.println("Sending error: " + ioe.getMessage());
        }

    }

    public static void main(String args[]) {
        ChatClient2 client = new ChatClient2();

    }
}