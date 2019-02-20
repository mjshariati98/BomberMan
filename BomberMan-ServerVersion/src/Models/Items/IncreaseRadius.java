package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

import java.util.List;

public class IncreaseRadius extends Item {
    public IncreaseRadius(Cell cell, List<BomberMan> bomberMans) {
        super(cell, bomberMans);
        setImage(Images.increaseRadius);
        setType("Increase Radius");
    }

    @Override
    public void act(BomberMan bomberMan) {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            if (bomberMan.getBombRange() < 5)
                bomberMan.setBombRange(bomberMan.getBombRange() + 1);
            getCell().setItem(null);
        }
    }
}
