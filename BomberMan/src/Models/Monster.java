package Models;

import Configs.GameConfiguration;
import Exceptions.GoOutOfMapException;
import Exceptions.GoToWallAndBlockCellException;
import utils.Direction;

import java.awt.*;
import java.util.List;

public abstract class Monster extends Character {
    private int id;
    private int deathScore;
    private int walk = 0;
    private int randomMoveCounter = 0;
    private int level;

    private Image front1;
    private Image front2;
    private Image front3;
    private Image back1;
    private Image back2;
    private Image back3;
    private Image left1;
    private Image left2;
    private Image left3;
    private Image right1;
    private Image right2;
    private Image right3;

    public Monster(Map map, int deathScore, int level) {
        super(map, GameConfiguration.MONSTERS_1_2_STEP);
        this.deathScore = deathScore;
        this.level = level;
        if (map != null) {
            List<Cell> emptyCells = map.getEmptyCells();
            emptyCells.remove(map.getCell(0, 0));
            emptyCells.remove(map.getCell(50, 0));
            emptyCells.remove(map.getCell(100, 0));
            emptyCells.remove(map.getCell(150, 0));
            emptyCells.remove(map.getCell(200, 0));
            emptyCells.remove(map.getCell(0, 50));
            emptyCells.remove(map.getCell(0, 100));
            emptyCells.remove(map.getCell(0, 150));
            emptyCells.remove(map.getCell(0, 200));

            Cell randomCell = map.getRandomCell(emptyCells);
            setX(randomCell.getX());
            setY(randomCell.getY());

//            map.getMonsters().add(this);
        }
        setWidth(GameConfiguration.MONSTER_WIDTH);
        setHeight(GameConfiguration.MONSTER_HEIGHT);
    }

    public Monster(Map map, int deathScore, int level, int id){
        super(map, GameConfiguration.MONSTERS_1_2_STEP);
        this.deathScore = deathScore;
        this.level = level;
        this.id = id;

        setWidth(GameConfiguration.MONSTER_WIDTH);
        setHeight(GameConfiguration.MONSTER_HEIGHT);

    }

    public int getId() {
        return id;
    }

    @Override
    public void setImage(int count, Direction direction) {
        switch (count) {
            case 0:
                switch (direction) {
                    case DOWN:
                        setImage(front1);
                        break;
                    case UP:
                        setImage(back1);
                        break;
                    case RIGHT:
                        setImage(right1);
                        break;
                    case LEFT:
                        setImage(left1);
                        break;
                }
                break;
            case 1:
                switch (direction) {
                    case DOWN:
                        setImage(front2);
                        break;
                    case UP:
                        setImage(back2);
                        break;
                    case RIGHT:
                        setImage(right2);
                        break;
                    case LEFT:
                        setImage(left2);
                        break;
                }
                break;
            case 2:
                switch (direction) {
                    case DOWN:
                        setImage(front3);
                        break;
                    case UP:
                        setImage(back3);
                        break;
                    case RIGHT:
                        setImage(right3);
                        break;
                    case LEFT:
                        setImage(left3);
                        break;
                }
                break;
        }
    }

    public Image getFront1() {
        return front1;
    }

    public void setFront1(Image front1) {
        this.front1 = front1;
    }

    public Image getFront2() {
        return front2;
    }

    public void setFront2(Image front2) {
        this.front2 = front2;
    }

    public Image getFront3() {
        return front3;
    }

    public void setFront3(Image front3) {
        this.front3 = front3;
    }

    public void setBack1(Image back1) {
        this.back1 = back1;
    }

    public void setBack2(Image back2) {
        this.back2 = back2;
    }

    public void setBack3(Image back3) {
        this.back3 = back3;
    }

    public void setLeft1(Image left1) {
        this.left1 = left1;
    }

    public void setLeft2(Image left2) {
        this.left2 = left2;
    }

    public void setLeft3(Image left3) {
        this.left3 = left3;
    }

    public void setRight1(Image right1) {
        this.right1 = right1;
    }

    public void setRight2(Image right2) {
        this.right2 = right2;
    }

    public void setRight3(Image right3) {
        this.right3 = right3;
    }

    public int getDeathScore() {
        return deathScore;
    }

    public int getLevel() {
        return level;
    }

