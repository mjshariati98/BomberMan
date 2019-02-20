package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

import java.util.List;

public class DecreaseRadius extends Item {
    public DecreaseRadius(Cell cell, List<BomberMan> bomberMans) {
        super(cell, bomberMans);
        setImage(Images.decreaseRadius);
        setType("Decrease Radius");
    }

    @Override
    public void act(BomberMan bomberMan) {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.OOO.play(0);
            if (bomberMan.getBombRange() > 1)
                bomberMan.setBombRange(bomberMan.getBombRange() - 1);
            getCell().setItem(null);
        }
    }
}
