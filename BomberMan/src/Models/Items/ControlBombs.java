package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

public class ControlBombs extends Item {
    public ControlBombs(Cell cell, BomberMan bomberMan) {
        super(cell, bomberMan);
        setImage(Images.controlBomb);
        setType("Control Bombs");
    }

    @Override
    public void act() {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            getBomberMan().setBombControl(true);
            getBomberMan().getMap().getScoreBoard().setControlBombIcon(true);
            getCell().setItem(null);
        }
    }
}
