package message;

import java.io.Serializable;
import java.util.Date;

public class MyMessage implements Serializable {

    int id;
    Date date;
    String sender;
    String receiver;
    String message;

    public MyMessage() {
    }

    public MyMessage(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public MyMessage(String message) {
        this.message = message;
    }

    public MyMessage(int id, String sender, String receiver, String message) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
