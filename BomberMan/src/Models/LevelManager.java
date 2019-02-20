package Models;

import Configs.GameConfiguration;
import Configs.Sound;
import Models.Items.*;
import Models.Monsters.*;
import Packets.BombermanPacket;
import Packets.MapPacket;
import Packets.MonsterPacket;
import sun.misc.BASE64Decoder;
import views.FrontPanel;
import views.GameFrame;
import views.GamePanel;
import views.SettingsDialog;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;
import java.util.List;

public class LevelManager {
    private int levelNumber;
    private int row;
    private int column;
    private int topInset;
    private int monstersNumber;
    private BomberMan bomberMan;
    private GameFrame gameFrame;
    private Map map;
    private boolean startingLoad;
    private SettingsDialog settingsDialog;
    private int bomberManID;
    private Connection connection;

    public LevelManager() {
        startingLoad = true;
    }

    public LevelManager(int row, int column, int topInset) {
        this.levelNumber = 1;
        this.row = row;
        this.column = column;
        this.topInset = topInset;
        if (GameConfiguration.MONSTER_NUMBERS == -1)
            this.monstersNumber = Math.min(row, column);
        else
            this.monstersNumber = GameConfiguration.MONSTER_NUMBERS;
        this.map = new Map(row, column, topInset);
        buildMonsters(levelNumber, this.map, this.monstersNumber);
        this.bomberMan = new BomberMan(this.map);
        this.gameFrame = new GameFrame(this.map, bomberMan, this, GameFrame.Type.SINGLE);
        this.gameFrame.newTimeThread();
        this.gameFrame.newMonsterThread();
        this.map.buildRandomItems();

        Sound.MEGALOBOX.play(Clip.LOOP_CONTINUOUSLY);
    }

    // Client Game
    public LevelManager(MapPacket mapPacket, int bomberManID) {
        this.bomberManID = bomberManID;
        this.row = mapPacket.getRows();
        this.column = mapPacket.getColumns();
        this.monstersNumber = mapPacket.getMonsterPackets().size();
        this.map = new Map(row, column, 22);
        this.map.setCells(mapPacket);

        newMonsters(mapPacket);

        List<BomberMan> otherBomberMans = new ArrayList<>();
        for (BombermanPacket bomberManPacket : mapPacket.getBombermanPackets()) {
            BomberMan bomberMan = new BomberMan(this.map, bomberManPacket.getId(),
                    bomberManPacket.getName());
            bomberMan.setX(bomberManPacket.getX());
            bomberMan.setY(bomberManPacket.getY());
            bomberMan.setBombControl(bomberManPacket.isBombControl());
            bomberMan.setBombLimit(bomberManPacket.getBombLimit());
            bomberMan.setBombRange(bomberManPacket.getBombRange());
            bomberMan.setScore(bomberManPacket.getScore());
            bomberMan.setStep(bomberManPacket.getSpeed());
            bomberMan.setRespawning(bomberManPacket.isRespawning());
            bomberMan.setDirection(bomberManPacket.getDirection());
            bomberMan.setGhostAbility(bomberManPacket.isGhostAbility());
            if (bomberManPacket.getId() == bomberManID)
                this.bomberMan = bomberMan;
            else
                otherBomberMans.add(bomberMan);
        }

        this.map.setItems(mapPacket);

        this.gameFrame = new GameFrame(this.map, this.bomberMan, this, GameFrame.Type.CLIENT_GAME);
        this.gameFrame.setOtherBomberMans(otherBomberMans);
        this.gameFrame.setTitle("Level " + mapPacket.getLevelNumber());
        this.gameFrame.getFrontPanel().setBoundsPosition(this.bomberMan.getX(), this.bomberMan.getY());

        Sound.MEGALOBOX.play(Clip.LOOP_CONTINUOUSLY);
    }

