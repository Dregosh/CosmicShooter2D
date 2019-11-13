package com.cosmic2d.main.classes;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public enum Music
{
    MENU("168.wav"),
    GAMEPLAY("c-jungle.wav"),
    HIGH_SCORES("c-ending.wav"),
    GAMEOVER("c-gameover.wav");

    public enum PlayType
    {
        ONCE,
        LOOPED;
    }

    private Clip clip;

    Music(String musicLocation)
    {
        URL url = this.getClass().getResource(musicLocation);
        try (AudioInputStream audioInput =
                     AudioSystem.getAudioInputStream(url))
        {
            clip = AudioSystem.getClip();
            clip.open(audioInput);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void play()
    {
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play(PlayType pt)
    {
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
        if (pt == PlayType.LOOPED)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop()
    {
        clip.stop();
    }

    public static void stopAllMusic()
    {
        Music.MENU.stop();
        Music.GAMEPLAY.stop();
        Music.HIGH_SCORES.stop();
    }
}
