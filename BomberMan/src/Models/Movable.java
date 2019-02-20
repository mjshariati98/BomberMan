package Models;

import Exceptions.GoOutOfMapException;
import Exceptions.GoToWallAndBlockCellException;
import utils.Direction;

public interface Movable {

    boolean isCellFree(Direction direction) throws GoToWallAndBlockCellException, GoOutOfMapException;

    void move(Direction direction);

}
