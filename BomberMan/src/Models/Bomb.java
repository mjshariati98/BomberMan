package Models;

import Configs.GameConfiguration;
import Configs.Images;
import Configs.Sound;
import Exceptions.DieException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bomb {
    private BomberMan bomberMan;
    private int x;
    private int y;
    private Map map;
    private boolean exploded;
    private Date date;
    private long createdTime;
    private Image image = null;
    private int imageCounter = 0;
    private Cell.CellTypes previousCellType;

    public Bomb(BomberMan bomberMan, Map map) {
        this.bomberMan = bomberMan;
        this.bomberMan.getBobms().add(this);
        this.x = bomberMan.getX() + (bomberMan.getWidth() / 2);
        this.y = bomberMan.getY() + (bomberMan.getHeight() / 2);
        this.map = map;
        exploded = false;
        previousCellType = getCell().getType();
        getCell().setType(Cell.CellTypes.BOMB, this);
        image = Images.bomb1;
        date = new Date();
        createdTime = date.getTime();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Sound.TICKTOCK.play(0);

                while (!isExploded()) {
                    if (imageCounter != 3)
                        setImage(imageCounter++ % 3);
                    map.getGamePanel().repaint();
                    try {
                        Thread.sleep(GameConfiguration.EXPLODE_TIME / 3);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                    }
                }
                map.getGamePanel().repaint();
                if (previousCellType == Cell.CellTypes.WALL)
                    getCell().setType(Cell.CellTypes.EMPTY);
                else
                    getCell().setType(previousCellType);
                getCell().setBomb(null);
            }
        }).start();
    }

    public Bomb (BomberMan bomberMan, int x, int y, Map map){
        this.bomberMan = bomberMan;
        this.bomberMan.getBobms().add(this);
        this.x = x;
        this.y = y;
        this.map = map;
        exploded = false;
        getCell().setType(Cell.CellTypes.BOMB, this);
        image = Images.bomb1;
        date = new Date();
        createdTime = date.getTime();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Sound.TICKTOCK.play(0);

                while (!isExploded()) {
                    if (imageCounter != 3)
                        setImage(imageCounter++ % 3);
                    map.getGamePanel().repaint();
                    try {
                        Thread.sleep(GameConfiguration.EXPLODE_TIME / 3);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                    }
                }
                map.getGamePanel().repaint();
                getCell().setType(Cell.CellTypes.EMPTY);
                getCell().setBomb(null);
            }
        }).start();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BomberMan getBomberMan() {
        return bomberMan;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImage(int count) {
        switch (count) {
            case 0:
                setImage(Images.bomb1);
                break;
            case 1:
                setImage(Images.bomb2);
                break;
            case 2:
                setImage(Images.bomb3);
                break;
        }
    }

    public boolean isExploded() {
        if (exploded)
            return true;
        Date newDate = new Date();
        if (newDate.getTime() - createdTime > GameConfiguration.EXPLODE_TIME &&
                bomberMan.isBombControl() == false) {
            return true;
        }
        return false;
    }

    public void setExploded(boolean x) {
        this.exploded = x;
    }

    public Cell getCell() {
        Cell cell = map.getCell((getX() / GameConfiguration.CELL_WIDTH) * GameConfiguration.CELL_WIDTH,
                (getY() / GameConfiguration.CELL_HEIGHT) * GameConfiguration.CELL_HEIGHT);
        return cell;
    }

    public Cell.CellTypes getPreviousCellType() {
        return previousCellType;
    }

    public void setPreviousCellType(Cell.CellTypes previousCellType) {
        this.previousCellType = previousCellType;
    }

    public List<Cell> getCellsInRange(int bombRange){
        int cellWidth = GameConfiguration.CELL_WIDTH;
        int cellHeight = GameConfiguration.CELL_HEIGHT;
        List<Cell> cellsInRange = new ArrayList<>();
        boolean up = true;
        boolean down = true;
        boolean right = true;
        boolean left = true;
        for (int i = 1; i <= bombRange; i++) {
            if (map.getCell(getX() + (cellWidth * i), getY()) != null && right) {
                cellsInRange.add(map.getCell(getX() + (cellWidth * i), getY()));
                if (map.getCell(getX() + (cellWidth * i), getY()).getType() == Cell.CellTypes.BLOCK)
                    right = false;
            }
            if (map.getCell(getX() - (cellWidth * i), getY()) != null && left) {
                cellsInRange.add(map.getCell(getX() - (cellWidth * i), getY()));
                if (map.getCell(getX() - (cellWidth * i), getY()).getType() == Cell.CellTypes.BLOCK)
                    left = false;
            }
            if (map.getCell(getX(), getY() + (cellHeight * i)) != null && down) {
                cellsInRange.add(map.getCell(getX(), getY() + (cellHeight * i)));
                if (map.getCell(getX(), getY() + (cellHeight * i)).getType() == Cell.CellTypes.BLOCK)
                    down = false;
            }
            if (map.getCell(getX(), getY() - (cellHeight * i)) != null && up) {
                cellsInRange.add(map.getCell(getX(), getY() - (cellHeight * i)));
                if (map.getCell(getX(), getY() - (cellHeight * i)).getType() == Cell.CellTypes.BLOCK)
                    up = false;
            }
        }
        cellsInRange.add(map.getCell(getX(),getY()));

        return cellsInRange;
    }

    // Server-Client
    public void explode(int bombRange, Graphics2D g2){
        for (Cell cell : getCellsInRange(bombRange)){
            if (cell.getType() == Cell.CellTypes.BOMB && cell.getBomb() != null)
                cell.getBomb().setExploded(true);
        }
        paintSpark(g2, getCellsInRange(bombRange));
    }

    // Single
    public void explode(int bombRange, BomberMan bomberMan, Graphics2D g2) throws DieException {
        Sound.TICKTOCK.stop();
        if (bomberMan.getBobms().size() % 2 == 1) {
            Sound.EXPLOSION2.play(0);
        } else {
            Sound.EXPLOSION1.play(0);
        }


        Cell bomberManCell = bomberMan.getCell();
        int cellWidth = GameConfiguration.CELL_WIDTH;
        int cellHeight = GameConfiguration.CELL_HEIGHT;
        boolean die = false;
        List<Monster> deadMonsters = new ArrayList<>();

        paintSpark(g2, getCellsInRange(bombRange));
        for (Cell cell : getCellsInRange(bombRange)) {
            if (cell.getType().equals(Cell.CellTypes.WALL)) {
                cell.setType(Cell.CellTypes.EMPTY);
                cell.setBomb(null);
                bomberMan.setScore(bomberMan.getScore() + GameConfiguration.DESTROY_WALL_SCORE);
            }
            if (cell.getType().equals(Cell.CellTypes.BOMB)) {
                cell.getBomb().setExploded(true);
            }
            if (bomberManCell == cell) {
                die = true;
            }
            for (Monster monster : getBomberMan().getMap().getMonsters()) {
                if (monster.getCell() == cell) {
                    deadMonsters.add(monster);
                    bomberMan.setScore(bomberMan.getScore() + monster.getDeathScore());
                }
            }
        }
        if (deadMonsters.size() > 0)
            Sound.MONSTERDIE.play(0);
        map.getMonsters().removeAll(deadMonsters);
        if (die || bomberManCell == getCell()) {
            throw new DieException();
        }
    }

    public void paint(Graphics2D g2) {
        g2.drawImage(getImage(),
                getCell().getX() + (GameConfiguration.CELL_WIDTH / 5),
                getCell().getY() + (GameConfiguration.CELL_HEIGHT / 5),
                new Color(0, 0, 0, 0), null);
    }

    public void paintSpark(Graphics2D g2, List<Cell> cells) {
        for (Cell cell : cells) {
            g2.drawImage(Images.spark, cell.getX(), cell.getY(), null);
        }

    }

}
