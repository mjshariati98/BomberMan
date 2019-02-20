package Models.Monsters;

import Configs.Images;
import Models.Map;
import Models.Monster;

public class MonsterLevel2 extends Monster {

    public MonsterLevel2(Map map) {
        super(map, 40, 2);

        loadImages();
    }

    public MonsterLevel2(Map map, int id) {
        super(map, 40, 2, id);

        loadImages();
    }

    private void loadImages() {
        setFront1(Images.monster2_Front1);
        setFront2(Images.monster2_Front2);
        setFront3(Images.monster2_Front3);
        setBack1(Images.monster2_Back1);
        setBack2(Images.monster2_Back2);
        setBack3(Images.monster2_Back3);
        setLeft1(Images.monster2_Left1);
        setLeft2(Images.monster2_Left2);
        setLeft3(Images.monster2_Left3);
        setRight1(Images.monster2_Right1);
        setRight2(Images.monster2_Right2);
        setRight3(Images.monster2_Right3);

        setImage(getFront2());
    }

}
