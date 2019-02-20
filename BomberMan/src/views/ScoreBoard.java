package views;

import Models.BomberMan;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ScoreBoard extends JDialog {
    private GameFrame gameFrame;
    private JLabel scoreLabel;
    private JLabel scoreLbl;
    private JLabel bombsLbl;
    private JLabel radiusLbl;
    private JLabel controlBombLbl;
    private JLabel speedLbl;

    private Image bomb1;
    private Image bomb2;
    private Image bomb3;
    private Image bomb4;
    private Image bomb5;
    private Image radius1;
    private Image radius2;
    private Image radius3;
    private Image radius4;
    private Image radius5;
    private Image controlBomb1;
    private Image controlBomb2;
    private Image speed1;
    private Image speed2;

    public ScoreBoard(JFrame parent) {
        this.gameFrame = (GameFrame) parent;
        int width = 100;
        int height = 425;
        setSize(width, height);
        setResizable(false);
        setLayout(null);
        setUndecorated(true);
        setLocation((int) getLocation().getX() - getWidth(), (int) getLocation().getY() + (2 * parent.getInsets().top));


        scoreLabel = new JLabel("Score ");
        scoreLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        scoreLbl = new JLabel();
        scoreLbl.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        bombsLbl = new JLabel();
        radiusLbl = new JLabel();
        controlBombLbl = new JLabel();
        speedLbl = new JLabel();

        try {
            bomb1 = ImageIO.read(new File("src/Images/Items/ScoreBoard/bomb1.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            bomb2 = ImageIO.read(new File("src/Images/Items/ScoreBoard/bomb2.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            bomb3 = ImageIO.read(new File("src/Images/Items/ScoreBoard/bomb3.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            bomb4 = ImageIO.read(new File("src/Images/Items/ScoreBoard/bomb4.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            bomb5 = ImageIO.read(new File("src/Images/Items/ScoreBoard/bomb5.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            radius1 = ImageIO.read(new File("src/Images/Items/ScoreBoard/radius1.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            radius2 = ImageIO.read(new File("src/Images/Items/ScoreBoard/radius2.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            radius3 = ImageIO.read(new File("src/Images/Items/ScoreBoard/radius3.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            radius4 = ImageIO.read(new File("src/Images/Items/ScoreBoard/radius4.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            radius5 = ImageIO.read(new File("src/Images/Items/ScoreBoard/radius5.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            controlBomb1 = ImageIO.read(new File("src/Images/Items/ScoreBoard/controlBombs0.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            controlBomb2 = ImageIO.read(new File("src/Images/Items/ScoreBoard/controlBombs1.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            speed1 = ImageIO.read(new File("src/Images/Items/ScoreBoard/speed0.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            speed2 = ImageIO.read(new File("src/Images/Items/ScoreBoard/speed1.png"))
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bombsLbl.setIcon(new ImageIcon(bomb1));
        radiusLbl.setIcon(new ImageIcon(radius1));
        controlBombLbl.setIcon(new ImageIcon(controlBomb1));
        speedLbl.setIcon(new ImageIcon(speed1));

        scoreLabel.setBounds(30, 15, 60, 30);
        add(scoreLabel);
        scoreLbl.setBounds(45, 30, 50, 50);
        add(scoreLbl);
        bombsLbl.setBounds(15, 85, 70, 70);
        add(bombsLbl);
        radiusLbl.setBounds(15, 170, 70, 70);
        add(radiusLbl);
        controlBombLbl.setBounds(15, 255, 70, 70);
        add(controlBombLbl);
        speedLbl.setBounds(15, 340, 70, 70);
        add(speedLbl);

        update();
    }

    public void setBombIcon(int bombCount) {
        switch (bombCount) {
            case 1:
                bombsLbl.setIcon(new ImageIcon(bomb1));
                break;
            case 2:
                bombsLbl.setIcon(new ImageIcon(bomb2));
                break;
            case 3:
                bombsLbl.setIcon(new ImageIcon(bomb3));
                break;
            case 4:
                bombsLbl.setIcon(new ImageIcon(bomb4));
                break;
            case 5:
                bombsLbl.setIcon(new ImageIcon(bomb5));
                break;
        }
    }

    public void setRadiusIcon(int radius) {
        switch (radius) {
            case 1:
                radiusLbl.setIcon(new ImageIcon(radius1));
                break;
            case 2:
                radiusLbl.setIcon(new ImageIcon(radius2));
                break;
            case 3:
                radiusLbl.setIcon(new ImageIcon(radius3));
                break;
            case 4:
                radiusLbl.setIcon(new ImageIcon(radius4));
                break;
            case 5:
                radiusLbl.setIcon(new ImageIcon(radius5));
                break;
        }
    }

    public void setControlBombIcon(boolean control) {
        if (control)
            controlBombLbl.setIcon(new ImageIcon(controlBomb2));
        else
            controlBombLbl.setIcon(new ImageIcon(controlBomb1));
    }

    public void setSpeedIcon(int speed) {
        if (speed == 10)
            speedLbl.setIcon(new ImageIcon(speed2));
        else
            speedLbl.setIcon(new ImageIcon(speed1));
    }

    public void updateScore(int score) {
        if (score == 0 || Math.log10(score) + 1 == 1) {
            scoreLbl.setBounds(45, 30, 50, 50);
        } else if ((int) (Math.log10(score)) + 1 == 2) {
            scoreLbl.setBounds(42, 30, 50, 50);
        } else if ((int) (Math.log10(score)) + 1 == 3) {
            scoreLbl.setBounds(39, 30, 50, 50);
        } else {
            scoreLbl.setBounds(36, 30, 50, 50);
        }
        scoreLbl.setText("" + score);
    }

    public void update() {
        BomberMan bomberman = gameFrame.getBomberMan();
        updateScore(bomberman.getScore());
        setBombIcon(bomberman.getBombLimit());
        setRadiusIcon(bomberman.getBombRange());
        setControlBombIcon(bomberman.isBombControl());
        setSpeedIcon(bomberman.getStep());

    }
}
