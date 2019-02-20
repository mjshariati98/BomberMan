package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

import java.util.List;

public class DecreseSpeed extends Item {
    public DecreseSpeed(Cell cell, List<BomberMan> bomberMans) {
        super(cell, bomberMans);
        setImage(Images.decreaseSpeed);
        setType("Decrease Speed");
    }

    @Override
    public void act(BomberMan bomberMan) {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.OOO.play(0);
            if (bomberMan.getStep() > 5)
                bomberMan.setStep(bomberMan.getStep() - 1);
            getCell().setItem(null);
        }
    }
}
