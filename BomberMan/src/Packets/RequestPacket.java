package Packets;

import java.io.Serializable;

public class RequestPacket implements Serializable {
    private requestID requestID;
    private GamePacket newGamePacket;
    private int viewGameID;
    private int playGameID;

    public RequestPacket(requestID requestID) {
        this.requestID = requestID;
    }

    public enum requestID{
        NONE, NEWGAME, VIEW, PLAY;
    }

    public requestID getRequestID() {
        return requestID;
    }

    public GamePacket getNewGamePacket() {
        return newGamePacket;
    }

    public int getViewGameID() {
        return viewGameID;
    }

    public int getPlayGameID() {
        return playGameID;
    }

    public void setNewGamePacket(GamePacket newGamePacket) {
        this.newGamePacket = newGamePacket;
    }

    public void setViewGameID(int viewGameID) {
        this.viewGameID = viewGameID;
    }

    public void setPlayGameID(int playGameID) {
        this.playGameID = playGameID;
    }
}
