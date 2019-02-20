package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

import java.util.List;

public class DecreaseBombs extends Item {
    public DecreaseBombs(Cell cell, List<BomberMan> bomberMans) {
        super(cell, bomberMans);
        setImage(Images.decreaseBombs);
        setType("Decrease Bombs");
    }

    @Override
    public void act(BomberMan bomberMan) {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.OOO.play(0);
            if (bomberMan.getBombLimit() > 1)
                bomberMan.setBombLimit(bomberMan.getBombLimit() - 1);
            getCell().setItem(null);
        }
    }
}
