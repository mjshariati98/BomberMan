package Packets;

import Models.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapPacket implements Serializable {
    private int rows;
    private int columns;
    private int levelNumber;
    private Cell[][] cells;
    private ItemsPacket itemsPacket;
    private List<BombermanPacket> bombermanPackets;
    private List<MonsterPacket> monsterPackets;
    private boolean newLevel;

    public MapPacket(Map map, boolean newLevel) {
        this.newLevel = newLevel;
        this.cells = map.getCells();
        this.rows = map.getRows();
        this.columns = map.getColumns();
        this.levelNumber = map.getGameServer().getLevelNumber();
        this.itemsPacket = new ItemsPacket(map);

        this.bombermanPackets = new ArrayList<>();
        for (BomberMan bomberMan : map.getBomberMans()) {
            BombermanPacket bombermanPacket = new BombermanPacket(bomberMan.getId(), bomberMan);
            bombermanPackets.add(bombermanPacket);
        }
        this.monsterPackets = new ArrayList<>();
        for (Monster monster : map.getMonsters()) {
            MonsterPacket monsterPacket = new MonsterPacket(monster, newLevel);
            monsterPackets.add(monsterPacket);
        }

    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public ItemsPacket getItemsPacket() {
        return itemsPacket;
    }

    public List<BombermanPacket> getBombermanPackets() {
        return bombermanPackets;
    }

    public List<MonsterPacket> getMonsterPackets() {
        return monsterPackets;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public boolean isNewLevel() {
        return newLevel;
    }
}
