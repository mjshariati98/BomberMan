package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

public class DecreaseScore extends Item {
    public DecreaseScore(Cell cell, BomberMan bomberMan) {
        super(cell, bomberMan);
        setImage(Images.decreaseScore);
        setType("Decrease Score");
    }

    @Override
    public void act() {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.OOO.play(0);
            getBomberMan().setScore(getBomberMan().getScore() - 100);
            getCell().setItem(null);
        }
    }
}