    public boolean isCellFree(Direction direction) throws GoToWallAndBlockCellException, GoOutOfMapException {
        Cell finalCell = null;
        int count = 0;
        switch (direction) {
            case LEFT:
                count = 0;
                finalCell = getMap().getCell(getX() - getStep(), getY());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = getMap().getCell(getX() - getStep(), getY() + getHeight());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                if (count == 2)
                    return true;
                throw new GoToWallAndBlockCellException();
            case DOWN:
                count = 0;
                finalCell = getMap().getCell(getX(), getY() + getHeight()+ getStep());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = getMap().getCell(getX() + getWidth(), getY() + getHeight() + getStep());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                if (count == 2)
                    return true;
                throw new GoToWallAndBlockCellException();
            case UP:
                count = 0;
                finalCell = getMap().getCell(getX(), getY() - getStep());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = getMap().getCell(getX() + getWidth(), getY() - getStep());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                if (count == 2)
                    return true;
                throw new GoToWallAndBlockCellException();
            case RIGHT:
                count = 0;
                finalCell = getMap().getCell(getX() + getWidth() + getStep(), getY());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = getMap().getCell(getX() + getWidth() + getStep(), getY() + getHeight());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                if (count == 2)
                    return true;
                throw new GoToWallAndBlockCellException();
        }
        return false;
    }

    public void move() {
        boolean flag = false;

        if (randomMoveCounter == 0 || randomMoveCounter > 40) {
            randomMoveCounter = 0;
            try {
                if (getX() - getMap().getBomberMan().getX() >= 4 &&
                        isCellFree(Direction.LEFT) && !flag) {
                    setDirection(Direction.LEFT);
                    flag = true;
                }
            } catch (GoToWallAndBlockCellException | GoOutOfMapException e) {
//                e.printStackTrace();
            }
            try {
                if (getMap().getBomberMan().getX() - getX() >= 4 &&
                        isCellFree(Direction.RIGHT) && !flag) {
                    setDirection(Direction.RIGHT);
                    flag = true;
                }
            } catch (GoToWallAndBlockCellException | GoOutOfMapException e) {
//                e.printStackTrace();
            }
            try {
                if (getY() - getMap().getBomberMan().getY() >= 4 &&
                        isCellFree(Direction.UP) && !flag) {
                    setDirection(Direction.UP);
                    flag = true;
                }
            } catch (GoToWallAndBlockCellException | GoOutOfMapException e) {
//                e.printStackTrace();
            }
            try {
                if (getMap().getBomberMan().getY() - getY() >= 4 &&
                        isCellFree(Direction.DOWN) && !flag) {
                    setDirection(Direction.DOWN);
                    flag = true;
                }
            } catch (GoToWallAndBlockCellException | GoOutOfMapException e) {
//                e.printStackTrace();
            }

            if (!flag) {
                setDirection(null);
            }

            if (getDirection() == null) {
                randomMove();
                randomMoveCounter++;
                return;
            }
        } else {
            randomMove();
            randomMoveCounter++;
            return;
        }

        move(getDirection());
        checkSmash();
    }

    public void randomMove() {
        try {
            if (getDirection() == null ||
                    !isCellFree(getDirection())) {
                setDirection(Direction.randomDirection());
            } else if (getMap().getFourWayCells().contains(getMap().getCell(getX() , getY() )) &&
                    getMap().getFourWayCells().contains(getMap().getCell(getX() + getWidth() , getY() + getHeight() )) &&
                    walk++ == 0) {
                if (getDirection() == Direction.RIGHT || getDirection() == Direction.LEFT) {
                    setDirection(Direction.randomDirection("ud"));
                } else {
                    setDirection(Direction.randomDirection("rl"));
                }
            } else if (!getMap().getFourWayCells().contains(getCell())) {
                walk = 0;
            }
        } catch (GoToWallAndBlockCellException | GoOutOfMapException e) {
            setDirection(Direction.randomDirection());
        }

        move(getDirection());
        checkSmash();
    }

    @Override
    public void paint(Graphics2D g2) {
        g2.drawImage(getImage(),
                getX(), getY(), new Color(0, 0, 0, 0), null);
    }

    public void checkSmash() {
        if (Math.abs(getX() - getMap().getBomberMan().getX()) < getWidth() &&
                Math.abs(getY() - getMap().getBomberMan().getY()) < getHeight()) {
            getMap().getBomberMan().respawn();
        }

    }
}
