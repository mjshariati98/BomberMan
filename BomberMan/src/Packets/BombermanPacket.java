package Packets;

import Models.BomberMan;
import utils.Direction;

import java.io.Serializable;

public class BombermanPacket implements Serializable {
    private int id;
    private String name;
    private int x;
    private int y;
    private Direction direction;
    private int bombRange;
    private int bombLimit;
    private boolean bombControl;
    private int speed;
    private int score;
    private boolean respawning;
    private int bombSize;
    private boolean failed;
    private boolean ghostAbility;


    public BombermanPacket(int id, BomberMan bomberMan) {
        this.id = id;
        this.name = bomberMan.getName();
        this.x = bomberMan.getX();
        this.y = bomberMan.getY();
        this.direction = bomberMan.getDirection();
        this.bombControl = bomberMan.isBombControl();
        this.bombLimit = bomberMan.getBombLimit();
        this.bombRange = bomberMan.getBombRange();
        this.speed = bomberMan.getStep();
        this.score = bomberMan.getScore();
        this.respawning = bomberMan.isRespawning();
        this.bombSize = bomberMan.getBobms().size();
        this.failed = bomberMan.isFailed();
        this.ghostAbility = bomberMan.isGhostAbility();
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getBombRange() {
        return bombRange;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public boolean isBombControl() {
        return bombControl;
    }

    public int getSpeed() {
        return speed;
    }

    public int getScore() {
        return score;
    }

    public boolean isRespawning() {
        return respawning;
    }

    public int getBombSize() {
        return bombSize;
    }

    public boolean isFailed() {
        return failed;
    }

    public boolean isGhostAbility() {
        return ghostAbility;
    }
}
