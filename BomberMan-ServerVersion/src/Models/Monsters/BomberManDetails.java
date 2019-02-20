package Models.Monsters;

import Models.BomberMan;

public class BomberManDetails {
    private int x;
    private int y;
    private int score;
    private int bombLimit;
    private int bombRange;
    private boolean controlBomb;
    private int speed;
    private boolean failed;

    public BomberManDetails(BomberMan bomberMan) {
        this.x = bomberMan.getX();
        this.y = bomberMan.getY();
        this.score = bomberMan.getScore();
        this.bombLimit = bomberMan.getBombLimit();
        this.bombRange = bomberMan.getBombRange();
        this.controlBomb = bomberMan.isBombControl();
        this.speed = bomberMan.getStep();
        this.failed = bomberMan.isFailed();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getScore() {
        return score;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public int getBombRange() {
        return bombRange;
    }

    public boolean isControlBomb() {
        return controlBomb;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isFailed() {
        return failed;
    }
}
