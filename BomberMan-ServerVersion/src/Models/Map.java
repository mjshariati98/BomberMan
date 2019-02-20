package Models;

import Configs.GameConfiguration;
import Models.Items.*;
import Network.GameServer;
import Network.SocketThread;
import views.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private GamePanel gamePanel;
    private int rows;
    private int columns;
    private int width;
    private int height;
    private int mainWidth;
    private int mainHeight;
    private int topInset;
    private Cell[][] cells;
    private List<Monster> monsters;
    private List<BomberMan> bomberMans;
    private GameServer gameServer;

    public Map(int rows, int columns, int topInset,
               List<BomberMan> bomberMans, GameServer gameServer) {
        this.rows = rows;
        this.columns = columns;
        this.topInset = topInset;
        width = columns * GameConfiguration.CELL_WIDTH;
        height = rows * GameConfiguration.CELL_HEIGHT;
        mainWidth = width;
        mainHeight = height + 2 * topInset;
        cells = new Cell[getColumns()][getRows()];
        this.monsters = new ArrayList<>();
        this.bomberMans = bomberMans;
        this.gameServer = gameServer;
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

    public void addBomb(Bomb bomb){
        for (SocketThread socketThread : gameServer.getClientsSocketThreads()){
            socketThread.getNewBombs().add(bomb);
        }
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

    public List<BomberMan> getBomberMans() {
        return bomberMans;
    }

    public void setBomberMans(List<BomberMan> bomberMans) {
        this.bomberMans = bomberMans;
    }

    public Cell getRandomCell(List<Cell> cells) {
        if (cells.size() == 0)
            return null;
        return cells.get((int) (Math.random() * cells.size()));
    }

    public void buildRandomItems() {
        int count = getRows() * getColumns() / 300;
        if (count == 0)
            count = 1;

        while (count > 0) {
            new GhostAbility(getRandomCell(getWallCells()), this.bomberMans);
            new IncreaseSpeed(getRandomCell(getWallCells()), this.bomberMans);
            new DecreaseRadius(getRandomCell(getWallCells()), this.bomberMans);
            new IncreaseScore(getRandomCell(getWallCells()), this.bomberMans);
            new DecreaseScore(getRandomCell(getWallCells()), this.bomberMans);
            new ControlBombs(getRandomCell(getWallCells()), this.bomberMans);
            new IncreaseBombs(getRandomCell(getWallCells()), this.bomberMans);
            new DecreseSpeed(getRandomCell(getWallCells()), this.bomberMans);
            new IncreaseRadius(getRandomCell(getWallCells()), this.bomberMans);
            new DecreaseBombs(getRandomCell(getWallCells()), this.bomberMans);
            count--;
        }
        new Door(getRandomCell(getWallCells()), this.bomberMans, monsters);
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public GameServer getGameServer() {
        return gameServer;
    }
}
