package com.cosmic2d.main.classes;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public enum Music
{
    MENU("res\\168.wav"),
    GAMEPLAY("res\\c-jungle.wav"),
    HIGH_SCORES("res\\c-ending.wav");

    private Clip clip;

    Music(String musicLocation)
    {
        File musicPath = new File(musicLocation);
        if (musicPath.exists())
        {
            try (AudioInputStream audioInput =
                         AudioSystem.getAudioInputStream(musicPath))
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
        else
        {
            System.out.println("Nie znaleziono pliku z muzyką");
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
