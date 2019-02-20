package views;

import Configs.GameConfiguration;
import utils.Direction;

import javax.swing.*;

public class FrontPanel extends JPanel {
    protected int x = 0;
    protected int y = 0;
    private int width;
    private int height;
    private GamePanel gamePanel;

    public FrontPanel(GamePanel gamePanel, int width, int height) {
        this.gamePanel = gamePanel;
        this.width = width;
        this.height = height;
        setLayout(null);

        gamePanel.setBounds(x, y, this.width, this.height);
        add(gamePanel);
    }

    public void setBoundsOfGamePanel(Direction direction) {
        int step = gamePanel.getBomberMan().getStep();
        switch (direction) {
            case LEFT:
                if (GameConfiguration.GAME_FRAME_WIDTH - x < width) {
                    this.x -= step;
                }
                break;
            case RIGHT:
                if (x < 0) {
                    this.x += step;
                }
                break;
            case UP:
                if (GameConfiguration.GAME_FRAME_HEIGHT - y < height + GameConfiguration.BOMBERMAN_HEIGHT) {
                    this.y -= step;
                }
                break;
            case DOWN:
                if (y < 0) {
                    this.y += step;
                }
                break;
        }
        gamePanel.setBounds(this.x, this.y, this.width, this.height);
    }

    public void setRespawnGamePanel() {
        this.x = 0;
        this.y = 0;
        gamePanel.setBounds(this.x, this.y, this.width, this.height);
    }

    public void setBoundsPosition(int x, int y) {
        if (x > GameConfiguration.GAME_FRAME_WIDTH / 2 &&
                gamePanel.getMap().getWidth() > GameConfiguration.GAME_FRAME_WIDTH) {
            if (gamePanel.getBomberMan().getX() < gamePanel.getMap().getWidth() - GameConfiguration.GAME_FRAME_WIDTH) {
                this.x = -(gamePanel.getBomberMan().getX() - GameConfiguration.GAME_FRAME_WIDTH / 2);
            } else {
                this.x = -(gamePanel.getMap().getWidth() - GameConfiguration.GAME_FRAME_WIDTH);
            }

        } else {
            this.x = 0;
        }
        if (y > GameConfiguration.GAME_FRAME_HEIGHT / 2 &&
                gamePanel.getMap().getHeight() > GameConfiguration.GAME_FRAME_HEIGHT) {
            if (gamePanel.getBomberMan().getY() < gamePanel.getMap().getHeight() - GameConfiguration.GAME_FRAME_HEIGHT) {
                this.y = -(gamePanel.getBomberMan().getY() - GameConfiguration.GAME_FRAME_HEIGHT / 2);
            } else {
                this.y = -(gamePanel.getMap().getMainHeight()- GameConfiguration.GAME_FRAME_HEIGHT);
            }
        } else {
            this.y = 0;
        }
        gamePanel.setBounds(this.x, this.y, this.width, this.height);
    }

    public void setWidth(int width) {
        this.width = width;
        gamePanel.setBounds(x, y, this.width, this.height);
    }

    public void setHeight(int height) {
        this.height = height;
        gamePanel.setBounds(x, y, this.width, this.height);
    }


}
