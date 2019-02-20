package Packets;

import java.io.Serializable;

public class GamePacket implements Serializable {
    private String name;
    private int maxPlayerNumber;
    private int rows;
    private int columns;
    private int bombLimit;
    private int bombRange;
    private boolean controlBomb;
    private int speed;
    private int topInset;

    public GamePacket(String name, int maxPlayerNumber, int rows, int columns,
                      int bombLimit, int bombRange, boolean controlBomb, int speed, int topInset) {
        this.name = name;
        this.maxPlayerNumber = maxPlayerNumber;
        this.rows = rows;
        this.columns = columns;
        this.bombLimit = bombLimit;
        this.bombRange = bombRange;
        this.controlBomb = controlBomb;
        this.speed = speed;
        this.topInset = topInset;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public int getBombRange() {
        return bombRange;
    }

    public boolean isControlBomb() {
        return controlBomb;
    }

    public int getSpeed() {
        return speed;
    }

    public int getTopInset() {
        return topInset;
    }
}
