package utils;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    public static Direction randomDirection() {
        int i = (int) (Math.random() * 4);
        switch (i) {
            case 0:
                return UP;
            case 1:
                return DOWN;
            case 2:
                return LEFT;
            default:
                return RIGHT;
        }
    }

    public static Direction randomDirection(String s){
        if (s.equals("rl")){ // rl : Right & Left
            while (true){
                Direction d = randomDirection();
                if (d == Direction.LEFT || d == Direction.RIGHT){
                    return d;
                }
            }
        }else{ // ud : Up & Down
            while (true){
                Direction d = randomDirection();
                if (d == Direction.UP || d == Direction.DOWN){
                    return d;
                }
            }
        }
    }
}
