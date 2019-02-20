package Configs;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Images {
    public static Image bomb1;
    public static Image bomb2;
    public static Image bomb3;
    public static Image spark;
    public static Image monster1_Front1;
    public static Image monster1_Front2;
    public static Image monster1_Front3;
    public static Image monster1_Back1;
    public static Image monster1_Back2;
    public static Image monster1_Back3;
    public static Image monster1_Left1;
    public static Image monster1_Left2;
    public static Image monster1_Left3;
    public static Image monster1_Right1;
    public static Image monster1_Right2;
    public static Image monster1_Right3;
    public static Image monster2_Front1;
    public static Image monster2_Front2;
    public static Image monster2_Front3;
    public static Image monster2_Back1;
    public static Image monster2_Back2;
    public static Image monster2_Back3;
    public static Image monster2_Left1;
    public static Image monster2_Left2;
    public static Image monster2_Left3;
    public static Image monster2_Right1;
    public static Image monster2_Right2;
    public static Image monster2_Right3;
    public static Image monster3_Front1;
    public static Image monster3_Front2;
    public static Image monster3_Front3;
    public static Image monster3_Back1;
    public static Image monster3_Back2;
    public static Image monster3_Back3;
    public static Image monster3_Left1;
    public static Image monster3_Left2;
    public static Image monster3_Left3;
    public static Image monster3_Right1;
    public static Image monster3_Right2;
    public static Image monster3_Right3;
    public static Image monster4_Front1;
    public static Image monster4_Front2;
    public static Image monster4_Front3;
    public static Image monster4_Back1;
    public static Image monster4_Back2;
    public static Image monster4_Back3;
    public static Image monster4_Left1;
    public static Image monster4_Left2;
    public static Image monster4_Left3;
    public static Image monster4_Right1;
    public static Image monster4_Right2;
    public static Image monster4_Right3;

    public static Image controlBomb;
    public static Image decreaseBombs;
    public static Image decreaseRadius;
    public static Image decreaseScore;
    public static Image decreaseSpeed;
    public static Image door;
    public static Image increaseBombs;
    public static Image increaseRadius;
    public static Image increaseScore;
    public static Image increaseSpeed;
    public static Image ghost;

    public static Image bomberMan_Front1;
    public static Image bomberMan_Front2;
    public static Image bomberMan_Front3;
    public static Image bomberMan_Front4;
    public static Image bomberMan_Back1;
    public static Image bomberMan_Back2;
    public static Image bomberMan_Back3;
    public static Image bomberMan_Back4;
    public static Image bomberMan_Left1;
    public static Image bomberMan_Left2;
    public static Image bomberMan_Left3;
    public static Image bomberMan_Left4;
    public static Image bomberMan_Right1;
    public static Image bomberMan_Right2;
    public static Image bomberMan_Right3;
    public static Image bomberMan_Right4;

    static {
        try {
            bomb1 = ImageIO.read(new File("src/Images/bomb sprite sheet/bomb-1.png"))
                    .getScaledInstance(GameConfiguration.BOMB_WIDTH, GameConfiguration.BOMB_HEIGHT, Image.SCALE_SMOOTH);
            bomb2 = ImageIO.read(new File("src/Images/bomb sprite sheet/bomb-2.png"))
                    .getScaledInstance(GameConfiguration.BOMB_WIDTH, GameConfiguration.BOMB_HEIGHT, Image.SCALE_SMOOTH);
            bomb3 = ImageIO.read(new File("src/Images/bomb sprite sheet/bomb-3.png"))
                    .getScaledInstance(GameConfiguration.BOMB_WIDTH, GameConfiguration.BOMB_HEIGHT, Image.SCALE_SMOOTH);
            spark = ImageIO.read(new File("src/Images/spark.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT, Image.SCALE_SMOOTH);


            monster1_Front1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/front-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster1_Front2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/front-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster1_Front3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/front-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster1_Back1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/back-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster1_Back2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/back-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster1_Back3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/back-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster1_Left1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/left-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster1_Left2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/left-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster1_Left3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/left-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster1_Right1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/right-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster1_Right2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/right-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster1_Right3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/yellow monster/right-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Front1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/front-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Front2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/front-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Front3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/front-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Back1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/back-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Back2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/back-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Back3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/back-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Left1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/left-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Left2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/left-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Left3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/left-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Right1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/right-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Right2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/right-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster2_Right3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/white monster/right-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Front1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/front-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Front2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/front-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Front3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/front-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Back1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/back-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Back2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/back-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Back3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/back-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Left1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/left-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Left2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/left-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Left3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/left-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Right1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/right-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Right2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/right-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster3_Right3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/purple monster/right-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Front1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/front-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Front2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/front-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Front3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/front-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Back1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/back-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Back2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/back-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Back3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/back-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Left1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/left-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Left2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/left-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Left3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/left-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Right1 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/right-1.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Right2 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/right-2.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);
            monster4_Right3 = ImageIO.read(new File("src/Images/character sprite sheet/monster/black monster/right-3.png"))
                    .getScaledInstance(GameConfiguration.MONSTER_WIDTH, GameConfiguration.MONSTER_HEIGHT, Image.SCALE_SMOOTH);

            controlBomb = ImageIO.read(new File("src/Images/Items/Control Bombs.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT, Image.SCALE_SMOOTH);
            decreaseBombs = ImageIO.read(new File("src/Images/Items/Decrease Bombs.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT, Image.SCALE_SMOOTH);
            decreaseRadius = ImageIO.read(new File("src/Images/Items/Decrease Radius.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT, Image.SCALE_SMOOTH);
            decreaseScore = ImageIO.read(new File("src/Images/Items/Decrease Score.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT, Image.SCALE_SMOOTH);
            decreaseSpeed = ImageIO.read(new File("src/Images/Items/Decrease Speed.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT, Image.SCALE_SMOOTH);
            door = ImageIO.read(new File("src/Images/Items/Door.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT, Image.SCALE_SMOOTH);
            increaseBombs = ImageIO.read(new File("src/Images/Items/Increase Bombs.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT, Image.SCALE_SMOOTH);
            increaseRadius = ImageIO.read(new File("src/Images/Items/Increase Radius.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT, Image.SCALE_SMOOTH);
            increaseScore = ImageIO.read(new File("src/Images/Items/Increase Score.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT, Image.SCALE_SMOOTH);
            increaseSpeed = ImageIO.read(new File("src/Images/Items/Increase Speed.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT, Image.SCALE_SMOOTH);
            ghost = ImageIO.read(new File("Src/Images/Items/ghost.png"))
                    .getScaledInstance(GameConfiguration.CELL_WIDTH, GameConfiguration.CELL_HEIGHT,Image.SCALE_SMOOTH);


            bomberMan_Front1 = ImageIO.read(new File("src/Images/character sprite sheet/front-1.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Front2 = ImageIO.read(new File("src/Images/character sprite sheet/front-2.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Front3 = ImageIO.read(new File("src/Images/character sprite sheet/front-3.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Front4 = ImageIO.read(new File("src/Images/character sprite sheet/front-4.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Back1 = ImageIO.read(new File("src/Images/character sprite sheet/back-1.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Back2 = ImageIO.read(new File("src/Images/character sprite sheet/back-2.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Back3 = ImageIO.read(new File("src/Images/character sprite sheet/back-3.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Back4 = ImageIO.read(new File("src/Images/character sprite sheet/back-4.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Right1 = ImageIO.read(new File("src/Images/character sprite sheet/right-1.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Right2 = ImageIO.read(new File("src/Images/character sprite sheet/right-2.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Right3 = ImageIO.read(new File("src/Images/character sprite sheet/right-3.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Right4 = ImageIO.read(new File("src/Images/character sprite sheet/right-4.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Left1 = ImageIO.read(new File("src/Images/character sprite sheet/left-1.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Left2 = ImageIO.read(new File("src/Images/character sprite sheet/left-2.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Left3 = ImageIO.read(new File("src/Images/character sprite sheet/left-3.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);
            bomberMan_Left4 = ImageIO.read(new File("src/Images/character sprite sheet/left-4.png"))
                    .getScaledInstance(GameConfiguration.BOMBERMAN_WIDTH, GameConfiguration.BOMBERMAN_HEIGHT, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
