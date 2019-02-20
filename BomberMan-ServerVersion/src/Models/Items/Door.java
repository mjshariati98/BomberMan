package Models.Items;

import Configs.Images;
import Configs.Sound;
import Models.BomberMan;
import Models.Cell;
import Models.Item;
import Models.Monster;

import java.util.List;

public class Door extends Item {
    private List<Monster> monsters;

    public Door(Cell cell, List<BomberMan> bomberMans, List<Monster> monsters) {
        super(cell, bomberMans);
        if (monsters != null)
            this.monsters = monsters;
        setImage(Images.door);
        setType("Door");
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    @Override
    public void act(BomberMan bomberMan) {
        if (monsters.size() == 0 && getCell().getType() == Cell.CellTypes.EMPTY) {
            Sound.DOOR.play(0);
            bomberMan.setScore(bomberMan.getScore()+100);
            bomberMan.getGameServer().newLevel();
        }
    }
}
