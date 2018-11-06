package client;

import message.MyMessage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient1 {

    public ChatClient1() {
        System.out.println("Establishing connection. Please wait ...");
        DataInputStream console = null;
        OutputStream streamOut = null;
        BufferedReader reader = null;
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            Socket socket = new Socket("127.0.0.1", 8885);
            System.out.println("Connected: " + socket);

            InputStream inputStream = socket.getInputStream();

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        } catch (UnknownHostException uhe) {
            System.err.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.err.println("Unexpected exception: " + ioe.getMessage());
        }
        String line = "";
        String answer = "";


        try {
//                line = console.readLine();
//                streamOut.write((line + "\n").getBytes());
//                streamOut.flush();
            MyMessage myMessage = new MyMessage(14, "JackR", "John", "ram pam pam");
            objectOutputStream.writeObject(myMessage);
            while (true) {

            }


        } catch (IOException ioe) {
            System.out.println("Sending error: " + ioe.getMessage());
        }

    }

    public static void main(String args[]) {
        ChatClient1 client = new ChatClient1();

    }
}