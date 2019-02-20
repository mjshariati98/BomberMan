package views;

import Models.BomberMan;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PointsFrame extends JFrame {
    private JLabel points;
    private JLabel nameLbl;
    private JLabel scoreLbl;
    private JPanel scrollPanel;
    private JScrollPane scrollPane;

    public PointsFrame(int topInset) {
        setLayout(null);
        setSize(300, 400 + topInset);

        points = new JLabel("Points");
        points.setFont(new Font(Font.SERIF, Font.PLAIN, 22));
        points.setHorizontalTextPosition(0);
        points.setBounds(120, 5, 150, 40);
        add(points);

        nameLbl = new JLabel("Username");
        scoreLbl = new JLabel("Score");
        nameLbl.setBounds(40, 40, 100, 30);
        scoreLbl.setBounds(220, 40, 100, 30);
        add(nameLbl);
        add(scoreLbl);

        scrollPanel = new JPanel();
        scrollPanel.setLayout(null);
        scrollPanel.setPreferredSize(new Dimension(getWidth() - 30, getHeight() - topInset - 85));
        scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        scrollPane.setBounds(15, 70, getWidth() - 30, getHeight() - topInset - 80);
        add(scrollPane);
    }

    public void update(List<BomberMan> bomberManList) {
        scrollPanel.removeAll();
        int i = 0;
        for (BomberMan bomberMan : bomberManList) {
            JLabel name = new JLabel(bomberMan.getName());
            JLabel score = new JLabel("" + bomberMan.getScore());

            name.setBounds(15, i * 30 + 15, 150, 30);
            score.setBounds(210, i * 30 + 15, 100, 30);

            scrollPanel.add(name);
            scrollPanel.add(score);
            i++;
        }
        scrollPanel.setPreferredSize(new Dimension(getWidth() - 30, i * 32));
        scrollPanel.updateUI();
    }
}
