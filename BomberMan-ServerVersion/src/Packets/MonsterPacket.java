package Packets;

import Models.Monster;
import Models.Monsters.InfiniteMonster;
import utils.Direction;

import java.io.Serializable;

public class MonsterPacket implements Serializable {
    private int id;
    private int level;
    private int x;
    private int y;
    private Direction direction;
    private int step;
    private String encodedImage;

    public MonsterPacket(Monster monster, boolean newLevel) {
        this.id = monster.getId();
        this.level = monster.getLevel();
        this.x = monster.getX();
        this.y = monster.getY();
        this.direction = monster.getDirection();
        this.step = monster.getStep();
        if (monster.getLevel() == 0 && newLevel){
            encodedImage = ((InfiniteMonster)monster).getEncodedImage();
        }
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
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

    public int getStep() {
        return step;
    }

    public String getEncodedImage() {
        return encodedImage;
    }
}