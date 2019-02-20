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
        int step = 10;
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

}
