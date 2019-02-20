package views;

import Models.LevelManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DataBaseLoadingFrame extends JFrame {
    private JPanel scrollPanel;
    private JScrollPane scrollPane;

    public DataBaseLoadingFrame(int[] IDs, String[] names, int size,
                                LevelManager levelManager, Connection connection) {
        setSize(290, 400);
        setLayout(null);

        scrollPanel = new JPanel();
        scrollPanel.setLayout(null);
        scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setBounds(0, 0, getWidth(), getHeight());
        add(scrollPane);

        scrollPanel.setPreferredSize(new Dimension(getWidth(), size * 43));

        for (int i = 1; i < size; i++) {
            JLabel id = new JLabel(IDs[i] + ".");
            JLabel name = new JLabel(names[i]);
            JButton loadBtn = new JButton("Load");

            id.setBounds(10, i * 40 - 15, 40, 40);
            name.setBounds(40, i * 40 - 15, 140, 40);
            loadBtn.setBounds(200, i * 40 - 10, 60, 33);

            final int x = i;
            loadBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    levelManager.loadDB(x, connection);
                    setVisible(false);
                }
            });

            scrollPanel.add(id);
            scrollPanel.add(name);
            scrollPanel.add(loadBtn);

        }
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (windowSize.getWidth() - this.getWidth()) / 2;
        int y = (int) (windowSize.getHeight() - this.getHeight()) / 2;
        setLocation(x,y);
        setResizable(false);
        setVisible(true);
    }
}