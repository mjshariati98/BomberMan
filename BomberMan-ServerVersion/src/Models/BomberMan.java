package Models;

import Configs.GameConfiguration;
import Configs.Images;
import Configs.Sound;
import Exceptions.GoOutOfMapException;
import Exceptions.GoToWallAndBlockCellException;
import Network.GameServer;
import utils.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BomberMan extends Character {
    private GameServer gameServer;
    private int bombRange;
    private int bombLimit;
    private boolean bombControl;
    private int score;
    private List<Bomb> bobms = new ArrayList<>();
    private boolean respawning;
    private int id;
    private String name;
    private boolean failed;
    private boolean ghostAbility;

    private Image front1;
    private Image front2;
    private Image front3;
    private Image front4;
    private Image back1;
    private Image back2;
    private Image back3;
    private Image back4;
    private Image left1;
    private Image left2;
    private Image left3;
    private Image left4;
    private Image right1;
    private Image right2;
    private Image right3;
    private Image right4;
    private int respawnCounter = 1;

    public BomberMan(Map map, int id, String name) {
        super(map, GameConfiguration.BOMBERMAN_INITIAL_STEP);
        this.id = id;
        this.name = name;
        this.failed =false;
        setX(0);
        setY(0);
        setWidth(GameConfiguration.BOMBERMAN_WIDTH);
        setHeight(GameConfiguration.BOMBERMAN_HEIGHT);
        bombRange = GameConfiguration.INITIAL_BOMB_RANGE;
        bombLimit = GameConfiguration.INITIAL_BOMB_LIMIT;
        score = GameConfiguration.INITIAL_SCORE;
        bombControl = GameConfiguration.INITIAL_CONTROL_BOMB;
        ghostAbility = false;

        loadImages();
    }

    public void loadImages() {
        front1 = Images.bomberMan_Front1;
        front2 = Images.bomberMan_Front2;
        front3 = Images.bomberMan_Front3;
        front4 = Images.bomberMan_Front4;
        back1 = Images.bomberMan_Back1;
        back2 = Images.bomberMan_Back2;
        back3 = Images.bomberMan_Back3;
        back4 = Images.bomberMan_Back4;
        left1 = Images.bomberMan_Left1;
        left2 = Images.bomberMan_Left2;
        left3 = Images.bomberMan_Left3;
        left4 = Images.bomberMan_Left4;
        right1 = Images.bomberMan_Right1;
        right2 = Images.bomberMan_Right2;
        right3 = Images.bomberMan_Right3;
        right4 = Images.bomberMan_Right4;

        setImage(front1);
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
            case 3:
                switch (direction) {
                    case DOWN:
                        setImage(front4);
                        break;
                    case UP:
                        setImage(back4);
                        break;
                    case RIGHT:
                        setImage(right4);
                        break;
                    case LEFT:
                        setImage(left4);
                        break;
                }
                break;
        }
    }

    public boolean isRespawning() {
        return respawning;
    }


    public int getBombRange() {
        return bombRange;
    }

    public void setBombRange(int bombRange) {
        this.bombRange = bombRange;
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public void setBombLimit(int bombLimit) {
        this.bombLimit = bombLimit;
    }

    public int getScore() {
        if (score < 0) {
            failed = true;

            setScore(0);
            setBombControl(false);
            setBombLimit(1);
            setBombRange(1);
            setStep(5);
        }
        return score;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Bomb> getBobms() {
        return bobms;
    }

    public boolean isBombControl() {
        return bombControl;
    }

    public void setBombControl(boolean bombControl) {
        this.bombControl = bombControl;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public String getImageName() {
        if (getImage() == front1)
            return "Front-1";
        else if (getImage() == front2)
            return "Front-2";
        else if (getImage() == front3)
            return "Front-3";
        else if (getImage() == front4)
            return "Front-4";
        else if (getImage() == back1)
            return "Back-1";
        else if (getImage() == back2)
            return "Back-2";
        else if (getImage() == back3)
            return "Back-3";
        else if (getImage() == back4)
            return "Back-4";
        else if (getImage() == right1)
            return "Right-1";
        else if (getImage() == right2)
            return "Right-2";
        else if (getImage() == right3)
            return "Right-3";
        else if (getImage() == right4)
            return "Right-4";
        else if (getImage() == left1)
            return "Left-1";
        else if (getImage() == left2)
            return "Left-2";
        else if (getImage() == left3)
            return "Left-3";
        else if (getImage() == left4)
            return "Left-4";

        return null;
    }

    public void respawn() {
        if (!respawning) {
            Sound.NO.play(0);
            respawning = true;
            long date = new Date().getTime();
            Cell goodCell = gameServer.getGoodCell();
            setX(goodCell.getX());
            setY(goodCell.getY());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (new Date().getTime() - date < GameConfiguration.RESPAWN_TIME) {
                        getMap().getGamePanel().repaint();
                    }
                    respawning = false;
                }
            }).start();
            setBombControl(false);
            setBombLimit(1);
            setBombRange(1);
            setStep(5);
            setGhostAbility(false);
//            getMap().getScoreBoard().update();
            setScore(getScore() - gameServer.getLevelNumber() * 100);
        }
    }

    public Composite getRespawnComposite() {
        respawnCounter++;
        switch (respawnCounter % 2) {
            case 0:
                return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F);
            default:
                return null;
        }
    }

    @Override
    public void paint(Graphics2D g2) {
        Composite oldComposite = g2.getComposite();
        if (respawning) {
            Composite c = getRespawnComposite();
            if (c != null)
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        }
        g2.drawImage(getImage(),
                getX(), getY(), new Color(0, 0, 0, 0), null);

        g2.setComposite(oldComposite);
    }

    public boolean isCellFree(Direction direction) throws GoToWallAndBlockCellException, GoOutOfMapException {
        Cell finalCell1 = null;
        Cell finalCell2 = null;
        int count = 0;
        switch (direction) {
            case LEFT:
                count = 0;
                finalCell1 = getMap().getCell(getX() - getStep(), getY());
                if (finalCell1 == null) {
                    throw new GoOutOfMapException();
                }
                finalCell2 = getMap().getCell(getX() - getStep(), getY() + getHeight() - getStep());
                if (finalCell2 == null) {
                    throw new GoOutOfMapException();
                }
                break;
            case RIGHT:
                finalCell1 = getMap().getCell(getX() + getWidth(), getY());
                if (finalCell1 == null) {
                    throw new GoOutOfMapException();
                }
                finalCell2 = getMap().getCell(getX() + getWidth(), getY() + getHeight() - getStep());
                if (finalCell2 == null) {
                    throw new GoOutOfMapException();
                }
                break;
            case UP:
                finalCell1 = getMap().getCell(getX(), getY() - getStep());
                if (finalCell1 == null) {
                    throw new GoOutOfMapException();
                }
                finalCell2 = getMap().getCell(getX() + getWidth() - getStep(), getY() - getStep());
                if (finalCell2 == null) {
                    throw new GoOutOfMapException();
                }
                break;
            case DOWN:
                finalCell1 = getMap().getCell(getX(), getY() + getHeight());
                if (finalCell1 == null) {
                    throw new GoOutOfMapException();
                }
                finalCell2 = getMap().getCell(getX() + getWidth() - getStep(), getY() + getHeight());
                if (finalCell2 == null) {
                    throw new GoOutOfMapException();
                }
                break;
        }
        if ((finalCell1.getType().equals(Cell.CellTypes.EMPTY)) ||
                (getCell().getType() == Cell.CellTypes.BOMB &&
                        finalCell1.getType().equals(Cell.CellTypes.BOMB)) ||
                ((isGhostAbility() && !finalCell1.getType().equals(Cell.CellTypes.BOMB)) ||
                        (isGhostAbility() && finalCell1.getType().equals(Cell.CellTypes.BOMB) &&
                                getCell().getType().equals(Cell.CellTypes.BOMB)))) {
            count++;
        }
        if ((finalCell2.getType().equals(Cell.CellTypes.EMPTY)) ||
                (getCell().getType() == Cell.CellTypes.BOMB &&
                        finalCell2.getType().equals(Cell.CellTypes.BOMB)) ||
                ((isGhostAbility() && !finalCell2.getType().equals(Cell.CellTypes.BOMB)) ||
                        (isGhostAbility() && finalCell2.getType().equals(Cell.CellTypes.BOMB) &&
                                getCell().getType().equals(Cell.CellTypes.BOMB)))) {
            count++;
        }
        if (count == 2)
            return true;
        throw new GoToWallAndBlockCellException();
    }

    @Override
    public void move(Direction direction) {
        super.move(direction);
        findItem();
    }

    public void findItem() {
        Cell c = getMap().getCell(getCx(), getCy());
        if (c.getItem() != null) {
            c.getItem().act(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void respawningStart() {
        respawning = true;
        long date = new Date().getTime();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (new Date().getTime() - date < GameConfiguration.STARTING_RESPAWN_TIME) {
                    getMap().getGamePanel().repaint();
                }
                respawning = false;
            }
        }).start();
    }

    public boolean isGhostAbility() {
        return ghostAbility;
    }

    public void setGhostAbility(boolean ghostAbility) {
        this.ghostAbility = ghostAbility;
    }
}
