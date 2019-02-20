package Models;

import java.awt.*;

public abstract class Item {
    private Cell cell;
    private Image image;
    private BomberMan bomberMan;
    private String type;

    public Item(Cell cell, BomberMan bomberMan) {
        this.cell = cell;
        this.bomberMan = bomberMan;
        if (cell != null)
            cell.setItem(this);
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public BomberMan getBomberMan() {
        return bomberMan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void paint(Graphics2D g2) {
        g2.drawImage(getImage(),
                getCell().getX(), getCell().getY(),
                new Color(0, 0, 0, 0), null);
    }

    public abstract void act();

    @Override
    public String toString() {
        return getType();
    }
}
