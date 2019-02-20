package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

public class IncreaseBombs extends Item {
    public IncreaseBombs(Cell cell, BomberMan bomberMan) {
        super(cell, bomberMan);
        setImage(Images.increaseBombs);
        setType("Increase Bombs");
    }

    @Override
    public void act() {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            if (getBomberMan().getBombLimit() < 5)
                getBomberMan().setBombLimit(getBomberMan().getBombLimit() + 1);
            getBomberMan().getMap().getScoreBoard().setBombIcon(getBomberMan().getBombLimit());
            getCell().setItem(null);
        }
    }
}
