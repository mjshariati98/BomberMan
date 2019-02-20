package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

import java.util.List;

public class IncreaseScore extends Item {
    public IncreaseScore(Cell cell, List<BomberMan> bomberMans) {
        super(cell, bomberMans);
        setImage(Images.increaseScore);
        setType("Increase Score");
    }

    @Override
    public void act(BomberMan bomberMan) {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            bomberMan.setScore(bomberMan.getScore() + 100);
            getCell().setItem(null);
        }
    }
}
