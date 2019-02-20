package Packets;

import java.io.Serializable;

public class BombPacket implements Serializable {
    private int x;
    private int y;
    private int bomberManID;

    public BombPacket(int x, int y, int bomberManID) {
        this.x = x;
        this.y = y;
        this.bomberManID = bomberManID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBomberManID() {
        return bomberManID;
    }
}
