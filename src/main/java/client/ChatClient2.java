package client;

import message.MyMessage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient2 {

    private Socket socket;
    private OutputStream serverOut;
    private InputStream serverIn;
    private BufferedReader bufferedIn;
    ObjectOutputStream objectOutputStream;

    public ChatClient2() {
        connect();
    }

    private boolean connect() {
        try {
            this.socket = new Socket("127.0.0.1", 8885);
            this.serverIn = socket.getInputStream();
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            return true;
        } catch (UnknownHostException uhe) {
            System.err.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.err.println("Unexpected exception: " + ioe.getMessage());
        }
        return false;
    }

    public static void main(String args[]) {
        ChatClient2 client = new ChatClient2();

    }
}