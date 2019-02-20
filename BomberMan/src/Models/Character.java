package Models;

import Exceptions.GoOutOfMapException;
import Exceptions.GoToWallAndBlockCellException;
import utils.Direction;

import java.awt.*;

public abstract class Character implements Movable{
    private int x;
    private int y;
    private int width;
    private int height;
    private Map map;
    private int step;
    private Direction direction;
    private Image image;
    private int frontImagesCounter = 1;
    private int backImagesCounter = 1;
    private int rightImagesCounter = 1;
    private int leftImagesCounter = 1;


    public Character(Map map, int step) {
        this.map = map;
        this.step = step;
        this.x = 0;
        this.y = 0;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCx() {
        return getX() + (getWidth() / 2);
    }

    public int getCy() {
        return getY() + (getHeight() / 2);
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Cell getCell() {
        return map.getCell(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public abstract void setImage(int count, Direction direction);

    public int getFrontImagesCounter() {
        return frontImagesCounter;
    }

    public int getBackImagesCounter() {
        return backImagesCounter;
    }

    public int getRightImagesCounter() {
        return rightImagesCounter;
    }

    public int getLeftImagesCounter() {
        return leftImagesCounter;
    }

    public void setFrontImagesCounter(int frontImagesCounter) {
        this.frontImagesCounter = frontImagesCounter;
    }

    public void setBackImagesCounter(int backImagesCounter) {
        this.backImagesCounter = backImagesCounter;
    }

    public void setRightImagesCounter(int rightImagesCounter) {
        this.rightImagesCounter = rightImagesCounter;
    }

    public void setLeftImagesCounter(int leftImagesCounter) {
        this.leftImagesCounter = leftImagesCounter;
    }

    public boolean isCellFree(Direction direction) throws GoToWallAndBlockCellException, GoOutOfMapException {
        Cell finalCell = null;
        int count = 0;
        switch (direction) {
            case LEFT:
                count = 0;
                finalCell = map.getCell(getX() - step, getY());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = map.getCell(getX() - step, getY() + getHeight() - step);

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
                finalCell = map.getCell(getX(), getY() + getHeight());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = map.getCell(getX() + getWidth() - step, getY() + getHeight());
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
                finalCell = map.getCell(getX(), getY() - step);
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = map.getCell(getX() + getWidth() - step, getY() - step);
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
                finalCell = map.getCell(getX() + getWidth(), getY());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (finalCell.getType().equals(Cell.CellTypes.EMPTY) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = map.getCell(getX() + getWidth(), getY() + getHeight() - step);
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

    public void move(Direction direction) {
        if (direction == null)
            return;
        int step = getStep();
        switch (direction) {
            case UP:
                setImage(backImagesCounter++ % 4, Direction.UP);
                try {
                    if (getY() - step >= 0 && isCellFree(Direction.UP)) {
                        setY(getY() - step);
                    } else {
                        throw new GoOutOfMapException();
                    }
                } catch (GoOutOfMapException | GoToWallAndBlockCellException e) {
//                    e.printStackTrace();
                }
                break;
            case DOWN:
                setImage(frontImagesCounter++ % 4, Direction.DOWN);
                try {
                    if (getY() + this.getHeight() + step <= map.getHeight() && isCellFree(Direction.DOWN)) {
                        setY(getY() + step);
                    } else {
                        throw new GoOutOfMapException();
                    }
                } catch (GoOutOfMapException | GoToWallAndBlockCellException e) {
//                    e.printStackTrace();
                }
                break;
            case LEFT:
                setImage(leftImagesCounter++ % 4, Direction.LEFT);
                try {
                    if (getX() - step >= 0 && isCellFree(Direction.LEFT)) {
                        setX(getX() - step);
                    } else {
                        throw new GoOutOfMapException();
                    }
                } catch (GoOutOfMapException | GoToWallAndBlockCellException e) {
//                    e.printStackTrace();
                }
                break;
            case RIGHT:
                setImage(rightImagesCounter++ % 4, Direction.RIGHT);
                try {
                    if (getX() + this.getWidth() + step <= map.getWidth() && isCellFree(Direction.RIGHT)) {
                        setX(getX() + step);
                    } else {
                        throw new GoOutOfMapException();
                    }
                } catch (GoOutOfMapException | GoToWallAndBlockCellException e) {
//                    e.printStackTrace();
                }
                break;
        }
    }

    public void move(int nextX, int nextY){
        if (nextX > getX())
            move(Direction.RIGHT);
        else if (nextX < getX())
            move(Direction.LEFT);

        if (nextY > getY())
            move(Direction.DOWN);
        else if (nextY < getY())
            move(Direction.UP);
    }

    public abstract void paint(Graphics2D g2);

}