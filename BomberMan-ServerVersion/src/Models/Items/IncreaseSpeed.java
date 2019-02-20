package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

import java.util.List;

public class IncreaseSpeed extends Item {
    public IncreaseSpeed(Cell cell, List<BomberMan> bomberMans) {
        super(cell, bomberMans);
        setImage(Images.increaseSpeed);
        setType("Increase Speed");
    }

    @Override
    public void act(BomberMan bomberMan) {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            bomberMan.setStep(bomberMan.getStep() + 5);
            getCell().setItem(null);
        }
    }
}
