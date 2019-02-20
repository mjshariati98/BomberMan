package Configs;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public enum Sound {
    MEGALOBOX("Sounds/MEGALOBOX.wav"),
    TICKTOCK("Sounds/Tick Tock.wav"),
    EXPLOSION1("Sounds/explosion.wav"),
    EXPLOSION2("Sounds/explosion.wav"),
    SELECT("Sounds/select.wav"),
    TIMEOVER("Sounds/Time Over.wav"),
    MONSTERDIE("Sounds/MonsterDie.wav"),
    BOMBERMAN("Sounds/Bomberman.wav"),
    GAMEOVER("Sounds/gameOver.wav"),
    NO("Sounds/No.wav"),
    OOO("Sounds/Ooo.wav"),
    POWERUP("Sounds/powerUp.wav"),
    DOOR("Sounds/GOAL.wav");

    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;

    // Constructor to construct each element of the enum with its own sound file.
    Sound(String soundFileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play or Re-play the sound effect from the beginning, by rewinding.
    public void play(int loop) {
        if (GameConfiguration.SOUND) {
            if (clip.isRunning()) {
            }
//            if (clip.isRunning())
//                clip.stop();   // Stop the player if it is still running
            clip.setFramePosition(0); // rewind to the beginning
            clip.loop(loop);
            clip.start();     // Start playing
        }
    }

    public void stop() {
        clip.stop();
    }

    // Optional static method to pre-load all the sound files.
    static void init() {
        values(); // calls the constructor for all the elements
    }

    public static void stopAll() {
        Sound.MEGALOBOX.stop();
        Sound.TICKTOCK.stop();
        Sound.EXPLOSION1.stop();
        Sound.EXPLOSION2.stop();
    }
}
