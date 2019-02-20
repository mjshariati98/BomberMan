package Models;

import Configs.GameConfiguration;
import Models.Items.*;
import Packets.MapPacket;
import views.GamePanel;
import views.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private GamePanel gamePanel;
    private BomberMan bomberMan;
    private int rows;
    private int columns;
    private int width;
    private int height;
    private int mainWidth;
    private int mainHeight;
    private int topInset;
    private Cell[][] cells;
    private List<Monster> monsters;
    private ScoreBoard scoreBoard;
    private List<BomberMan> otherBomberMans;

    public Map(int rows, int columns, int topInset) {
        this.rows = rows;
        this.columns = columns;
        this.topInset = topInset;
        width = columns * GameConfiguration.CELL_WIDTH;
        height = rows * GameConfiguration.CELL_HEIGHT;
        mainWidth = width;
        mainHeight = height + 2 * topInset;
        cells = new Cell[getColumns()][getRows()];
        monsters = new ArrayList<>();

        buildRandomMap();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTopInset() {
        return topInset;
    }

    public int getMainWidth() {
        return mainWidth;
    }

    public int getMainHeight() {
        return mainHeight;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public void buildRandomMap() {
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {
                if (i % 2 != 0 && j % 2 != 0) {
                    cells[i][j] = new Cell(i * GameConfiguration.CELL_WIDTH, j * GameConfiguration.CELL_HEIGHT, Cell.CellTypes.BLOCK);
                } else {
                    cells[i][j] = new Cell(i * GameConfiguration.CELL_WIDTH, j * GameConfiguration.CELL_HEIGHT, Cell.CellTypes.EMPTY);
                }
            }
        }

        int freeCells = getRows() * getColumns() / 2 - 2;
        int wallCells = (int) (GameConfiguration.WALL_CELLS_RATIO * freeCells);
        while (wallCells > 0) {
            int iRandom = (int) (Math.random() * getColumns());
            int jRandom = (int) (Math.random() * getRows());
            if (cells[iRandom][jRandom].getType().equals(Cell.CellTypes.EMPTY)) {
                wallCells--;
                cells[iRandom][jRandom].setType(Cell.CellTypes.WALL);
            }
        }

        cells[0][0] = new Cell(0, 0, Cell.CellTypes.EMPTY);
        cells[1][0] = new Cell(GameConfiguration.CELL_WIDTH, 0, Cell.CellTypes.EMPTY);
        cells[0][1] = new Cell(0, GameConfiguration.CELL_HEIGHT, Cell.CellTypes.EMPTY);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0)
            return null;
        int cellX = x / GameConfiguration.CELL_WIDTH;
        int cellY = y / GameConfiguration.CELL_HEIGHT;
        if (cellX >= 0 && cellY >= 0 && cellX < getColumns() && cellY < getRows())
            return cells[cellX][cellY];
        return null;
    }

    public List<Cell> getEmptyCells() {
        List<Cell> emptyCells = new ArrayList<>();
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {
                if (cells[i][j].getType() == Cell.CellTypes.EMPTY)
                    emptyCells.add(cells[i][j]);
            }
        }
        return emptyCells;
    }

    public List<Cell> getWallCells() {
        List<Cell> wallCells = new ArrayList<>();
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {
                if (cells[i][j].getType() == Cell.CellTypes.WALL)
                    wallCells.add(cells[i][j]);
            }
        }
        return wallCells;
    }

    public List<Cell> getFourWayCells() {
        List<Cell> fourWayCells = new ArrayList<>();
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    fourWayCells.add(cells[i][j]);
                }
            }
        }
        return fourWayCells;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setBomberMan(BomberMan bomberMan) {
        this.bomberMan = bomberMan;
    }

    public BomberMan getBomberMan() {
        return bomberMan;
    }

    public Cell getRandomCell(List<Cell> cells) {

        return cells.get((int) (Math.random() * cells.size()));
    }

    public void buildRandomItems() {

        if ((getBomberMan().getGameFrame().getLevelManager().getLevelNumber() > 2 ||
                (getRows() >= 20 || getColumns() >= 20)) &&
                getBomberMan().getStep() == 5) {
            new IncreaseSpeed(getRandomCell(getWallCells()), this.bomberMan);
        }
        if ((getBomberMan().getGameFrame().getLevelManager().getLevelNumber() > 2 ||
                (getRows() >= 20 || getColumns() >= 20)) &&
                getBomberMan().getStep() == 10) {
            new DecreseSpeed(getRandomCell(getWallCells()), this.bomberMan);
        }
        int count = getRows() * getColumns() / 300;
        if (count == 0)
            count = 1;

        while (count > 0) {
            new GhostAbility(getRandomCell(getWallCells()),this.bomberMan);
            new DecreaseRadius(getRandomCell(getWallCells()), this.bomberMan);
            new IncreaseScore(getRandomCell(getWallCells()), this.bomberMan);
            new DecreaseScore(getRandomCell(getWallCells()), this.bomberMan);
            new ControlBombs(getRandomCell(getWallCells()), this.bomberMan);
            new IncreaseBombs(getRandomCell(getWallCells()), this.bomberMan);
            new IncreaseRadius(getRandomCell(getWallCells()), this.bomberMan);
            new DecreaseBombs(getRandomCell(getWallCells()), this.bomberMan);
            count--;
        }
        new Door(getRandomCell(getWallCells()), this.bomberMan, monsters);
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<BomberMan> getOtherBomberMans() {
        return otherBomberMans;
    }

    public void setOtherBomberMans(List<BomberMan> otherBomberMans) {
        this.otherBomberMans = otherBomberMans;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public void setItems(MapPacket mapPacket) {
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {
                Cell cell = cells[i][j];
                switch (mapPacket.getItemsPacket().getItems()[i][j]) {
                    case "Control Bombs":
                        new ControlBombs(cell, this.bomberMan);
                        break;
                    case "Decrease Bombs":
                        new DecreaseBombs(cell, this.bomberMan);
                        break;
                    case "Decrease Radius":
                        new DecreaseRadius(cell, this.bomberMan);
                        break;
                    case "Decrease Score":
                        new DecreaseScore(cell, this.bomberMan);
                        break;
                    case "Decrease Speed":
                        new DecreseSpeed(cell, this.bomberMan);
                        break;
                    case "Door":
                        new Door(cell, this.bomberMan, null);
                        break;
                    case "Increase Bombs":
                        new IncreaseBombs(cell, this.bomberMan);
                        break;
                    case "Increase Radius":
                        new IncreaseRadius(cell, this.bomberMan);
                        break;
                    case "Increase Score":
                        new IncreaseScore(cell, this.bomberMan);
                        break;
                    case "Increase Speed":
                        new IncreaseSpeed(cell, this.bomberMan);
                        break;
                    case "Ghost":
                        new GhostAbility(cell, this.bomberMan);
                        break;
                    default:
                        cell.setItem(null);
                }
            }
        }
    }

    public BomberMan getBomberManByID(int bomberManID) {
        List<BomberMan> allBomberMans = new ArrayList<>(otherBomberMans);
        if (this.bomberMan != null) {
            allBomberMans.add(this.bomberMan);
        }

        for (BomberMan bomberMan : allBomberMans) {
            if (bomberMan.getId() == bomberManID) {
                return bomberMan;
            }
        }
        return null;
    }

    public void setCells(MapPacket mapPacket) {
        for (int i = 0; i < getColumns(); i++) {
            for (int j = 0; j < getRows(); j++) {
                getCells()[i][j].setType(mapPacket.getCells()[i][j].getType());
            }
        }
    }
}