    // Client View
    public LevelManager(MapPacket mapPacket) {
        this.row = mapPacket.getRows();
        this.column = mapPacket.getColumns();
        this.monstersNumber = mapPacket.getMonsterPackets().size();
        this.map = new Map(row, column, 22);
        this.map.setCells(mapPacket);

        newMonsters(mapPacket);

        List<BomberMan> otherBomberMans = new ArrayList<>();
        for (BombermanPacket bomberManPacket : mapPacket.getBombermanPackets()) {
            BomberMan bomberMan = new BomberMan(this.map);
            bomberMan.setX(bomberManPacket.getX());
            bomberMan.setY(bomberManPacket.getY());
            bomberMan.setBombControl(bomberManPacket.isBombControl());
            bomberMan.setBombLimit(bomberManPacket.getBombLimit());
            bomberMan.setBombRange(bomberManPacket.getBombRange());
            bomberMan.setScore(bomberManPacket.getScore());
            bomberMan.setStep(bomberManPacket.getSpeed());
            bomberMan.setRespawning(bomberManPacket.isRespawning());
            bomberMan.setDirection(bomberManPacket.getDirection());
            bomberMan.setId(bomberManPacket.getId());
            bomberMan.setGhostAbility(bomberManPacket.isGhostAbility());
            otherBomberMans.add(bomberMan);
        }

        this.map.setItems(mapPacket);

        this.gameFrame = new GameFrame(this.map, null, this, GameFrame.Type.CLIENT_VIEW);
        this.gameFrame.setOtherBomberMans(otherBomberMans);
        this.gameFrame.setTitle("Level " + mapPacket.getLevelNumber());

        Sound.MEGALOBOX.play(Clip.LOOP_CONTINUOUSLY);
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public SettingsDialog getSettingsDialog() {
        return settingsDialog;
    }

    public void setSettingsDialog(SettingsDialog settingsDialog) {
        this.settingsDialog = settingsDialog;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public Map getMap() {
        return map;
    }

    public void newLevel() {
        gameFrame.setVisible(false);
        gameFrame.getScoreBoard().setVisible(false);

        setLevelNumber(getLevelNumber() + 1);
        gameFrame.setTitle("Level " + getLevelNumber());
        Map newMap = new Map(row, column, topInset);
        newMap.setScoreBoard(gameFrame.getScoreBoard());
        gameFrame.getGamePanel().setMap(newMap);
        buildMonsters(getLevelNumber(), newMap, monstersNumber);
        gameFrame.setMap(newMap);
        newMap.setGamePanel(gameFrame.getGamePanel());
        newMap.setBomberMan(this.bomberMan);
        gameFrame.setTime(new Date());
        this.bomberMan.setMap(newMap);
        this.bomberMan.setX(0);
        this.bomberMan.setY(0);

        newMap.buildRandomItems();

        gameFrame.newMonsterThread();
        gameFrame.newTimeThread();
        gameFrame.getFrontPanel().setRespawnGamePanel();

        gameFrame.getScoreBoard().setVisible(true);
        gameFrame.setVisible(true);
    }

    private static void buildMonsters(int levelNumber, Map map, int numbers) {
        int[] enemyNumbers = new int[4];

        if (levelNumber == 1) {
            enemyNumbers[0] = numbers;
        } else if (levelNumber == 2) {
            enemyNumbers[0] = (int) (Math.random() * numbers);
            enemyNumbers[1] = numbers - enemyNumbers[0];
        } else if (levelNumber == 3) {
            enemyNumbers[0] = (int) (Math.random() * numbers);
            enemyNumbers[1] = (int) (Math.random() * (numbers - enemyNumbers[0]));
            enemyNumbers[2] = numbers - enemyNumbers[0] - enemyNumbers[1];
        } else {
            for (int i = 4; i <= levelNumber; i++) {
                numbers = numbers + (int) (numbers * GameConfiguration.ENEMY_INCREASE_EVERY_LEVEL_PERCENT);
            }
            enemyNumbers[0] = (int) (Math.random() * numbers);
            enemyNumbers[1] = (int) (Math.random() * (numbers - enemyNumbers[0]));
            enemyNumbers[2] = (int) (Math.random() * (numbers - enemyNumbers[0] - enemyNumbers[1]));
            enemyNumbers[3] = numbers - enemyNumbers[0] - enemyNumbers[1] - enemyNumbers[2];
        }

        for (int i = 0; i < enemyNumbers[0]; i++) {
            MonsterLevel1 yellowMonster = new MonsterLevel1(map);
            map.getMonsters().add(yellowMonster);
        }
        for (int i = 0; i < enemyNumbers[1]; i++) {
            MonsterLevel2 whiteMonster = new MonsterLevel2(map);
            map.getMonsters().add(whiteMonster);
        }
        for (int i = 0; i < enemyNumbers[2]; i++) {
            MonsterLevel3 purpleMonster = new MonsterLevel3(map);
            map.getMonsters().add(purpleMonster);
        }
        for (int i = 0; i < enemyNumbers[3]; i++) {
            MonsterLevel4 blackMonster = new MonsterLevel4(map);
            map.getMonsters().add(blackMonster);
        }
    }

    public void newMonsters(MapPacket mapPacket) {
        List<Monster> newMonsters = new ArrayList<>();
        for (MonsterPacket monsterPacket : mapPacket.getMonsterPackets()) {
            switch (monsterPacket.getLevel()) {
                case 1:
                    Monster m1 = new MonsterLevel1(this.map, monsterPacket.getId());
                    m1.setX(monsterPacket.getX());
                    m1.setY(monsterPacket.getY());
                    m1.setDirection(monsterPacket.getDirection());
                    newMonsters.add(m1);
                    break;
                case 2:
                    Monster m2 = new MonsterLevel2(this.map, monsterPacket.getId());
                    m2.setX(monsterPacket.getX());
                    m2.setY(monsterPacket.getY());
                    m2.setDirection(monsterPacket.getDirection());
                    newMonsters.add(m2);
                    break;
                case 3:
                    Monster m3 = new MonsterLevel3(this.map, monsterPacket.getId());
                    m3.setX(monsterPacket.getX());
                    m3.setY(monsterPacket.getY());
                    m3.setDirection(monsterPacket.getDirection());
                    newMonsters.add(m3);
                    break;
                case 4:
                    Monster m4 = new MonsterLevel4(this.map, monsterPacket.getId());
                    m4.setX(monsterPacket.getX());
                    m4.setY(monsterPacket.getY());
                    m4.setDirection(monsterPacket.getDirection());
                    newMonsters.add(m4);
                    break;
                case 0:
                    try {
                        Monster infiniteMonster = new InfiniteMonster(this.map, monsterPacket.getId());
                        String encodedImage = monsterPacket.getEncodedImage();
                        BASE64Decoder decoder = new BASE64Decoder();
                        byte[] imageByte = decoder.decodeBuffer(encodedImage);
                        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                        BufferedImage image = ImageIO.read(bis);
                        bis.close();
                        Image i = image.getScaledInstance(GameConfiguration.MONSTER_WIDTH,
                                GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
                        infiniteMonster.setImage(i);
                        ((InfiniteMonster) infiniteMonster).loadImages();
                        infiniteMonster.setX(monsterPacket.getX());
                        infiniteMonster.setY(monsterPacket.getY());
                        infiniteMonster.setDirection(monsterPacket.getDirection());
                        infiniteMonster.setStep(monsterPacket.getStep());
                        newMonsters.add(infiniteMonster);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        this.map.getMonsters().addAll(newMonsters);
    }

    public void save(FileOutputStream fos) {
        PrintStream printer = new PrintStream(fos);
        String text = "";
        text += "BomberMan Game on " + LocalDateTime.now().toString() + '\n';
        text += "Level: " + levelNumber + '\n';
        text += "SpentTime: " + (new Date().getTime() - gameFrame.getTime().getTime()) + '\n';

        text += "Map Informations: \n";
        text += "Rows: " + map.getRows() + '\n';
        text += "Columns: " + map.getColumns() + '\n';
        text += "TopInset: " + map.getTopInset() + '\n';
        for (int i = 0; i < map.getColumns(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                text += "Cell [" + i + "][" + j + "] : " + '\n';
                text += map.getCells()[i][j].getType();
                text += '\n';
                text += map.getCells()[i][j].getItem();
                text += '\n';
            }
        }

        text += "BomberMan@ " + bomberMan.getX() + " " + bomberMan.getY() + '\n';
        text += "BomberManScore: " + bomberMan.getScore() + '\n';
        text += "BomberManBombLimit: " + bomberMan.getBombLimit() + '\n';
        text += "BomberManBombRange: " + bomberMan.getBombRange() + '\n';
        text += "BomberManControlBomb: " + bomberMan.isBombControl() + '\n';
        text += "BomberManStep: " + bomberMan.getStep() + '\n';
        text += "BomberManGhostAbility: " + bomberMan.isGhostAbility() + '\n';

        List<Monster> monsters = map.getMonsters();
        text += "Monsters: ";
        text += monsters.size();
        text += '\n';
        text += "MonstersInitialNumber: ";
        text += this.monstersNumber;
        text += '\n';
        for (Monster monster : monsters) {
            text += "MonsterLevel " + monster.getLevel() + " @ " + monster.getX() + " " + monster.getY() + '\n';
        }

        printer.print(text);
        printer.close();
    }

    public void load(FileInputStream fis, String filePath) {
        Scanner scanner = new Scanner(fis);

        scanner.nextLine();
        scanner.next();
        int levelNumber = scanner.nextInt();
        scanner.nextLine();
        scanner.next();
        long spentTime = scanner.nextLong();
        scanner.nextLine();
        scanner.nextLine();
        scanner.next();
        int rows = scanner.nextInt();
        scanner.nextLine();
        scanner.next();
        int columns = scanner.nextInt();
        scanner.nextLine();
        scanner.next();
        int topInset = scanner.nextInt();
        scanner.nextLine();
        Map newMap = new Map(rows, columns, topInset);

        if (startingLoad) {
            startingLoad = false;
            this.bomberMan = new BomberMan(newMap);
            newMap.setBomberMan(this.bomberMan);
            this.gameFrame = new GameFrame(newMap, bomberMan, this, GameFrame.Type.SINGLE);
            this.gameFrame.newTimeThread();
            this.gameFrame.newMonsterThread();
            newMap.setScoreBoard(gameFrame.getScoreBoard());
        }
        Door door = null;
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Cell cell = newMap.getCells()[i][j];
                scanner.nextLine();
                String type = scanner.next();
                switch (type) {
                    case "EMPTY":
                        cell.setType(Cell.CellTypes.EMPTY);
                        break;
                    case "WALL":
                        cell.setType(Cell.CellTypes.WALL);
                        break;
                    case "BLOCK":
                        cell.setType(Cell.CellTypes.BLOCK);
                        break;
                    case "BOMB":
                        cell.setType(Cell.CellTypes.BOMB);
                        new Bomb(this.bomberMan, newMap);
                }
                scanner.nextLine();
                String item = scanner.nextLine();
                switch (item) {
                    case "Control Bombs":
                        new ControlBombs(cell, this.bomberMan);
                        break;
                    case "Decrease Bombs":
                        new DecreaseBombs(cell, this.bomberMan);
                        break;
                    case "Decrease Radius":
                        new DecreaseRadius(cell, this.bomberMan);
                        break;
                    case "Decrease Score":
                        new DecreaseScore(cell, this.bomberMan);
                        break;
                    case "Decrease Speed":
                        new DecreseSpeed(cell, this.bomberMan);
                        break;
                    case "Door":
                        door = new Door(cell, this.bomberMan, null);
                        break;
                    case "Increase Bombs":
                        new IncreaseBombs(cell, this.bomberMan);
                        break;
                    case "Increase Radius":
                        new IncreaseRadius(cell, this.bomberMan);
                        break;
                    case "Increase Score":
                        new IncreaseScore(cell, this.bomberMan);
                        break;
                    case "Increase Speed":
                        new IncreaseSpeed(cell, this.bomberMan);
                        break;
                    case "Ghost":
                        new GhostAbility(cell, this.bomberMan);
                        break;
                    case "null":
                        cell.setItem(null);
                        break;
                }
            }
        }

        scanner.next();
        int bombermanX = scanner.nextInt();
        int bombermanY = scanner.nextInt();
        scanner.nextLine();
        scanner.next();
        int score = scanner.nextInt();
        scanner.nextLine();
        scanner.next();
        int bombLimit = scanner.nextInt();
        scanner.nextLine();
        scanner.next();
        int radius = scanner.nextInt();
        scanner.nextLine();
        scanner.next();
        String controlBomb = scanner.next();
        scanner.nextLine();
        scanner.next();
        int step = scanner.nextInt();
        scanner.nextLine();
        scanner.next();
        String ghostAbility = scanner.next();
        scanner.nextLine();

        scanner.next();
        int size = scanner.nextInt();
        scanner.nextLine();
        scanner.next();
        this.monstersNumber = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < size; i++) {
            scanner.next();
            int level = scanner.nextInt();
            scanner.next();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Monster monster = null;
            switch (level) {
                case 1:
                    monster = new MonsterLevel1(newMap);
                    break;
                case 2:
                    monster = new MonsterLevel2(newMap);
                    break;
                case 3:
                    monster = new MonsterLevel3(newMap);
                    break;
                case 4:
                    monster = new MonsterLevel4(newMap);
                    break;

            }
            monster.setX(x);
            monster.setY(y);
            newMap.getMonsters().add(monster);
            scanner.nextLine();
        }

        door.setMonsters(newMap.getMonsters());

        setLevelNumber(levelNumber);
        this.row = rows;
        this.column = columns;
        this.topInset = topInset;
        GameConfiguration.LEVEL_TIME = (int) (Math.max(row, column) / 3);


        if (!startingLoad) {
            newMap.setBomberMan(this.bomberMan);
            gameFrame.setVisible(false);
            gameFrame.getScoreBoard().setVisible(false);
            gameFrame.setTitle("Level " + getLevelNumber());

            gameFrame.setMap(newMap);
            bomberMan.setMap(newMap);
            gameFrame.setSize();
            gameFrame.getGamePanel().setMap(newMap);
            GamePanel newGamePanel = new GamePanel(newMap, this.bomberMan);
            FrontPanel newFrontPanel = new FrontPanel(newGamePanel, newMap.getWidth(), newMap.getHeight());
            gameFrame.setContentPane(newFrontPanel);
            gameFrame.setGamePanel(newGamePanel);
            gameFrame.setFrontPanel(newFrontPanel);

            gameFrame.getFrontPanel().setWidth(newMap.getWidth());
            gameFrame.getFrontPanel().setHeight(newMap.getHeight());

            newMap.setScoreBoard(gameFrame.getScoreBoard());
            gameFrame.newTimeThread();
            gameFrame.newMonsterThread();
        }
        gameFrame.setLoadTime(spentTime);

        this.gameFrame.setSaved(true);
        this.gameFrame.setSavePath(filePath);

        bomberMan.setX(bombermanX);
        bomberMan.setY(bombermanY);
        bomberMan.setBombRange(radius);
        bomberMan.setBombLimit(bombLimit);
        bomberMan.setScore(score);
        bomberMan.setStep(step);
        if (controlBomb.equals("true"))
            bomberMan.setBombControl(true);
        else
            bomberMan.setBombControl(false);
        if (ghostAbility.equals("true"))
            bomberMan.setGhostAbility(true);
        else
            bomberMan.setGhostAbility(false);

        gameFrame.getScoreBoard().update();

        newMap.setGamePanel(gameFrame.getGamePanel());

        gameFrame.getFrontPanel().setBoundsPosition(bomberMan.getX(), bomberMan.getY());

        Sound.stopAll();
        Sound.MEGALOBOX.play(Clip.LOOP_CONTINUOUSLY);

        gameFrame.getScoreBoard().setVisible(true);
        gameFrame.setVisible(true);
    }

    public void saveDB(String name) {
        String url = "jdbc:mysql://localhost/BomberMan";
        String user = "root";
        String password = "";

        int spentTime = (int) (new Date().getTime() - gameFrame.getTime().getTime());
        String monstersText = "";
        for (Monster monster : map.getMonsters()) {
            monstersText += "MonsterLevel " + monster.getLevel() + " @ " + monster.getX() + " " + monster.getY() + '\n';
        }
        String mapTypesText = "";
        String mapItemsText = "";
        for (int i = 0; i < map.getColumns(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                mapTypesText += map.getCells()[i][j].getType();
                mapTypesText += '\n';

                mapItemsText += map.getCells()[i][j].getItem();
                mapItemsText += '\n';
            }
        }


        try {
            connection = getConnection(url, user, password);

            PreparedStatement insertGames = getInsertGamesStatement(connection);
            insertGames.setString(1, name);
            insertGames.setInt(2, levelNumber);
            insertGames.setInt(3, map.getRows());
            insertGames.setInt(4, map.getColumns());
            insertGames.setInt(5, spentTime);
            insertGames.execute();

            PreparedStatement insertBomberMans = getInsertBomberMansStatement(connection);
            insertBomberMans.setInt(1, bomberMan.getX());
            insertBomberMans.setInt(2, bomberMan.getY());
            insertBomberMans.setInt(3, bomberMan.getScore());
            insertBomberMans.setInt(4, bomberMan.getBombLimit());
            insertBomberMans.setInt(5, bomberMan.getBombRange());
            insertBomberMans.setBoolean(6, bomberMan.isBombControl());
            insertBomberMans.setInt(7, bomberMan.getStep());
            insertBomberMans.setBoolean(8, bomberMan.isGhostAbility());
            insertBomberMans.execute();

            PreparedStatement insertMaps = getInsertMapsStatement(connection);
            insertMaps.setString(1, mapTypesText);
            insertMaps.setString(2, mapItemsText);
            insertMaps.execute();

            PreparedStatement insertMonsters = getInsertMonstersStatement(connection);
            insertMonsters.setInt(1, this.monstersNumber);
            insertMonsters.setInt(2, map.getMonsters().size());
            insertMonsters.setString(3, monstersText);
            insertMonsters.execute();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void loadDB(int id, Connection connection) {
        try {
            int level = 0;
            int rows = 0;
            int columns = 0;
            int spentTime = 0;
            int bomberManX = 0;
            int bomberManY = 0;
            int score = 0;
            int bombLimit = 0;
            int bombRange = 0;
            boolean controlBomb = false;
            int step = 0;
            boolean ghostAbility = false;
            String cellsTypeText = "";
            String cellsItemText = "";
            int initialMonstersSize = 0;
            int monstersSize = 0;
            String monstersText = "";

            PreparedStatement selectGames = getGamesSelectStatement(connection);
            selectGames.setInt(1, id);
            ResultSet result = selectGames.executeQuery();
            if (result.next()) {
                level = result.getInt("Level");
                rows = result.getInt("Rows");
                columns = result.getInt("Columns");
                spentTime = result.getInt("Spent_Time");
            }

            PreparedStatement selectBomberMans = getBomberMansSelectStatement(connection);
            selectBomberMans.setInt(1, id);
            result = selectBomberMans.executeQuery();
            if (result.next()) {
                bomberManX = result.getInt("X");
                bomberManY = result.getInt("Y");
                score = result.getInt("Score");
                bombLimit = result.getInt("Bomb_Limit");
                bombRange = result.getInt("Bomb_Range");
                controlBomb = result.getBoolean("Control_Bomb");
                step = result.getInt("Step");
                ghostAbility = result.getBoolean("Ghost_Ability");
            }

            PreparedStatement selectMaps = getMapsSelectStatement(connection);
            selectMaps.setInt(1, id);
            result = selectMaps.executeQuery();
            if (result.next()) {
                cellsTypeText = result.getString("Cells_Type");
                cellsItemText = result.getString("Cells_Item");
            }

            PreparedStatement selectMonsters = getMonstersSelectStatement(connection);
            selectMonsters.setInt(1, id);
            result = selectMonsters.executeQuery();
            if (result.next()) {
                initialMonstersSize = result.getInt("Initial_Monsters_Size");
                monstersSize = result.getInt("Monsters_Size");
                monstersText = result.getString("Monsters");
            }


            Map newMap = new Map(rows, columns, 22);

            if (startingLoad) {
                startingLoad = false;
                this.bomberMan = new BomberMan(newMap);
                newMap.setBomberMan(this.bomberMan);
                this.gameFrame = new GameFrame(newMap, bomberMan, this, GameFrame.Type.SINGLE);
                this.gameFrame.newTimeThread();
                this.gameFrame.newMonsterThread();
                newMap.setScoreBoard(gameFrame.getScoreBoard());
            }
            Door door = null;

            Scanner scanner = new Scanner(cellsTypeText);
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < rows; j++) {
                    Cell cell = newMap.getCells()[i][j];
                    String type = scanner.nextLine();
                    switch (type) {
                        case "EMPTY":
                            cell.setType(Cell.CellTypes.EMPTY);
                            break;
                        case "WALL":
                            cell.setType(Cell.CellTypes.WALL);
                            break;
                        case "BLOCK":
                            cell.setType(Cell.CellTypes.BLOCK);
                            break;
                        case "BOMB":
                            cell.setType(Cell.CellTypes.BOMB);
                            new Bomb(this.bomberMan, newMap);
                    }
                }
            }

            scanner = new Scanner(cellsItemText);
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < rows; j++) {
                    Cell cell = newMap.getCells()[i][j];
                    String item = scanner.nextLine();
                    switch (item) {
                        case "Control Bombs":
                            new ControlBombs(cell, this.bomberMan);
                            break;
                        case "Decrease Bombs":
                            new DecreaseBombs(cell, this.bomberMan);
                            break;
                        case "Decrease Radius":
                            new DecreaseRadius(cell, this.bomberMan);
                            break;
                        case "Decrease Score":
                            new DecreaseScore(cell, this.bomberMan);
                            break;
                        case "Decrease Speed":
                            new DecreseSpeed(cell, this.bomberMan);
                            break;
                        case "Door":
                            door = new Door(cell, this.bomberMan, null);
                            break;
                        case "Increase Bombs":
                            new IncreaseBombs(cell, this.bomberMan);
                            break;
                        case "Increase Radius":
                            new IncreaseRadius(cell, this.bomberMan);
                            break;
                        case "Increase Score":
                            new IncreaseScore(cell, this.bomberMan);
                            break;
                        case "Increase Speed":
                            new IncreaseSpeed(cell, this.bomberMan);
                            break;
                        case "Ghost":
                            new GhostAbility(cell, this.bomberMan);
                            break;
                        case "null":
                            cell.setItem(null);
                            break;
                    }
                }
            }

            scanner = new Scanner(monstersText);
            this.monstersNumber = initialMonstersSize;
            for (int i = 0; i < monstersSize; i++) {
                scanner.next();
                int monsterLevel = scanner.nextInt();
                scanner.next();
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                Monster monster = null;
                switch (level) {
                    case 1:
                        monster = new MonsterLevel1(newMap);
                        break;
                    case 2:
                        monster = new MonsterLevel2(newMap);
                        break;
                    case 3:
                        monster = new MonsterLevel3(newMap);
                        break;
                    case 4:
                        monster = new MonsterLevel4(newMap);
                        break;

                }
                monster.setX(x);
                monster.setY(y);
                newMap.getMonsters().add(monster);
                scanner.nextLine();
            }

            door.setMonsters(newMap.getMonsters());

            setLevelNumber(level);
            this.row = rows;
            this.column = columns;
            GameConfiguration.LEVEL_TIME = (int) (Math.max(row, column) / 3);

            if (!startingLoad) {
                newMap.setBomberMan(this.bomberMan);
                gameFrame.setVisible(false);
                gameFrame.getScoreBoard().setVisible(false);
                gameFrame.setTitle("Level " + getLevelNumber());

                gameFrame.setMap(newMap);
                bomberMan.setMap(newMap);
                gameFrame.setSize();
                gameFrame.getGamePanel().setMap(newMap);
                GamePanel newGamePanel = new GamePanel(newMap, this.bomberMan);
                FrontPanel newFrontPanel = new FrontPanel(newGamePanel, newMap.getWidth(), newMap.getHeight());
                gameFrame.setContentPane(newFrontPanel);
                gameFrame.setGamePanel(newGamePanel);
                gameFrame.setFrontPanel(newFrontPanel);

                gameFrame.getFrontPanel().setWidth(newMap.getWidth());
                gameFrame.getFrontPanel().setHeight(newMap.getHeight());

                newMap.setScoreBoard(gameFrame.getScoreBoard());
                gameFrame.newTimeThread();
                gameFrame.newMonsterThread();
            }
            gameFrame.setLoadTime(spentTime);

            bomberMan.setX(bomberManX);
            bomberMan.setY(bomberManY);
            bomberMan.setBombRange(bombRange);
            bomberMan.setBombLimit(bombLimit);
            bomberMan.setScore(score);
            bomberMan.setStep(step);
            bomberMan.setBombControl(controlBomb);
            bomberMan.setGhostAbility(ghostAbility);
            gameFrame.getScoreBoard().update();

            newMap.setGamePanel(gameFrame.getGamePanel());

            gameFrame.getFrontPanel().setBoundsPosition(bomberMan.getX(), bomberMan.getY());

            Sound.stopAll();
            Sound.MEGALOBOX.play(Clip.LOOP_CONTINUOUSLY);

            gameFrame.getScoreBoard().setVisible(true);
            gameFrame.setVisible(true);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public PreparedStatement getInsertGamesStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("Insert into Games " +
                "(Name,Level,Rows,Columns,Spent_Time) VALUES (?,?,?,?,?)");
    }

    public PreparedStatement getInsertBomberMansStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO BomberMans" +
                "(X,Y,Score,Bomb_Limit,Bomb_Range,Control_Bomb,Step, Ghost_Ability)" +
                "VALUES (?,?,?,?,?,?,?,?)");
    }

