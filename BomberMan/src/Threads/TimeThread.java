package Threads;

import Configs.Sound;
import views.GameFrame;

public class TimeThread extends Thread{
    private GameFrame gameFrame;

    public TimeThread(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    public void run() {
        Sound.TIMEOVER.play(0);
        while (gameFrame.isRun()) {
            gameFrame.getBomberMan().setScore(gameFrame.getBomberMan().getScore() - 1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
