package com.cosmic2d.main.classes;

import com.cosmic2d.main.Game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public enum SoundFX
{
    MISSILE_SHOT("res/missile-shot.wav"),
    EXPLOSION("res/explosion.wav"),
    NEW_SQUADRON("res/new_squadron.wav");

    private Clip clip;

    SoundFX(String soundLocation)
    {
        URL url = Game.class.getResource(soundLocation);
        try (AudioInputStream audioInput =
                     AudioSystem.getAudioInputStream(url))
        {
            this.clip = AudioSystem.getClip();
            this.clip.open(audioInput);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void play()
    {
        if (this.clip.isRunning())
            this.clip.stop();
        this.clip.setFramePosition(0);
        this.clip.start();
    }

    public void stop()
    {
        this.clip.stop();
    }
}
