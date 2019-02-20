package Models.Monsters;

import utils.Direction;

import java.util.List;

/**
 * Your class must extends AbstractNewMonster.
 * No package !
 */
public abstract class AbstractNewMonster {
    private int rows;
    private int columns;
    private int width;
    private int height;
    private int startingLevel;
    private String imagePath;
    private int deathScore;

    /**
     * Set startingLevel, imagePath and deathScore in Constructor with using theit setter.
     * StartingLevel begins from 2.
     * @param rows rows number of the map.
     * @param columns columns number of the map.
     */
    public AbstractNewMonster(int rows, int columns, int width, int height) {
        this.rows = rows;
        this.columns = columns;
        this.width = width;
        this.height = height;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getStartingLevel() {
        return startingLevel;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getDeathScore() {
        return deathScore;
    }

    public void setStartingLevel(int startingLevel) {
        this.startingLevel = startingLevel;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDeathScore(int deathScore) {
        this.deathScore = deathScore;
    }

    /**
     * @return an int for speed of monster in every move.
     */
    public abstract int getSpeed();

    /**
     *
     * @param cells Give you the Type of every Cell of the Map. ([column][row])
     * @param bomberMansDetails give you any required information about all bomberMans in the Map.
     * You can see Fields and Methods of BomberManDetails in BomberManDetailsClass tab.
     * @return a Direction to move or null for no move.
     * You can see Cell.CellTypes enum in CellTypesEnum tab.
     */
    public abstract Direction move(int x, int y, int row, int column, CellType[][] cells,int cellSize,
                                   List<BomberManDetails> bomberMansDetails);
}
