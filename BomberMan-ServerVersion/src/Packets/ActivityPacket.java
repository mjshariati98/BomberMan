package Packets;

import utils.Direction;

import java.io.Serializable;

public class ActivityPacket implements Serializable {
    private int id;
    private int x;
    private int y;
    private Direction direction;
    private Activity activity;

    public ActivityPacket(int id, int x, int y, Direction direction) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
        activity = Activity.NONE;
    }

    public enum Activity {
        NONE, UP, DOWN, LEFT, RIGHT, BOMB, EXPLODE
    }

    public int getId() {
        return id;
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}