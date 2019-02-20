package views;

import javax.swing.*;
import java.awt.*;

public class PointsFrame extends JFrame {
    private JLabel nameLbl;
    private JLabel scoreLbl;
    private JPanel scrollPanel;
    private JScrollPane scrollPane;

    public PointsFrame() throws HeadlessException {
        setLayout(null);
        setSize(300,400);
        
        scrollPanel = new JPanel();
        scrollPanel.setLayout(null);
        scrollPane = new JScrollPane(scrollPanel);
    }
}