    public PreparedStatement getInsertMapsStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO Maps" +
                "(Cells_Type,Cells_Item) VALUES (?,?)");
    }

    public PreparedStatement getInsertMonstersStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("INSERT INTO Monsters" +
                "(Initial_Monsters_Size,Monsters_Size,Monsters) VALUES (?,?,?)");
    }

    public PreparedStatement getGamesSelectStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("Select Level, Rows, Columns, Spent_Time from Games where id = ?",
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE,
                ResultSet.CLOSE_CURSORS_AT_COMMIT);
    }

    public PreparedStatement getBomberMansSelectStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("Select X, Y, Score, Bomb_Limit, Bomb_Range, Control_Bomb, Step, Ghost_Ability" +
                        " from BomberMans where id = ?", ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE,
                ResultSet.CLOSE_CURSORS_AT_COMMIT);
    }

    public PreparedStatement getMapsSelectStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("Select Cells_Type, Cells_Item from Maps where id = ?",
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE,
                ResultSet.CLOSE_CURSORS_AT_COMMIT);
    }

    public PreparedStatement getMonstersSelectStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("Select Initial_Monsters_Size, Monsters_Size, Monsters " +
                        "from Monsters where id = ?",
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE,
                ResultSet.CLOSE_CURSORS_AT_COMMIT);
    }
}