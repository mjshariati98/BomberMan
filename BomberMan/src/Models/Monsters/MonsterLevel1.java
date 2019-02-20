package Models.Monsters;

import Configs.Images;
import Models.Map;
import Models.Monster;

public class MonsterLevel1 extends Monster {
    public MonsterLevel1(Map map) {
        super(map, 20, 1);

        loadImages();
    }

    public MonsterLevel1(Map map, int id){
        super(map,20,1,id);
        setStep(5);

        loadImages();
    }

    private void loadImages(){
        setFront1(Images.monster1_Front1);
        setFront2(Images.monster1_Front2);
        setFront3(Images.monster1_Front3);
        setBack1(Images.monster1_Back1);
        setBack2(Images.monster1_Back2);
        setBack3(Images.monster1_Back3);
        setLeft1(Images.monster1_Left1);
        setLeft2(Images.monster1_Left2);
        setLeft3(Images.monster1_Left3);
        setRight1(Images.monster1_Right1);
        setRight2(Images.monster1_Right2);
        setRight3(Images.monster1_Right3);

        setImage(getFront2());
    }

    @Override
    public void move() {
        randomMove();
    }
}
