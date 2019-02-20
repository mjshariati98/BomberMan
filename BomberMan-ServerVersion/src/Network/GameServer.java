package Network;

import Configs.GameConfiguration;
import Models.BomberMan;
import Models.Cell;
import Models.Map;
import Models.Monster;
import Models.Monsters.*;
import Packets.MessagePacket;
import utils.Direction;
import views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends JFrame {
    private int gameID;
    private String name;
    private int maxPlayerNumber;
    private int rows;
    private int columns;
    private int bombLimit;
    private int bombRange;
    private boolean controlBomb;
    private int speed;
    private int width;
    private int height;
    private int levelNumber;
    private int topInset;
    private Map map;
    private List<BomberMan> bomberMans;
    private int monstersNumber;
    private int bomberMansIDs = 1;
    private int monsterIDs = 1;
    private List<MessagePacket> messagePackets;
    private List<SocketThread> clientsSocketThreads;
    private ChatRoom chatRoom;
    private GamePanel gamePanel;
    private FrontPanel frontPanel;
    private GameThread gameThread;
    private List<Class> newMonstersClasses;
    private PointsFrame pointsFrame;

    public GameServer(int gameID, String name, int maxPlayerNumber, int rows, int columns,
                      int bombLimit, int bombRange, boolean controlBomb, int speed, int topInset) {
        this.gameID = gameID;
        this.name = name;
        this.maxPlayerNumber = maxPlayerNumber;
        this.rows = rows;
        this.columns = columns;
        this.levelNumber = 1;
        this.monstersNumber = Math.min(rows, columns);
        this.bombLimit = bombLimit;
        this.bombRange = bombRange;
        this.controlBomb = controlBomb;
        this.speed = speed;
        this.topInset = topInset;

        this.messagePackets = new ArrayList<>();
        this.clientsSocketThreads = new ArrayList<>();
        this.newMonstersClasses = new ArrayList<>();

        this.bomberMans = new ArrayList<>();
        this.map = new Map(rows, columns, topInset, bomberMans, this);
        buildMonsters(levelNumber, this.map, this.monstersNumber);
        this.map.buildRandomItems();

        this.gamePanel = new GamePanel(this.map);
        this.frontPanel = new FrontPanel(gamePanel, this.map.getWidth(), this.map.getHeight());
        setContentPane(frontPanel);

        gameThread = new GameThread(this.map);
        gameThread.start();

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        frontPanel.setBoundsOfGamePanel(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_UP:
                        frontPanel.setBoundsOfGamePanel(Direction.DOWN);
                        break;
                    case KeyEvent.VK_RIGHT:
                        frontPanel.setBoundsOfGamePanel(Direction.LEFT);
                        break;
                    case KeyEvent.VK_DOWN:
                        frontPanel.setBoundsOfGamePanel(Direction.UP);
                        break;
                    case KeyEvent.VK_X:
                        newLevel();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.pointsFrame = new PointsFrame(22);
        setSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (windowSize.getWidth() - this.getWidth()) / 2;
        int y = (int) (windowSize.getHeight() - this.getHeight()) / 2;
        setLocation(x, y);
        setResizable(false);
        setJMenuBar(menu());
        setTitle("Level " + this.levelNumber);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    public void newLevel() {
        levelNumber = getLevelNumber() + 1;
        setTitle("Level " + getLevelNumber());
        this.map = new Map(rows, columns, topInset, bomberMans, this);
        this.map.setGamePanel(this.gamePanel);
        this.gamePanel.setMap(this.map);
        buildMonsters(getLevelNumber(), this.map, monstersNumber);
        this.map.buildRandomItems();
        this.frontPanel.setRespawnGamePanel();

        for (BomberMan bomberMan : bomberMans) {
            bomberMan.setMap(this.map);
            bomberMan.setFailed(false);
            Cell goodCell = getGoodCell();
            bomberMan.setX(goodCell.getX());
            bomberMan.setY(goodCell.getY());
            bomberMan.respawningStart();
        }

        gameThread.stop();
        gameThread = new GameThread(this.map);
        gameThread.start();

        newLevelStopThreads();

    }

    private void buildMonsters(int levelNumber, Map map, int numbers) {
        int[] enemyNumbers = new int[4 + newMonstersClasses.size()];
        int remainder = 0;
        if (levelNumber == 1) {
            enemyNumbers[0] = numbers;
        } else if (levelNumber == 2) {
            enemyNumbers[0] = (int) (Math.random() * numbers);
            if (newMonstersClasses.size() > 0) {
                enemyNumbers[1] = (int) (Math.random() * (numbers - enemyNumbers[0]));
                remainder = numbers - enemyNumbers[0] - enemyNumbers[1];
            } else {
                enemyNumbers[1] = numbers - enemyNumbers[0];
            }
            for (int i = 0; i < newMonstersClasses.size(); i++) {
                if (getMonsterClassStartingLevel(i) <= 2) {
                    if (i == newMonstersClasses.size() - 1) {
                        enemyNumbers[4 + i] = remainder;
                        remainder = 0;
                    } else {
                        enemyNumbers[4 + i] = (int) (Math.random() * remainder);
                        remainder -= enemyNumbers[4 + i];
                    }
                }
            }
            if (remainder > 0) {
                enemyNumbers[1] += remainder;
            }
        } else if (levelNumber == 3) {
            enemyNumbers[0] = (int) (Math.random() * numbers);
            remainder = numbers - enemyNumbers[0];
            enemyNumbers[1] = (int) (Math.random() * remainder);
            remainder -= enemyNumbers[1];
            if (newMonstersClasses.size() > 0) {
                enemyNumbers[2] = (int) (Math.random() * remainder);
                remainder -= enemyNumbers[2];
            } else {
                enemyNumbers[2] = remainder;
            }
            for (int i = 0; i < newMonstersClasses.size(); i++) {
                if (getMonsterClassStartingLevel(i) <= 3) {
                    if (i == newMonstersClasses.size() - 1) {
                        enemyNumbers[4 + i] = remainder;
                        remainder = 0;
                    } else {
                        enemyNumbers[4 + i] = (int) (Math.random() * remainder);
                        remainder -= enemyNumbers[4 + i];
                    }
                }
            }
            if (remainder > 0) {
                enemyNumbers[2] += remainder;
            }
        } else {
            for (int i = 4; i <= levelNumber; i++) {
                numbers = numbers + (int) (numbers * GameConfiguration.ENEMY_INCREASE_EVERY_LEVEL_PERCENT);
            }
            this.monstersNumber = numbers;
            enemyNumbers[0] = (int) (Math.random() * numbers);
            remainder = numbers - enemyNumbers[0];
            enemyNumbers[1] = (int) (Math.random() * remainder);
            remainder -= enemyNumbers[1];
            enemyNumbers[2] = (int) (Math.random() * remainder);
            remainder -= enemyNumbers[2];
            if (newMonstersClasses.size() > 0) {
                enemyNumbers[3] = (int) (Math.random() * remainder);
                remainder -= enemyNumbers[3];
            } else {
                enemyNumbers[3] = remainder;
            }
            for (int i = 0; i < newMonstersClasses.size(); i++) {
                if (getMonsterClassStartingLevel(i) <= levelNumber) {
                    if (i == newMonstersClasses.size() - 1) {
                        enemyNumbers[4 + i] = remainder;
                        remainder = 0;
                    } else {
                        enemyNumbers[4 + i] = (int) (Math.random() * remainder);
                        remainder -= enemyNumbers[4 + i];
                    }
                }
            }
            if (remainder > 0) {
                enemyNumbers[3] += remainder;
            }
        }

        for (int i = 0; i < enemyNumbers[0]; i++) {
            new MonsterLevel1(map, monsterIDs++);
        }
        for (int i = 0; i < enemyNumbers[1]; i++) {
            new MonsterLevel2(map, monsterIDs++);
        }
        for (int i = 0; i < enemyNumbers[2]; i++) {
            new MonsterLevel3(map, monsterIDs++);
        }
        for (int i = 0; i < enemyNumbers[3]; i++) {
            new MonsterLevel4(map, monsterIDs++);
        }
        for (int i = 0; i < newMonstersClasses.size(); i++) {
            for (int j = 0; j < enemyNumbers[i + 4]; j++) {
                InfiniteMonster infiniteMonster = new InfiniteMonster(this.map, newMonstersClasses.get(i), monsterIDs++);
                map.getMonsters().add(infiniteMonster);
            }
        }
    }

    private int getMonsterClassStartingLevel(int index) {
        try {
            AbstractNewMonster abm = (AbstractNewMonster) newMonstersClasses.get(index)
                    .getConstructor(int.class, int.class, int.class, int.class).newInstance(0, 0, 0, 0);
            return abm.getStartingLevel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public GameThread getGameThread() {
        return gameThread;
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


    private void newLevelStopThreads() {
        for (SocketThread socketThread : clientsSocketThreads) {
            socketThread.setNewLevel(true);
        }
    }

    public int getGameID() {
        return gameID;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public Map getMap() {
        return map;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<BomberMan> getBomberMans() {
        return bomberMans;
    }

    public int getPlayers() {
        return bomberMans.size();
    }


    /**
     * good cell is cell with no monster around in range 1
     */
    public Cell getGoodCell() {
        List<Cell> monsterCells = new ArrayList<>();
        for (Monster monster : this.map.getMonsters()) {
            monsterCells.add(monster.getCell());
        }
        monsterCells.add(null);
        List<Cell> goodCells = new ArrayList<>();
        for (Cell cell : map.getEmptyCells()){
            int i = cell.getX() / GameConfiguration.CELL_WIDTH;
            int j = cell.getY() / GameConfiguration.CELL_HEIGHT;
            if (i % 2 == 0 && j % 2 == 0) {

                if (!monsterCells.contains(cell) &&
                        !monsterCells.contains(this.map.getCell(cell.getX() + GameConfiguration.CELL_WIDTH,
                                cell.getY())) &&
                        this.map.getCell(cell.getX() + GameConfiguration.CELL_WIDTH,
                                cell.getY()).getType() == Cell.CellTypes.EMPTY &&
                        !monsterCells.contains(this.map.getCell(cell.getX() - GameConfiguration.CELL_WIDTH,
                                cell.getY())) &&
                        this.map.getCell(cell.getX() - GameConfiguration.CELL_WIDTH,
                                cell.getY()).getType() == Cell.CellTypes.EMPTY &&
                        !monsterCells.contains(this.map.getCell(cell.getX(),
                                cell.getY() + GameConfiguration.CELL_HEIGHT)) &&
                        this.map.getCell(cell.getX(),
                                cell.getY() + GameConfiguration.CELL_HEIGHT).getType() == Cell.CellTypes.EMPTY &&
                        !monsterCells.contains(this.map.getCell(cell.getX(),
                                cell.getY() - GameConfiguration.CELL_HEIGHT)) &&
                        this.map.getCell(cell.getX(),
                                cell.getY() - GameConfiguration.CELL_HEIGHT).getType() == Cell.CellTypes.EMPTY) {
                    goodCells.add(cell);
                }

            } else if (i % 2 != 0 && j % 2 == 0) {

                if (!monsterCells.contains(cell) &&
                        !monsterCells.contains(this.map.getCell(cell.getX() + GameConfiguration.CELL_WIDTH,
                                cell.getY())) &&
                        this.map.getCell(cell.getX() + GameConfiguration.CELL_WIDTH,
                                cell.getY()).getType() == Cell.CellTypes.EMPTY &&
                        !monsterCells.contains(this.map.getCell(cell.getX() - GameConfiguration.CELL_WIDTH,
                                cell.getY())) &&
                        this.map.getCell(cell.getX() - GameConfiguration.CELL_WIDTH,
                                cell.getY()).getType() == Cell.CellTypes.EMPTY) {
                    goodCells.add(cell);
                }

            } else if (i % 2 == 0 && j % 2 != 0) {

                if (!monsterCells.contains(cell) &&
                        !monsterCells.contains(this.map.getCell(cell.getX(),
                                cell.getY() + GameConfiguration.CELL_HEIGHT)) &&
                        this.map.getCell(cell.getX(),
                                cell.getY() + GameConfiguration.CELL_HEIGHT).getType() == Cell.CellTypes.EMPTY &&
                        !monsterCells.contains(this.map.getCell(cell.getX(),
                                cell.getY() - GameConfiguration.CELL_WIDTH)) &&
                        this.map.getCell(cell.getX(),
                                cell.getY() - GameConfiguration.CELL_WIDTH).getType() == Cell.CellTypes.EMPTY) {
                    goodCells.add(cell);
                }

            }
        }
        Cell goodCell = map.getRandomCell(goodCells);
        if (goodCell == null)
            goodCell = map.getRandomCell(map.getEmptyCells());
        return goodCell;
    }

    public BomberMan addBomberMan(String name) {
        BomberMan bomberMan = new BomberMan(this.map, bomberMansIDs++, name);
        Cell goodCell = getGoodCell();

        bomberMan.setX(goodCell.getX());
        bomberMan.setY(goodCell.getY());
        bomberMan.setBombLimit(bombLimit);
        bomberMan.setBombRange(bombRange);
        bomberMan.setBombControl(controlBomb);
        bomberMan.setStep(speed);
        bomberMan.respawningStart();
        bomberMan.setGhostAbility(false);

        bomberMan.setGameServer(this);
        bomberMans.add(bomberMan);
        return bomberMan;
    }

    public synchronized List<MessagePacket> getMessagePackets() {
        return messagePackets;
    }

    public List<SocketThread> getClientsSocketThreads() {
        return clientsSocketThreads;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public List<Class> getNewMonstersClasses() {
        return newMonstersClasses;
    }

    public PointsFrame getPointsFrame() {
        return pointsFrame;
    }

    public JMenuBar menu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        JMenu view = new JMenu("View");

        JMenuItem addEnemyItem = new JMenuItem("Add Enemy");
        JMenuItem helpItem = new JMenuItem("Help");
        JCheckBoxMenuItem chatsItem = new JCheckBoxMenuItem("Chats");

        chatsItem.setSelected(false);
        chatsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chatRoom == null)
                    GameServer.this.chatRoom = new ChatRoom(GameServer.this,
                            GameServer.this.getInsets().top);

                JCheckBoxMenuItem x = (JCheckBoxMenuItem) e.getSource();
                chatRoom.setVisible(x.isSelected());
                GameServer.this.setVisible(true);

            }
        });

        addEnemyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEnemyFrame addEnemyFrame = new AddEnemyFrame(GameServer.this);
                addEnemyFrame.setVisible(true);
            }
        });

        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HelpDialog helpDialog = new HelpDialog(GameServer.this);
                helpDialog.setVisible(true);
            }
        });

        menuBar.add(file);
        menuBar.add(help);
        menuBar.add(view);
        file.add(addEnemyItem);
        help.add(helpItem);
        view.add(chatsItem);

        return menuBar;
    }
}
