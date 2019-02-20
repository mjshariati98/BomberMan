package Models.Monsters;

import Configs.GameConfiguration;
import Configs.Images;
import Exceptions.GoOutOfMapException;
import Exceptions.GoToWallAndBlockCellException;
import Models.Cell;
import Models.Map;
import Models.Monster;
import utils.Direction;

public class MonsterLevel4 extends Monster {
    public MonsterLevel4(Map map) {
        super(map, 80, 4);
        setStep(GameConfiguration.MONSTERS_3_4_STEP);

        loadImages();
    }

    public MonsterLevel4(Map map, int id) {
        super(map, 80, 4, id);
        setStep(GameConfiguration.MONSTERS_3_4_STEP);

        loadImages();
    }

    private void loadImages(){
        setFront1(Images.monster4_Front1);
        setFront2(Images.monster4_Front2);
        setFront3(Images.monster4_Front3);
        setBack1(Images.monster4_Back1);
        setBack2(Images.monster4_Back2);
        setBack3(Images.monster4_Back3);
        setLeft1(Images.monster4_Left1);
        setLeft2(Images.monster4_Left2);
        setLeft3(Images.monster4_Left3);
        setRight1(Images.monster4_Right1);
        setRight2(Images.monster4_Right2);
        setRight3(Images.monster4_Right3);

        setImage(getFront2());
    }

    public boolean isCellFree(Direction direction) throws GoToWallAndBlockCellException, GoOutOfMapException {
        Cell finalCell = null;
        int count = 0;
        switch (direction) {
            case LEFT:
                count = 0;
                finalCell = getMap().getCell(getX() - getStep(), getCy() - (getHeight() / 2));
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (!finalCell.getType().equals(Cell.CellTypes.BOMB) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = getMap().getCell(getX() - getStep(), getCy() + (getHeight() / 2) - getStep());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (!finalCell.getType().equals(Cell.CellTypes.BOMB) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                if (count == 2)
                    return true;
                throw new GoToWallAndBlockCellException();
            case DOWN:
                count = 0;
                finalCell = getMap().getCell(getCx() - (getWidth() / 2), getY() + getHeight());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (!finalCell.getType().equals(Cell.CellTypes.BOMB) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = getMap().getCell(getCx() + (getWidth() / 2) - getStep(), getY() + getHeight());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (!finalCell.getType().equals(Cell.CellTypes.BOMB) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                if (count == 2)
                    return true;
                throw new GoToWallAndBlockCellException();
            case UP:
                count = 0;
                finalCell = getMap().getCell(getCx() - (getWidth() / 2), getY() - getStep());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (!finalCell.getType().equals(Cell.CellTypes.BOMB) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = getMap().getCell(getCx() + (getWidth() / 2) - getStep(), getY() - getStep());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (!finalCell.getType().equals(Cell.CellTypes.BOMB) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                if (count == 2)
                    return true;
                throw new GoToWallAndBlockCellException();
            case RIGHT:
                count = 0;
                finalCell = getMap().getCell(getX() + getWidth(), getCy() - (getHeight() / 2));
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (!finalCell.getType().equals(Cell.CellTypes.BOMB) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                finalCell = getMap().getCell(getX() + getWidth(), getCy() + (getHeight() / 2) - getStep());
                if (finalCell == null) {
                    throw new GoOutOfMapException();
                }
                if (!finalCell.getType().equals(Cell.CellTypes.BOMB) ||
                        (getCell().getType() == Cell.CellTypes.BOMB &&
                                finalCell.getType().equals(Cell.CellTypes.BOMB)))
                    count++;
                if (count == 2)
                    return true;
                throw new GoToWallAndBlockCellException();
        }
        return false;
    }

}
