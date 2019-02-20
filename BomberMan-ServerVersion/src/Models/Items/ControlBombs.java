package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

import java.util.List;

public class ControlBombs extends Item {
    public ControlBombs(Cell cell, List<BomberMan> bomberMans) {
        super(cell, bomberMans);
        setImage(Images.controlBomb);
        setType("Control Bombs");
    }

    @Override
    public void act(BomberMan bomberMan) {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            bomberMan.setBombControl(true);
            getCell().setItem(null);
        }
    }
}
