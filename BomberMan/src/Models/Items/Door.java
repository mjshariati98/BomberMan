package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;
import Models.Monster;
import views.NewLevelDialog;

import java.util.List;

public class Door extends Item {
    private boolean available;
    private List<Monster> monsters;

    public Door(Cell cell, BomberMan bomberMan, List<Monster> monsters) {
        super(cell, bomberMan);
        if (monsters != null)
            this.monsters = monsters;
        setImage(Images.door);
        available = false;
        setType("Door");
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    @Override
    public void act() {
        if (monsters.size() == 0 && getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.DOOR.play(0);
            NewLevelDialog newLevelDialog = new NewLevelDialog(getBomberMan().getGameFrame(), getBomberMan());
        }
    }
}
