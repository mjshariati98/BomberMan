package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

public class DecreseSpeed extends Item {
    public DecreseSpeed(Cell cell, BomberMan bomberMan) {
        super(cell, bomberMan);
        setImage(Images.decreaseSpeed);
        setType("Decrease Speed");
    }

    @Override
    public void act() {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.OOO.play(0);
            if (getBomberMan().getStep() > 5)
                getBomberMan().setStep(getBomberMan().getStep() - 1);
            getBomberMan().getMap().getScoreBoard().setSpeedIcon(getBomberMan().getStep());
            getCell().setItem(null);
        }
    }
}
