package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

public class DecreaseBombs extends Item {
    public DecreaseBombs(Cell cell, BomberMan bomberMan) {
        super(cell, bomberMan);
        setImage(Images.decreaseBombs);
        setType("Decrease Bombs");
    }

    @Override
    public void act() {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.OOO.play(0);
            if (getBomberMan().getBombLimit() > 1)
                getBomberMan().setBombLimit(getBomberMan().getBombLimit() - 1);
            getBomberMan().getMap().getScoreBoard().setBombIcon(getBomberMan().getBombLimit());
            getCell().setItem(null);
        }
    }
}
