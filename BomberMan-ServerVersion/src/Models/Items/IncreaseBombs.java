package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

import java.util.List;

public class IncreaseBombs extends Item {
    public IncreaseBombs(Cell cell, List<BomberMan> bomberMans) {
        super(cell, bomberMans);
        setImage(Images.increaseBombs);
        setType("Increase Bombs");
    }

    @Override
    public void act(BomberMan bomberMan) {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            if (bomberMan.getBombLimit() < 5)
                bomberMan.setBombLimit(bomberMan.getBombLimit() + 1);
            getCell().setItem(null);
        }
    }
}
