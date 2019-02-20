package Packets;

import java.io.Serializable;

public class MessagePacket implements Serializable {
    private int id;
    private String name;
    private String Message;

    public MessagePacket(int id, String name, String message) {
        this.id = id;
        this.name = name;
        Message = message;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return Message;
    }
}
