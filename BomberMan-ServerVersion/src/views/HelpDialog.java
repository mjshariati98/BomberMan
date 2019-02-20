package views;

import Configs.GameConfiguration;
import Configs.Images;
import Models.Items.*;

import javax.swing.*;
import java.awt.*;

public class HelpDialog extends JDialog {
    int count = 0;
    private JLabel whiteMonsterLbl;
    private JLabel purpleMonsterLbl;
    private JLabel yellowMonsterLbl;
    private JLabel blackMonsterLbl;
    private Icon yellowIcon1;
    private Icon yellowIcon2;
    private Icon yellowIcon3;
    private Icon whiteIcon1;
    private Icon whiteIcon2;
    private Icon whiteIcon3;
    private Icon blackIcon1;
    private Icon blackIcon2;
    private Icon blackIcon3;
    private Icon purpleIcon1;
    private Icon purpleIcon2;
    private Icon purpleIcon3;

    public HelpDialog(JFrame parent) {
        int width = GameConfiguration.BOMBERMAN_WIDTH;
        int height = GameConfiguration.BOMBERMAN_HEIGHT;
        setSize(720, 350);
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
        setLayout(null);

        whiteMonsterLbl = new JLabel();
        purpleMonsterLbl = new JLabel();
        yellowMonsterLbl = new JLabel();
        blackMonsterLbl = new JLabel();
        whiteMonsterLbl.setBounds(20, 20, width, height);
        purpleMonsterLbl.setBounds(20, 100, width, height);
        yellowMonsterLbl.setBounds(20, 180, width, height);
        blackMonsterLbl.setBounds(20, 260, width, height);

        yellowIcon1 = new ImageIcon(Images.monster1_Front1);
        yellowIcon2 = new ImageIcon(Images.monster1_Front2);
        yellowIcon3 = new ImageIcon(Images.monster1_Front3);
        whiteIcon1 = new ImageIcon(Images.monster2_Front1);
        whiteIcon2 = new ImageIcon(Images.monster2_Front2);
        whiteIcon3 = new ImageIcon(Images.monster2_Front3);
        purpleIcon1 = new ImageIcon(Images.monster3_Front1);
        purpleIcon2 = new ImageIcon(Images.monster3_Front2);
        purpleIcon3 = new ImageIcon(Images.monster3_Front3);
        blackIcon1 = new ImageIcon(Images.monster4_Front1);
        blackIcon2 = new ImageIcon(Images.monster4_Front2);
        blackIcon3 = new ImageIcon(Images.monster4_Front3);

        add(yellowMonsterLbl);
        add(whiteMonsterLbl);
        add(purpleMonsterLbl);
        add(blackMonsterLbl);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isVisible()) {
                    walk(count++);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        JLabel levelLbl1 = new JLabel("Enemy Level 1");
        JLabel levelLbl2 = new JLabel("Enemy Level 2");
        JLabel levelLbl3 = new JLabel("Enemy Level 3");
        JLabel levelLbl4 = new JLabel("Enemy Level 4");

        levelLbl1.setBounds(70, 30, 100, 20);
        levelLbl2.setBounds(70, 110, 100, 20);
        levelLbl3.setBounds(70, 190, 100, 20);
        levelLbl4.setBounds(70, 270, 100, 20);

        add(levelLbl1);
        add(levelLbl2);
        add(levelLbl3);
        add(levelLbl4);

        JLabel increaseSpeedImageLbl = new JLabel();
        JLabel decreaseSpeedImageLbl = new JLabel();
        JLabel increaseScoreImageLbl = new JLabel();
        JLabel decreaseScoreImageLbl = new JLabel();
        JLabel doorImageLbl = new JLabel();
        JLabel increaseBombsImageLbl = new JLabel();
        JLabel increaseRadiusImageLbl = new JLabel();
        JLabel decreaseRadiusImageLbl = new JLabel();
        JLabel decreaseBombsImageLbl = new JLabel();
        JLabel controlBombsImageLbl = new JLabel();

        JLabel increaseSpeedLbl = new JLabel("Increase Speed");
        JLabel decreaseSpeedLbl = new JLabel("Decrease Speed");
        JLabel increaseScoreLbl = new JLabel("Increase Score (100)");
        JLabel decreaseScoreLbl = new JLabel("Decrease Score (100)");
        JLabel doorLbl = new JLabel("Door ");
        JLabel increaseBombsLbl = new JLabel("Increase Bombs Limit");
        JLabel decreaseBombsLbl = new JLabel("Decrease Bombs Limit");
        JLabel increaseRaiusLbl = new JLabel("Increase Bombs Range");
        JLabel decreaseRadiusLbl = new JLabel("Decrease Bombs Range");
        JLabel controlBombsLbl = new JLabel("Control Bombs with Space");

        IncreaseSpeed increaseSpeed = new IncreaseSpeed(null, null);
        Image iSpeed = increaseSpeed.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon iSpeedIcon = new ImageIcon(iSpeed);
        increaseSpeedImageLbl.setIcon(iSpeedIcon);
        DecreseSpeed decreseSpeed = new DecreseSpeed(null, null);
        Image dSpeed = decreseSpeed.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon dSpeedIcon = new ImageIcon(dSpeed);
        decreaseSpeedImageLbl.setIcon(dSpeedIcon);
        IncreaseScore increaseScore = new IncreaseScore(null, null);
        Image iScore = increaseScore.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon iScoreIcon = new ImageIcon(iScore);
        increaseScoreImageLbl.setIcon(iScoreIcon);
        DecreaseScore decreaseScore = new DecreaseScore(null, null);
        Image dScore = decreaseScore.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon dScoreIcon = new ImageIcon(dScore);
        decreaseScoreImageLbl.setIcon(dScoreIcon);
        Door door = new Door(null, null, null);
        Image doorImage = door.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon doorIcon = new ImageIcon(doorImage);
        doorImageLbl.setIcon(doorIcon);
        IncreaseBombs increseBombs = new IncreaseBombs(null, null);
        Image iBombs = increseBombs.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon iBombsIcon = new ImageIcon(iBombs);
        increaseBombsImageLbl.setIcon(iBombsIcon);
        DecreaseBombs decreaseBombs = new DecreaseBombs(null, null);
        Image dBombs = decreaseBombs.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon dBombsIcon = new ImageIcon(dBombs);
        decreaseBombsImageLbl.setIcon(dBombsIcon);
        IncreaseRadius increaseRadius = new IncreaseRadius(null, null);
        Image iRadius = increaseRadius.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon iRadiusIcon = new ImageIcon(iRadius);
        increaseRadiusImageLbl.setIcon(iRadiusIcon);
        DecreaseRadius decreaseRadius = new DecreaseRadius(null, null);
        Image dRadius = decreaseRadius.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon dRadiusIcon = new ImageIcon(dRadius);
        decreaseRadiusImageLbl.setIcon(dRadiusIcon);
        ControlBombs controlBombs = new ControlBombs(null, null);
        Image cBombs = controlBombs.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon cBombsIcon = new ImageIcon(cBombs);
        controlBombsImageLbl.setIcon(cBombsIcon);

        increaseSpeedImageLbl.setBounds(200, 20, 40, 40);
        increaseSpeedLbl.setBounds(280, 30, 150, 20);
        decreaseSpeedImageLbl.setBounds(200, 80, 40, 40);
        decreaseSpeedLbl.setBounds(280, 90, 150, 20);
        increaseScoreImageLbl.setBounds(200, 140, 40, 40);
        increaseScoreLbl.setBounds(280, 150, 150, 20);
        decreaseScoreImageLbl.setBounds(200, 200, 40, 40);
        decreaseScoreLbl.setBounds(280, 210, 150, 20);
        doorImageLbl.setBounds(200, 260, 40, 40);
        doorLbl.setBounds(280, 270, 150, 20);
        increaseBombsImageLbl.setBounds(450, 20, 40, 40);
        increaseBombsLbl.setBounds(530, 30, 150, 20);
        decreaseBombsImageLbl.setBounds(450, 80, 40, 40);
        decreaseBombsLbl.setBounds(530, 90, 150, 20);
        increaseRadiusImageLbl.setBounds(450, 140, 40, 40);
        increaseRaiusLbl.setBounds(530, 150, 150, 20);
        decreaseRadiusImageLbl.setBounds(450, 200, 40, 40);
        decreaseRadiusLbl.setBounds(530, 210, 150, 20);
        controlBombsImageLbl.setBounds(450, 260, 40, 40);
        controlBombsLbl.setBounds(530, 270, 170, 20);

        add(increaseSpeedImageLbl);
        add(increaseSpeedLbl);
        add(decreaseSpeedImageLbl);
        add(decreaseSpeedLbl);
        add(increaseScoreImageLbl);
        add(increaseScoreLbl);
        add(decreaseScoreImageLbl);
        add(decreaseScoreLbl);
        add(doorImageLbl);
        add(doorLbl);
        add(increaseBombsImageLbl);
        add(increaseBombsLbl);
        add(decreaseBombsImageLbl);
        add(decreaseBombsLbl);
        add(increaseRadiusImageLbl);
        add(increaseRaiusLbl);
        add(decreaseRadiusImageLbl);
        add(decreaseRadiusLbl);
        add(controlBombsImageLbl);
        add(controlBombsLbl);

    }

    private void walk(int count) {
        switch (count % 3) {
            case 0:
                yellowMonsterLbl.setIcon(yellowIcon1);
                whiteMonsterLbl.setIcon(whiteIcon1);
                purpleMonsterLbl.setIcon(purpleIcon1);
                blackMonsterLbl.setIcon(blackIcon1);
                break;
            case 1:
                yellowMonsterLbl.setIcon(yellowIcon2);
                whiteMonsterLbl.setIcon(whiteIcon2);
                purpleMonsterLbl.setIcon(purpleIcon2);
                blackMonsterLbl.setIcon(blackIcon2);
                break;
            case 2:
                yellowMonsterLbl.setIcon(yellowIcon3);
                whiteMonsterLbl.setIcon(whiteIcon3);
                purpleMonsterLbl.setIcon(purpleIcon3);
                blackMonsterLbl.setIcon(blackIcon3);
                break;
        }

    }
}
