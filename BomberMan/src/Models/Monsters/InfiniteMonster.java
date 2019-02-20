package Models.Monsters;

import Models.Map;
import Models.Monster;

public class InfiniteMonster extends Monster {

    public InfiniteMonster(Map map, int id) {
        super(map, 0, 0, id);

        loadImages();
    }

    public void loadImages(){
        setFront1(getImage());
        setFront2(getImage());
        setFront3(getImage());
        setBack1(getImage());
        setBack2(getImage());
        setBack3(getImage());
        setLeft1(getImage());
        setLeft2(getImage());
        setLeft3(getImage());
        setRight1(getImage());
        setRight2(getImage());
        setRight3(getImage());
    }


}
