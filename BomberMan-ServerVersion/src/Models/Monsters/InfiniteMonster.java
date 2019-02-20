package Models.Monsters;

import Configs.GameConfiguration;
import Models.BomberMan;
import Models.Map;
import Models.Monster;
import utils.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class InfiniteMonster extends Monster {
    private Class monsterClass;
    private AbstractNewMonster newMonster;
    private int startingLevel;
    private String imagePath;

    public InfiniteMonster(Map map, Class monsterClass, int id) {
        super(map, id);
        this.monsterClass = monsterClass;
        try {
            Constructor constructor = monsterClass.getConstructor(int.class, int.class, int.class, int.class);
            newMonster = (AbstractNewMonster) constructor.newInstance(map.getRows(), map.getColumns(),
                    GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT);
            Method startingLevelGetterMethod = monsterClass.getMethod("getStartingLevel");
            startingLevel = (int) (startingLevelGetterMethod.invoke(newMonster));
//            newMonster.getStartingLevel();
            Method imageGetterMethod = monsterClass.getMethod("getImagePath");
            imagePath = (String) imageGetterMethod.invoke(newMonster);
//            newMonster.getImagePath();
            BufferedImage image = ImageIO.read(new File(imagePath));
            setImage(image.getScaledInstance(GameConfiguration.MONSTER_WIDTH,
                    GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH));
            Method deathScoreGetterMethod = monsterClass.getMethod("getDeathScore");
            setDeathScore((int) (deathScoreGetterMethod.invoke(newMonster)));
//            newMonster.getDeathScore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getStartingLevel() {
        return startingLevel;
    }

    public String getEncodedImage() {
        try {
            File file = new File(imagePath);
            FileInputStream fileInputStreamReader = null;
            fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            String encodedFile = Base64.getEncoder().encodeToString(bytes);
            fileInputStreamReader.close();
            return encodedFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void move() {
        try {
            Method speedMethod = monsterClass.getMethod("getSpeed");
            int step = (int) speedMethod.invoke(newMonster);
//            newMonster.getSpeed();
            Method moveMethod = monsterClass.getMethod("move", int.class, int.class,
                    int.class, int.class, CellType[][].class, int.class, List.class);


            CellType[][] cells = new CellType[getMap().getColumns()][getMap().getRows()];
            for (int i = 0; i < getMap().getColumns(); i++) {
                for (int j = 0; j < getMap().getRows(); j++) {
                    switch (getMap().getCells()[i][j].getType()) {
                        case EMPTY:
                            cells[i][j] = CellType.EMPTY;
                            break;
                        case BOMB:
                            cells[i][j] = CellType.BOMB;
                            break;
                        case BLOCK:
                            cells[i][j] = CellType.BLOCK;
                            break;
                        case WALL:
                            cells[i][j] = CellType.WALL;
                            break;
                    }
                }
            }
            List<BomberManDetails> bomberManDetailsList = new ArrayList<>();
            for (BomberMan bomberMan : getMap().getBomberMans()) {
                if (!bomberMan.isFailed()) {
                    BomberManDetails bd = new BomberManDetails(bomberMan);
                    bomberManDetailsList.add(bd);
                }
            }
            Direction direction = (Direction) moveMethod.invoke(newMonster, getX(), getY(),
                    getMap().getRows(), getMap().getColumns(), cells,
                    GameConfiguration.CELL_WIDTH, bomberManDetailsList);
//            newMonster.move();

            setStep(step);
            setDirection(direction);
            moveInfinite(direction);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
