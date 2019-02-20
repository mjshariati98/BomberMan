package Threads;

import Configs.GameConfiguration;
import Models.Monster;
import views.GameFrame;

import java.util.Date;

public class MonsterThread extends Thread {
    private GameFrame gameFrame;
    private boolean timeThreadStarted;

    public MonsterThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public void run() {
        while (gameFrame.isRun()) {
            for (Monster monster : gameFrame.getMap().getMonsters()) {
                monster.move();
            }
            gameFrame.getGamePanel().repaint();
            if (!timeThreadStarted &&
                    ((new Date().getTime() - gameFrame.getTime().getTime()) + gameFrame.getLoadTime())
                            >= GameConfiguration.LEVEL_TIME * 60 * 1000) {
                timeThreadStarted = true;
                gameFrame.getTimeThread().start();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
    }
}
