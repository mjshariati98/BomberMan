package views;

import Configs.GameConfiguration;
import Configs.Sound;
import Models.LevelManager;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsDialog extends JDialog {
    private LevelManager levelManager;
    private JPanel bombermanPanel;
    private JPanel monstersPanel;
    private JPanel gamePanel;
    private JButton applyBtn;

    public SettingsDialog(JFrame parent) {
        setSize(330, 500);
        setLocationRelativeTo(parent);
        setLayout(null);
        setResizable(false);

        bombermanPanel = new JPanel();
        bombermanPanel.setSize(290, 165);
        bombermanPanel.setLayout(null);
        bombermanPanel.setBorder(BorderFactory.createTitledBorder("BomberMan"));
        bombermanPanel.setBounds(20, 20, 290, 165);
        JLabel bombLimit = new JLabel("Initial Bomb Limit : ");
        JLabel bombrange = new JLabel("Initial Bomb Range : ");
        JLabel control = new JLabel("Initial Control Bomb : ");
        JLabel speed = new JLabel("Initial Speed : ");
        JLabel max = new JLabel("( max : 5 )");
        JLabel max2 = new JLabel("( max : 5 )");
        JLabel min = new JLabel("min");
        JLabel maxx = new JLabel("max");
        JTextField bombLimitText = new JTextField("1");
        bombLimitText.setHorizontalAlignment(0);
        JTextField bombRangeText = new JTextField("1");
        bombRangeText.setHorizontalAlignment(0);
        JCheckBox controlBombCheckBox = new JCheckBox();
        controlBombCheckBox.setSelected(false);
        JRadioButton minSpeed = new JRadioButton();
        JRadioButton maxSpeed = new JRadioButton();
        ButtonGroup speedGroup = new ButtonGroup();
        speedGroup.add(minSpeed);
        speedGroup.add(maxSpeed);
        minSpeed.setSelected(true);
        minSpeed.setActionCommand("min");
        bombLimit.setBounds(20, 30, 150, 20);
        bombrange.setBounds(20, 60, 150, 20);
        control.setBounds(20, 90, 150, 20);
        speed.setBounds(20, 120, 150, 20);
        bombLimitText.setBounds(155, 30, 50, 20);
        bombRangeText.setBounds(155, 60, 50, 20);
        max.setBounds(210, 30, 80, 20);
        max2.setBounds(210, 60, 80, 20);
        controlBombCheckBox.setBounds(160, 80, 40, 40);
        min.setBounds(140, 120, 40, 20);
        minSpeed.setBounds(170, 110, 40, 40);
        maxx.setBounds(220, 120, 40, 20);
        maxSpeed.setBounds(250, 110, 40, 40);
        bombermanPanel.add(bombLimit);
        bombermanPanel.add(bombrange);
        bombermanPanel.add(control);
        bombermanPanel.add(speed);
        bombermanPanel.add(bombLimitText);
        bombermanPanel.add(bombRangeText);
        bombermanPanel.add(max);
        bombermanPanel.add(max2);
        bombermanPanel.add(controlBombCheckBox);
        bombermanPanel.add(min);
        bombermanPanel.add(minSpeed);
        bombermanPanel.add(maxx);
        bombermanPanel.add(maxSpeed);
        add(bombermanPanel);

        monstersPanel = new JPanel();
        monstersPanel.setSize(290, 135);
        monstersPanel.setBounds(20, 200, 290, 135);
        monstersPanel.setBorder(BorderFactory.createTitledBorder("Monsters"));
        monstersPanel.setLayout(null);
        JLabel number = new JLabel("Number of monsters : ");
        JLabel minRowColumn = new JLabel("Min of rows and columns ");
        JLabel initialNumber = new JLabel("Initial number : ");
        initialNumber.setEnabled(false);
        JTextField enemyNumbers = new JTextField();
        enemyNumbers.setHorizontalAlignment(0);
        enemyNumbers.setEnabled(false);
        JCheckBox monstersCheckBox = new JCheckBox();
        monstersCheckBox.setSelected(true);
        number.setBounds(20, 30, 150, 20);
        minRowColumn.setBounds(40, 60, 200, 20);
        monstersCheckBox.setBounds(210, 52, 40, 40);
        initialNumber.setBounds(40, 90, 150, 20);
        enemyNumbers.setBounds(150, 90, 50, 20);
        monstersPanel.add(number);
        monstersPanel.add(minRowColumn);
        monstersPanel.add(initialNumber);
        monstersPanel.add(enemyNumbers);
        monstersPanel.add(monstersCheckBox);
        monstersCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!monstersCheckBox.isSelected()) {
                    enemyNumbers.setEnabled(true);
                    initialNumber.setEnabled(true);
                } else {
                    enemyNumbers.setEnabled(false);
                    initialNumber.setEnabled(false);
                }
            }
        });
        add(monstersPanel);

        gamePanel = new JPanel();
        gamePanel.setLayout(null);
        gamePanel.setSize(290, 70);
        gamePanel.setBounds(20, 350, 290, 70);
        gamePanel.setBorder(BorderFactory.createTitledBorder("Game"));
        JLabel sound = new JLabel("Sound : ");
        JCheckBox soundCheckBox = new JCheckBox();
        soundCheckBox.setSelected(true);
        sound.setBounds(20, 30, 80, 20);
        soundCheckBox.setBounds(80, 20, 40, 40);
        gamePanel.add(sound);
        gamePanel.add(soundCheckBox);
        add(gamePanel);

        applyBtn = new JButton("Apply");
        applyBtn.setBounds(115, 430, 100, 40);
        add(applyBtn);
        applyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sound.SELECT.play(0);
                GameConfiguration.SOUND = soundCheckBox.isSelected();
                if (GameConfiguration.SOUND) {
                    if (levelManager != null) {
                        Sound.MEGALOBOX.play(Clip.LOOP_CONTINUOUSLY);
                    }
                } else {
                    Sound.stopAll();
                }
                GameConfiguration.INITIAL_BOMB_LIMIT = Integer.parseInt(bombLimitText.getText());
                GameConfiguration.INITIAL_BOMB_RANGE = Integer.parseInt(bombRangeText.getText());
                GameConfiguration.INITIAL_CONTROL_BOMB = controlBombCheckBox.isSelected();
                if (maxSpeed.isSelected())
                    GameConfiguration.BOMBERMAN_INITIAL_STEP = 10;
                if (!monstersCheckBox.isSelected()) {
                    GameConfiguration.MONSTER_NUMBERS = Integer.parseInt(enemyNumbers.getText());
                }
                setVisible(false);
                if (levelManager != null) {
                    levelManager.getGameFrame().setVisible(true);
                }
            }
        });

    }

    public void setLevelManager(LevelManager levelManager) {
        this.levelManager = levelManager;
    }
}
