package views;

import Configs.Sound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GameOverFrame extends JFrame {
    private JLabel imageLbl1;
    private JLabel imageLbl2;
    private JButton newGameBtn;
    private JButton exitBtn;

    public GameOverFrame(JFrame parent) {
        ((GameFrame) parent).setRun(false);
        ((GameFrame) parent).setEnabled(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(null);
        setResizable(false);

        imageLbl1 = new JLabel();
        imageLbl2 = new JLabel();
        newGameBtn = new JButton("New Game");
        exitBtn = new JButton("Exit");
        Image image1 = null;
        Image image2 = null;
        try {
            image1 = ImageIO.read(new File("src/Images/gameOver1.png"))
                    .getScaledInstance(220, 90, Image.SCALE_SMOOTH);
            image2 = ImageIO.read(new File("src/Images/gameOver2.png"))
                    .getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon1 = new ImageIcon(image1);
        ImageIcon icon2 = new ImageIcon(image2);
        imageLbl1.setIcon(icon1);
        imageLbl2.setIcon(icon2);

        imageLbl1.setBounds(70, 20, 220, 90);
        imageLbl2.setBounds(5, 5, 90, 90);
        newGameBtn.setBounds(40, 125, 100, 35);
        exitBtn.setBounds(190, 125, 70, 35);

        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                parent.dispose();
                StartFrame startFrame = new StartFrame();
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(imageLbl1);
        add(imageLbl2);
        add(newGameBtn);
        add(exitBtn);

        setVisible(true);
        Sound.GAMEOVER.play(0);
    }
}
