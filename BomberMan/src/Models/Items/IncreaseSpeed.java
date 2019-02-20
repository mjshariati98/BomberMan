package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

public class IncreaseSpeed extends Item {
    public IncreaseSpeed(Cell cell, BomberMan bomberMan) {
        super(cell, bomberMan);
        setImage(Images.increaseSpeed);
        setType("Increase Speed");
    }

    @Override
    public void act() {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            getBomberMan().setStep(getBomberMan().getStep() + 5);
            getBomberMan().getMap().getScoreBoard().setSpeedIcon(getBomberMan().getStep());
            getCell().setItem(null);
        }
    }
}
