package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

import java.util.List;

public class DecreaseScore extends Item {
    public DecreaseScore(Cell cell, List<BomberMan> bomberMans) {
        super(cell, bomberMans);
        setImage(Images.decreaseScore);
        setType("Decrease Score");
    }

    @Override
    public void act(BomberMan bomberMan) {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.OOO.play(0);
            bomberMan.setScore(bomberMan.getScore() - 100);
            getCell().setItem(null);
        }
    }
}
