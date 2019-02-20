package views;

import Configs.GameConfiguration;
import Exceptions.GoOutOfMapException;
import Exceptions.GoToWallAndBlockCellException;
import Models.Bomb;
import Models.BomberMan;
import Models.LevelManager;
import Models.Map;
import Packets.ActivityPacket;
import Threads.MonsterThread;
import Threads.TimeThread;
import utils.Direction;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class GameFrame extends JFrame {
    private Type type;
    private LevelManager levelManager;
    private Map map;
    private GamePanel gamePanel;
    private BomberMan bomberMan;
    private LocalDateTime lastPress;
    private JFileChooser fileChooser;
    private boolean saved = false;
    private String savePath = "";
    private FrontPanel frontPanel;
    private int width;
    private int height;
    private ScoreBoard scoreBoard;
    private Date time;
    private long loadTime;
    private MonsterThread monstersThread;
    private TimeThread timeThread;
    private boolean run;
    private List<BomberMan> otherBomberMans;
    private ActivityPacket activityPacket;
    private ChatRoom chatRoom;

    public enum Type {
        SINGLE, CLIENT_GAME, CLIENT_VIEW;
    }

    public GameFrame(Map map, BomberMan bomberMan, LevelManager levelManager, Type type) {
        this.type = type;
        this.map = map;
        this.bomberMan = bomberMan;
        if (type == Type.SINGLE || type == Type.CLIENT_GAME)
            this.bomberMan.setGameFrame(this);
        this.otherBomberMans = new ArrayList<>();
        map.setOtherBomberMans(otherBomberMans);
        this.levelManager = levelManager;
        this.map.setBomberMan(bomberMan);
        gamePanel = new GamePanel(this.map, bomberMan);
        lastPress = LocalDateTime.now();
        frontPanel = new FrontPanel(gamePanel, this.map.getWidth(), this.map.getHeight());

        if (type == Type.CLIENT_GAME)
            this.activityPacket = new ActivityPacket(bomberMan.getId(),
                    bomberMan.getX(), bomberMan.getY(), bomberMan.getDirection());

        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Bomberman files (*.bomberman)", "bomberman");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        time = new Date();
        loadTime = 0;
        run = true;

        setSize();
        setContentPane(frontPanel);
        setJMenuBar(menu());
        if (type == Type.SINGLE) {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        } else {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
        setResizable(false);
        setTitle("Level " + levelManager.getLevelNumber());
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (windowSize.getWidth() - this.getWidth()) / 2;
        int y = (int) (windowSize.getHeight() - this.getHeight()) / 2;
        setLocation(x, y);

        if (type != Type.CLIENT_VIEW) {
            scoreBoard = new ScoreBoard(this);
            this.map.setScoreBoard(scoreBoard);
            scoreBoard.updateScore(bomberMan.getScore());
            scoreBoard.setVisible(true);
        }


        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                synchronized (lastPress) {
                    if (lastPress.isBefore(LocalDateTime.now().minusNanos(30000000))) {
                        lastPress = LocalDateTime.now();
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_LEFT:
                                if (type == Type.SINGLE) {
                                    bomberMan.move(Direction.LEFT);
                                } else if (type == Type.CLIENT_VIEW ||
                                        (type == Type.CLIENT_GAME && bomberMan.isFailed())) {
                                    frontPanel.setBoundsOfGamePanel(Direction.RIGHT);
                                } else {
                                    activityPacket.setActivity(ActivityPacket.Activity.LEFT);
                                }
                                try {
                                    if (GameFrame.this.map.getWidth() > GameConfiguration.GAME_FRAME_WIDTH &&
                                            bomberMan.getX() - (-frontPanel.x) < getWidth() / 2 &&
                                            bomberMan.isCellFree(Direction.LEFT)) {
                                        frontPanel.setBoundsOfGamePanel(Direction.RIGHT);
                                    }
                                } catch (GoToWallAndBlockCellException | GoOutOfMapException e1) {
//                                    System.err.println("Wall And Block Exception");
                                }
                                break;
                            case KeyEvent.VK_UP:
                                if (type == Type.SINGLE) {
                                    bomberMan.move(Direction.UP);
                                } else if (type == Type.CLIENT_VIEW ||
                                        (type == Type.CLIENT_GAME && bomberMan.isFailed())) {
                                    frontPanel.setBoundsOfGamePanel(Direction.DOWN);

                                } else {
                                    activityPacket.setActivity(ActivityPacket.Activity.UP);
                                }
                                try {
                                    if (GameFrame.this.map.getHeight() > GameConfiguration.GAME_FRAME_HEIGHT &&
                                            bomberMan.getY() - (-frontPanel.y) < getHeight() / 2 &&
                                            bomberMan.isCellFree(Direction.UP)) {
                                        frontPanel.setBoundsOfGamePanel(Direction.DOWN);
                                    }
                                } catch (GoToWallAndBlockCellException | GoOutOfMapException e1) {
//                                    System.err.println("Wall And Block Exception");
                                }
                                break;
                            case KeyEvent.VK_RIGHT:
                                if (type == Type.SINGLE) {
                                    bomberMan.move(Direction.RIGHT);
                                } else if (type == Type.CLIENT_VIEW ||
                                        (type == Type.CLIENT_GAME && bomberMan.isFailed())) {
                                    frontPanel.setBoundsOfGamePanel(Direction.LEFT);

                                } else {
                                    activityPacket.setActivity(ActivityPacket.Activity.RIGHT);
                                }
                                try {
                                    if (GameFrame.this.map.getWidth() > GameConfiguration.GAME_FRAME_WIDTH &&
                                            bomberMan.getX() > getWidth() / 2 &&
                                            bomberMan.isCellFree(Direction.RIGHT)) {
                                        frontPanel.setBoundsOfGamePanel(Direction.LEFT);
                                    }
                                } catch (GoToWallAndBlockCellException | GoOutOfMapException e1) {
//                                    System.err.println("Wall And Block Exception");
                                }
                                break;
                            case KeyEvent.VK_DOWN:
                                if (type == Type.SINGLE) {
                                    bomberMan.move(Direction.DOWN);
                                } else if (type == Type.CLIENT_VIEW ||
                                        (type == Type.CLIENT_GAME && bomberMan.isFailed())) {
                                    frontPanel.setBoundsOfGamePanel(Direction.UP);
                                } else {
                                    activityPacket.setActivity(ActivityPacket.Activity.DOWN);
                                }
                                try {
                                    if (GameFrame.this.map.getHeight() > GameConfiguration.GAME_FRAME_HEIGHT &&
                                            bomberMan.getY() > getHeight() / 2 &&
                                            bomberMan.isCellFree(Direction.DOWN)) {
                                        frontPanel.setBoundsOfGamePanel(Direction.UP);
                                    }
                                } catch (GoToWallAndBlockCellException | GoOutOfMapException e1) {
//                                    System.err.println("Wall And Block Exception");
                                }
                                break;
                            case KeyEvent.VK_B:
                                if (type == Type.SINGLE &&
                                        bomberMan.getBobms().size() < bomberMan.getBombLimit()) {
                                    new Bomb(bomberMan, GameFrame.this.map);
                                } else if (type == Type.CLIENT_GAME) {
                                    activityPacket.setActivity(ActivityPacket.Activity.BOMB);
                                }
                                break;
                            case KeyEvent.VK_SPACE:
                                if (type == Type.SINGLE &&
                                        bomberMan.isBombControl() && bomberMan.getBobms().size() > 0) {
                                    bomberMan.getBobms().get(0).setExploded(true);
                                } else if (type == Type.CLIENT_GAME) {
                                    activityPacket.setActivity(ActivityPacket.Activity.EXPLODE);
                                }

                                break;
                            case KeyEvent.VK_N:
                                new NewLevelDialog(GameFrame.this, bomberMan);
                                break;
                        }
                        gamePanel.repaint();

                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (type == Type.CLIENT_GAME || type == Type.SINGLE) {
                    String bomberManImageName = bomberMan.getImageName();
                    if (bomberManImageName.equals("Front-2") ||
                            bomberManImageName.equals("Front-4")) {
                        bomberMan.setImage(bomberMan.getFrontImagesCounter() % 4, Direction.DOWN);
                        bomberMan.setFrontImagesCounter(bomberMan.getFrontImagesCounter() + 1);
                    } else if (bomberManImageName.equals("Back-2") ||
                            bomberManImageName.equals("Back-4")) {
                        bomberMan.setImage(bomberMan.getBackImagesCounter() % 4, Direction.UP);
                        bomberMan.setBackImagesCounter(bomberMan.getBackImagesCounter() + 1);
                    } else if (bomberManImageName.equals("Right-2") ||
                            bomberManImageName.equals("Right-4")) {
                        bomberMan.setImage(bomberMan.getRightImagesCounter() % 4, Direction.RIGHT);
                        bomberMan.setRightImagesCounter(bomberMan.getRightImagesCounter() + 1);
                    } else if (bomberManImageName.equals("Left-2") ||
                            bomberManImageName.equals("Left-4")) {
                        bomberMan.setImage(bomberMan.getLeftImagesCounter() % 4, Direction.LEFT);
                        bomberMan.setLeftImagesCounter(bomberMan.getLeftImagesCounter() + 1);
                    }
                    gamePanel.repaint();
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                if (type != Type.CLIENT_VIEW)
                    scoreBoard.setLocation((int) getLocation().getX() - scoreBoard.getWidth(),
                            (int) getLocation().getY() + (2 * getInsets().top));
            }
        });

    }

    public void setSize() {
        this.width = this.map.getMainWidth();
        this.height = this.map.getMainHeight();
        int frameWidth = GameConfiguration.GAME_FRAME_WIDTH;
        int frameHeight = GameConfiguration.GAME_FRAME_HEIGHT;
        if (this.map.getMainWidth() >= frameWidth && this.map.getMainHeight() <= frameHeight) {
            this.width = frameWidth;
        } else if (this.map.getMainWidth() <= frameWidth && this.map.getMainHeight() >= frameHeight) {
            this.height = frameHeight;
        } else if (this.map.getMainWidth() >= frameWidth && this.map.getMainHeight() >= frameHeight) {
            this.width = frameWidth;
            this.height = frameHeight;
        }
        setSize(getWidth(), getHeight());
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public BomberMan getBomberMan() {
        return bomberMan;
    }

    public void setBomberMan(BomberMan bomberMan) {
        this.bomberMan = bomberMan;
        getGamePanel().setBomberMan(bomberMan);
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public FrontPanel getFrontPanel() {
        return frontPanel;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public Type getGameType() {
        return type;
    }

    public Date getTime() {
        return time;
    }

    public String getStringTime() {
        int spentTime = (int) ((new Date().getTime() - time.getTime()) / 1000);
        if (spentTime < 60) {
            return (new Date().getTime() - time.getTime()) / 1000 + "s";
        } else {
            int min = spentTime / 60;
            int seconds = spentTime - min * 60;
            return min + " min & " + seconds + " s";
        }
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(long loadTime) {
        this.loadTime = loadTime;
    }

    public boolean isRun() {
        return run;
    }

    void setRun(boolean run) {
        this.run = run;
    }

    public MonsterThread getMonstersThread() {
        return monstersThread;
    }

    public TimeThread getTimeThread() {
        return this.timeThread;
    }

    public void setFrontPanel(FrontPanel frontPanel) {
        this.frontPanel = frontPanel;
    }

    public void newMonsterThread() {
        if (this.monstersThread != null)
            this.monstersThread.stop();
        this.monstersThread = new MonsterThread(GameFrame.this);
        this.monstersThread.start();
    }

    public void newTimeThread() {
        if (this.timeThread != null)
            this.timeThread.stop();
        this.timeThread = new TimeThread(GameFrame.this);
    }

    @Override
    public void dispose() {
        if (monstersThread != null) {
            monstersThread.stop();
            if (timeThread.isAlive())
                timeThread.stop();
        }
        if (scoreBoard != null)
            scoreBoard.dispose();
        super.dispose();
    }

    public void addBomberMan(BomberMan bomberMan) {
        otherBomberMans.add(bomberMan);
        map.setOtherBomberMans(otherBomberMans);
    }

    public List<BomberMan> getOtherBomberMans() {
        return otherBomberMans;
    }

    public void setOtherBomberMans(List<BomberMan> otherBomberMans) {
        this.otherBomberMans = otherBomberMans;
        this.map.setOtherBomberMans(otherBomberMans);
    }

    public ActivityPacket getActivityPacket() {
        return activityPacket;
    }

    public void setActivityPacket(ActivityPacket activityPacket) {
        this.activityPacket = activityPacket;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public JMenuBar menu() {
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        JMenu view = new JMenu("View");
        menu.add(file);
        menu.add(help);
        menu.add(view);

        JMenuItem newItem = new JMenuItem("New Game");
        JMenuItem loadItem = new JMenuItem("Open...");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As...");
        JMenuItem settingsItem = new JMenuItem("Settings");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem exitItem = new JMenuItem("Exit");
        JCheckBoxMenuItem scoreBoaredItem = new JCheckBoxMenuItem("Score Board");
        JCheckBoxMenuItem chatItem = new JCheckBoxMenuItem("Chats");
        chatItem.setSelected(false);
        scoreBoaredItem.setSelected(true);
        JMenuItem saveDB = new JMenuItem("Save (Database)");
        JMenuItem loadDB = new JMenuItem("Load (Database)");

        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewAndExitItemDialog newItemDialog = new NewAndExitItemDialog(GameFrame.this, false);
            }
        });

        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileInputStream fileInputStream = null;
                fileChooser.showOpenDialog(GameFrame.this);
                try {
                    fileInputStream = new FileInputStream(
                            new File(fileChooser.getSelectedFile().getPath()));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                levelManager.load(fileInputStream, fileChooser.getSelectedFile().getPath());
            }
        });

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileOutputStream fileOutputStream = null;
                if (saved) {
                    try {
                        File file = new File(savePath);
                        if (!file.toString().endsWith(".bomberman")) {
                            fileOutputStream = new FileOutputStream(file + ".bomberman", false);
                        } else {
                            fileOutputStream = new FileOutputStream(file, false);
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    fileChooser.showSaveDialog(GameFrame.this);
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
                    setSaved(true);
                    savePath = fileChooser.getSelectedFile().getPath();
                }
                levelManager.save(fileOutputStream);
            }
        });

        saveAsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileOutputStream fileOutputStream = null;
                fileChooser.showSaveDialog(GameFrame.this);
                try {
                    File file = new File(fileChooser.getSelectedFile().getPath());
                    if (!file.toString().endsWith(".bomberman")) {
                        fileOutputStream = new FileOutputStream(file + ".bomberman");
                    } else {
                        fileOutputStream = new FileOutputStream(file);
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                setSaved(true);
                savePath = fileChooser.getSelectedFile().getPath();
                levelManager.save(fileOutputStream);
            }
        });

        saveDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Image image = null;
                try {
                    image = ImageIO.read(new File("src/Images/db.png"))
                            .getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                ImageIcon icon = new ImageIcon(image);
                String name = (String) JOptionPane.showInputDialog(GameFrame.this,
                        "Enter The Game Name : ", "Save To DataBase", JOptionPane.QUESTION_MESSAGE, icon, null, null);
                levelManager.saveDB(name);
            }
        });

        loadDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost/BomberMan";
                String user = "root";
                String password = "";
                Connection connection = null;
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
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewAndExitItemDialog newItemDialog = new NewAndExitItemDialog(GameFrame.this, true);
            }
        });

        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelpDialog helpDialog = new HelpDialog(GameFrame.this);
            }
        });

        settingsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                levelManager.getSettingsDialog().setVisible(true);
            }
        });

        scoreBoaredItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem checkBoxItem = (JCheckBoxMenuItem) e.getSource();
                scoreBoard.updateScore(bomberMan.getScore());
                scoreBoard.setLocation((int) getLocation().getX() - scoreBoard.getWidth(),
                        (int) getLocation().getY() + (2 * GameFrame.this.getInsets().top));
                scoreBoard.setVisible(checkBoxItem.isSelected());
                GameFrame.this.setVisible(true);
            }
        });

        chatItem.setSelected(false);
        chatItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) e.getSource();
                chatRoom.setVisible(checkBoxMenuItem.isSelected());
                GameFrame.this.setVisible(true);
            }
        });

        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + InputEvent.SHIFT_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        chatItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        if (type == Type.SINGLE) {
            file.add(newItem);
            file.add(loadItem);
            file.add(loadDB);
            file.add(saveItem);
            file.add(saveAsItem);
            file.add(saveDB);
            file.addSeparator();
        } else if (type == Type.CLIENT_GAME) {
            view.add(chatItem);
        }
        file.add(settingsItem);
        file.add(exitItem);
        help.add(helpItem);
        view.add(scoreBoaredItem);

        return menu;
    }
}
