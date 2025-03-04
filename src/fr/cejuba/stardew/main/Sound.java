package fr.cejuba.stardew.main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class Sound {
    private MediaPlayer mediaPlayer;
    private final URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/fr/cejuba/stardew/sounds/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/fr/cejuba/stardew/sounds/coin.wav");
        soundURL[2] = getClass().getResource("/fr/cejuba/stardew/sounds/powerup.wav");
        soundURL[3] = getClass().getResource("/fr/cejuba/stardew/sounds/unlock.wav");
        soundURL[4] = getClass().getResource("/fr/cejuba/stardew/sounds/fanfare.wav");
        soundURL[5] = getClass().getResource("/fr/cejuba/stardew/sounds/hitmonster.wav");
        soundURL[6] = getClass().getResource("/fr/cejuba/stardew/sounds/receivedamage.wav");
        soundURL[7] = getClass().getResource("/fr/cejuba/stardew/sounds/swingweapon.wav"); // TODO : Not created yet
        soundURL[8] = getClass().getResource("/fr/cejuba/stardew/sounds/levelup.wav");
    }

    public void setFile(int i) {
        try {
            if (soundURL[i] != null) {
                Media sound = new Media(soundURL[i].toString());
                mediaPlayer = new MediaPlayer(sound);
                System.out.println("MediaPlayer initialized for file: " + soundURL[i]);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
            System.out.println("MediaPlayer played");
        } else {
            System.out.println("MediaPlayer not initialized");
        }
    }

    public void loopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    public void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
