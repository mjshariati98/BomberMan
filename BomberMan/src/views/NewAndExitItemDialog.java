package views;

import Configs.Sound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class NewAndExitItemDialog extends JDialog {
    private GameFrame gameFrame;
    private JLabel lbl;
    private JLabel imageLabel;
    private JButton saveBtn;
    private JButton dontSaveBtn;
    private JButton cancelBtn;
    private boolean exit;
    private JFileChooser fileChooser;

    public NewAndExitItemDialog(JFrame parent, boolean exit) {
        super(parent, "", false);
        gameFrame = (GameFrame) parent;
        fileChooser = new JFileChooser();
        if (exit) {
            setTitle("Exit");
        } else {
            setTitle("New Game");
        }
        this.exit = exit;
        setSize(400, 230);
        setLayout(null);
        setLocationRelativeTo(parent);
        setResizable(false);

        lbl = new JLabel("Do you want to save the game?");
        imageLabel = new JLabel();
        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");
        dontSaveBtn = new JButton("Don't Save");

        imageLabel.setBounds(20, 20, 120, 120);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/Images/BM-Dialog.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image resizeIMG = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(resizeIMG);
        imageLabel.setIcon(imageIcon);
        add(imageLabel);

        dontSaveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ((GameFrame)parent).dispose();

                if (!exit) {
                    new StartFrame();
                } else {
                    System.exit(0);
                }

                Sound.stopAll();
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileOutputStream fileOutputStream = null;
                if (gameFrame.isSaved()) {
                    try {
                        File file = new File(gameFrame.getSavePath());
                        if (!file.toString().endsWith(".bomberman")) {
                            fileOutputStream = new FileOutputStream(file + ".bomberman", false);
                        } else {
                            fileOutputStream = new FileOutputStream(file, false);
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    fileChooser.showSaveDialog(NewAndExitItemDialog.this);
                    try {
                        File file = new File(fileChooser.getSelectedFile().getPath());
                        if (!file.toString().endsWith(".bomberman")) {
                            fileOutputStream = new FileOutputStream(
                                    file + ".bomberman");
                        } else {
                            fileOutputStream = new FileOutputStream(
                                    file);
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
                gameFrame.getLevelManager().save(fileOutputStream);

                setVisible(false);
                ((GameFrame)parent).dispose();
                if (!exit) {
                    StartFrame startFrame = new StartFrame();
                } else {
                    System.exit(0);
                }

                Sound.stopAll();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        lbl.setBounds(170, 55, 250, 50);

        add(lbl);
        dontSaveBtn.setBounds(20, 160, 110, 30);

        add(dontSaveBtn);
        cancelBtn.setBounds(300, 160, 90, 30);

        add(cancelBtn);
        saveBtn.setBounds(200, 160, 90, 30);

        add(saveBtn);

        setVisible(true);

    }
}
