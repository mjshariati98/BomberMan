package Models;

import java.awt.*;
import java.util.List;

public abstract class Item {
    private Cell cell;
    private Image image;
    private List<BomberMan> bomberMans;
    private String type;

    public Item(Cell cell, List<BomberMan> bomberMans) {
        this.cell = cell;
        this.bomberMans = bomberMans;
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

    public List<BomberMan> getBomberMans() {
        return bomberMans;
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

    public abstract void act(BomberMan bomberMan);

    @Override
    public String toString() {
        return getType();
    }
}
