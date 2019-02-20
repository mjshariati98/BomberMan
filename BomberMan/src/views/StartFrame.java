package views;

import Configs.GameConfiguration;
import Configs.Sound;
import Models.LevelManager;
import Network.ClientFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.sql.DriverManager.getConnection;

public class StartFrame extends JFrame {
    private static int ROW_DEFAULT = 10;
    private static int COLUMN_DEFAULT = 15;

    private JLabel row;
    private JLabel column;
    private JTextField rowField;
    private JTextField columnField;
    private JFileChooser fileChooser;
    private JButton singlePlayerBtn;
    private JButton multiPlayerBtn;
    private JLabel imageLabel;
    private LevelManager levelManager;
    private SettingsDialog settingsDialog;
    private boolean builtGame = false;

    public StartFrame() {
        setJMenuBar(menu());
        row = new JLabel("rows: ");
        column = new JLabel("columns: ");
        rowField = new JTextField(4);
        rowField.setText(Integer.toString(ROW_DEFAULT));
        columnField = new JTextField(4);
        columnField.setText(Integer.toString(COLUMN_DEFAULT));
        singlePlayerBtn = new JButton("Single Player");
        multiPlayerBtn = new JButton("Multi Player");
        imageLabel = new JLabel();

        settingsDialog = new SettingsDialog(StartFrame.this);

        setTitle("BomberMan");
        setLayout(null);

        imageLabel.setBounds(50, 20, 300, 100);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("src/Images/BM-StartFrame.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image resizeIMG = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(resizeIMG);
        imageLabel.setIcon(imageIcon);
        add(imageLabel);

        row.setBounds(110, 140, 100, 50);
        add(row);
        column.setBounds(110, 185, 100, 50);
        add(column);
        rowField.setBounds(200, 155, 100, 20);
        add(rowField);
        columnField.setBounds(200, 200, 100, 20);
        add(columnField);
        singlePlayerBtn.setBounds(50, 260, 150, 30);
        add(singlePlayerBtn);
        multiPlayerBtn.setBounds(200, 260, 150, 30);
        add(multiPlayerBtn);


        singlePlayerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sound.SELECT.play(0);

                if (!builtGame) {
                    builtGame = true;
                    int r = 15;
                    int c = 10;

                    if (!rowField.getText().equals("")) {
                        r = Integer.parseInt(rowField.getText());
                    } else {
                        JOptionPane.showMessageDialog(StartFrame.this, "rows default number: 10");
                        rowField.setText(Integer.toString(ROW_DEFAULT));
                    }

                    if (!columnField.getText().equals("")) {
                        c = Integer.parseInt(columnField.getText());
                    } else {
                        JOptionPane.showMessageDialog(StartFrame.this, "columns default number: 10");
                        columnField.setText(Integer.toString(COLUMN_DEFAULT));
                    }
                    int topInset = getInsets().top;
                    levelManager = new LevelManager(r, c, topInset);
                    levelManager.setSettingsDialog(settingsDialog);
                    settingsDialog.setLevelManager(levelManager);
                    GameConfiguration.LEVEL_TIME = (int) (Math.max(r, c) / 3);
                    setVisible(false);
                    levelManager.getGameFrame().setVisible(true);
                }
            }
        });

        multiPlayerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sound.SELECT.play(0);
                ClientFrame clientFrame = new ClientFrame();
                setVisible(false );

            }
        });

        setSize(400, 365);
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (windowSize.getWidth() - this.getWidth()) / 2;
        int y = (int) (windowSize.getHeight() - this.getHeight()) / 2;
        setLocation(x, y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        Sound.BOMBERMAN.play(0);
    }


    private JMenuBar menu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open...");
        JMenuItem loadDB = new JMenuItem("Load (DataBase)");
        JMenuItem settingsItem = new JMenuItem("Settings");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(loadDB);
        fileMenu.addSeparator();
        fileMenu.add(settingsItem);
        fileMenu.add(exitItem);
        helpMenu.add(helpItem);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Bomberman files (*.bomberman)", "bomberman");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileInputStream fileInputStream = null;
                fileChooser.showOpenDialog(StartFrame.this);
                try {
                    fileInputStream = new FileInputStream(
                            new File(fileChooser.getSelectedFile().getPath()));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                levelManager = new LevelManager();
                levelManager.load(fileInputStream, fileChooser.getSelectedFile().getPath());
                levelManager.setSettingsDialog(settingsDialog);
                settingsDialog.setLevelManager(levelManager);
                setVisible(false);
            }
        });
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rowField.setText(Integer.toString(ROW_DEFAULT));
                columnField.setText(Integer.toString(COLUMN_DEFAULT));
            }
        });
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelpDialog helpDialog = new HelpDialog(StartFrame.this);
            }
        });

        settingsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsDialog.setVisible(true);
            }
        });

        loadDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost/BomberMan";
                String user = "root";
                String password = "";
                Connection connection = null;
                levelManager = new LevelManager();
                levelManager.setSettingsDialog(settingsDialog);
                settingsDialog.setLevelManager(levelManager);
                try {
                    connection = getConnection(url, user, password);
                    Statement query = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                            ResultSet.CLOSE_CURSORS_AT_COMMIT);

                    int[] IDs = new int[1000];
                    String[] names = new String[1000];
                    int i = 1;
                    ResultSet result = query.executeQuery("SELECT id,name from Games ");
                    while (result.next()) {
                        IDs[i] = result.getInt("ID");
                        names[i] = result.getString("Name");
                        i++;
                    }
                    DataBaseLoadingFrame dblf = new DataBaseLoadingFrame(IDs, names, i,
                            levelManager, connection);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });

        return menuBar;
    }

}
