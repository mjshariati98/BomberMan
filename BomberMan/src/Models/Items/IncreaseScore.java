package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

public class IncreaseScore extends Item {
    public IncreaseScore(Cell cell, BomberMan bomberMan) {
        super(cell, bomberMan);
        setImage(Images.increaseScore);
        setType("Increase Score");
    }

    @Override
    public void act() {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            getBomberMan().setScore(getBomberMan().getScore() + 100);
            getCell().setItem(null);
        }
    }
}
