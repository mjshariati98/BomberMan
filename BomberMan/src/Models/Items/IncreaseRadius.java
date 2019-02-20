package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

public class IncreaseRadius extends Item {
    public IncreaseRadius(Cell cell, BomberMan bomberMan) {
        super(cell, bomberMan);
        setImage(Images.increaseRadius);
        setType("Increase Radius");
    }

    @Override
    public void act() {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.POWERUP.play(0);
            if (getBomberMan().getBombRange() < 5)
                getBomberMan().setBombRange(getBomberMan().getBombRange() + 1);
            getBomberMan().getMap().getScoreBoard().setRadiusIcon(getBomberMan().getBombRange());
            getCell().setItem(null);
        }
    }
}
