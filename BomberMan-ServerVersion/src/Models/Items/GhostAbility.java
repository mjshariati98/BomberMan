package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

import java.util.List;

public class GhostAbility extends Item {

    public GhostAbility(Cell cell, List<BomberMan> bomberMans) {
        super(cell, bomberMans);
        setImage(Images.ghost);
        setType("Ghost");
    }

    @Override
    public void act(BomberMan bomberMan) {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            bomberMan.setGhostAbility(true);
            getCell().setItem(null);
        }
    }
}
