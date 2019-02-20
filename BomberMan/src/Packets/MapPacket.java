package Packets;

import Models.Cell;
import Models.Map;

import java.io.Serializable;
import java.util.List;

public class MapPacket implements Serializable {
    private int rows;
    private int columns;
    private int levelNumber;
    private Cell[][] cells;
    private ItemsPacket itemsPacket;
    private List<BombermanPacket> bombermanPackets;
    private List<MonsterPacket> monsterPackets;
    //    private List<BombPacket> bombPackets;
    private boolean newLevel;

    public MapPacket(Map map, boolean newLevel) {
        this.newLevel = newLevel;
        this.cells = map.getCells();
        this.rows = map.getRows();
        this.columns = map.getColumns();
        this.itemsPacket = new ItemsPacket(map);
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
