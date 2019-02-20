package Models;

import java.io.Serializable;

public class Cell implements Serializable {
    private int x;
    private int y;
    private CellTypes type;
    private transient Item item = null;
    private transient Bomb bomb = null;

    public Cell(int x, int y, CellTypes type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public enum CellTypes {
        EMPTY, WALL, BLOCK, BOMB
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public CellTypes getType() {
        return type;
    }

    public void setType(CellTypes type) {
        this.type = type;
    }

    public void setType(CellTypes type, Bomb bomb) {
        this.type = type;
        this.bomb = bomb;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }
}
