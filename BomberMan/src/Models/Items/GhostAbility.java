package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

public class GhostAbility extends Item {

    public GhostAbility(Cell cell, BomberMan bomberMan) {
        super(cell, bomberMan);
        setImage(Images.ghost);
        setType("Ghost");
    }

    @Override
    public void act() {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            getBomberMan().setGhostAbility(true);
            getCell().setItem(null);
        }
    }
}
