package Models.Monsters;

import Configs.GameConfiguration;
import Configs.Images;
import Models.Map;
import Models.Monster;

public class MonsterLevel3 extends Monster {

    public MonsterLevel3(Map map) {
        super(map, 60, 3);
        setStep(GameConfiguration.MONSTERS_3_4_STEP);

        loadImages();
    }

    public MonsterLevel3(Map map, int id){
        super(map, 60, 3, id);
        setStep(GameConfiguration.MONSTERS_3_4_STEP);

        loadImages();
    }
    private void loadImages(){
        setFront1(Images.monster3_Front1);
        setFront2(Images.monster3_Front2);
        setFront3(Images.monster3_Front3);
        setBack1(Images.monster3_Back1);
        setBack2(Images.monster3_Back2);
        setBack3(Images.monster3_Back3);
        setLeft1(Images.monster3_Left1);
        setLeft2(Images.monster3_Left2);
        setLeft3(Images.monster3_Left3);
        setRight1(Images.monster3_Right1);
        setRight2(Images.monster3_Right2);
        setRight3(Images.monster3_Right3);

        setImage(getFront2());
    }
}
