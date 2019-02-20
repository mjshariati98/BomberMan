package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;

public class DecreaseRadius extends Item {
    public DecreaseRadius(Cell cell, BomberMan bomberMan) {
        super(cell, bomberMan);
        setImage(Images.decreaseRadius);
        setType("Decrease Radius");
    }

    @Override
    public void act() {
        if (getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.OOO.play(0);
            if (getBomberMan().getBombRange() > 1)
                getBomberMan().setBombRange(getBomberMan().getBombRange() - 1);
            getBomberMan().getMap().getScoreBoard().setRadiusIcon(getBomberMan().getBombRange());
            getCell().setItem(null);
        }
    }
}
