package Network;

import Models.*;

import java.util.ArrayList;
import java.util.List;

public class GameThread extends Thread {
    private Map map;

    public GameThread(Map map) {
        this.map = map;
    }

    @Override
    public void run() {
        while (true) {
            for (Monster monster : map.getMonsters()) {
                monster.move();
            }
            int faildeBomberMans = 0;
            for (BomberMan bomberMan : map.getBomberMans()) {
                // check all bomberMans failed
                if (bomberMan.isFailed())
                    faildeBomberMans++;

                List<Bomb> bombs = bomberMan.getBobms();
                List<Bomb> explodedBombs = new ArrayList<>();
                for (Bomb bomb : bombs) {
                    if (bomb.isExploded()) {
                        int bombRange = bomberMan.getBombRange();
                        bomb.explode(bombRange, map.getBomberMans());
                        explodedBombs.add(bomb);
                    }
                }
                bomberMan.getBobms().removeAll(explodedBombs);
                explodedBombs.clear();
            }
            if (faildeBomberMans == map.getBomberMans().size()) {
                for (BomberMan bomberMan : map.getBomberMans()) {
                    Cell goodcell = map.getGameServer().getGoodCell();
                    bomberMan.setX(goodcell.getX());
                    bomberMan.setY(goodcell.getY());
                    bomberMan.respawningStart();
                    bomberMan.setFailed(false);
                }
            }

            map.getGamePanel().repaint();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
