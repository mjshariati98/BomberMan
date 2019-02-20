package views;

import Models.BomberMan;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class NewLevelDialog extends JDialog{
    private JButton okBtn;
    private JLabel lbl;
    private JLabel scoreLbl;
    private JLabel imageLbl;
    private JLabel timeLbl;
    private JLabel lbl2;

    public NewLevelDialog(JFrame parent, BomberMan bomberMan) {
        setSize(300, 205);
        setLocationRelativeTo(parent);
        setResizable(false);
        setTitle("Next Level");
        setLayout(null);

        lbl = new JLabel("Your Score : ");
        lbl2 = new JLabel("You spent this level in ");
        scoreLbl = new JLabel("" + bomberMan.getScore());
        timeLbl = new JLabel(((GameFrame) parent).getStringTime());
        okBtn = new JButton("Next Level");
        imageLbl = new JLabel();

        Font font = new Font(Font.SERIF, Font.PLAIN, 16);
        lbl.setFont(font);
        scoreLbl.setFont(font);
        lbl2.setFont(font);
        timeLbl.setFont(font);

        lbl.setBounds(145, 23, 100, 30);
        scoreLbl.setBounds(240, 23, 50, 30);
        lbl2.setBounds(125, 60, 150, 30);
        if (((GameFrame) parent).getStringTime().length() < 5) {
            timeLbl.setBounds(185, 95, 100, 30);
        } else {
            timeLbl.setBounds(155, 95, 100, 30);

        }
        okBtn.setBounds(145, 140, 100, 30);
        imageLbl.setBounds(20, 20, 85, 120);
        Image image = null;
        try {
            image = ImageIO.read(new File("src/Images/bomb-dialog.png"))
                    .getScaledInstance(70, 120, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(image);
        imageLbl.setIcon(icon);
        add(lbl);
        add(scoreLbl);
        add(lbl2);
        add(timeLbl);
        add(okBtn);
        add(imageLbl);

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame oldGameFrame = (GameFrame) parent;
                oldGameFrame.getLevelManager().newLevel();
                setVisible(false);
            }
        });

        setVisible(true);
    }
}
