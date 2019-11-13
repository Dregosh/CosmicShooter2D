package com.cosmic2d.main.classes;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public enum SoundFX
{
    MISSILE_SHOT("res\\missile-shot.wav"),
    EXPLOSION("res\\explosion.wav"),
    NEW_SQUADRON("res\\new_squadron.wav");

    private Clip clip;

    SoundFX(String soundLocation)
    {
        File soundFile = new File(soundLocation);
        if (soundFile.exists())
        {
            try (AudioInputStream audioInput =
                         AudioSystem.getAudioInputStream(soundFile))
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
        else
        {
            System.out.println("Nie znaleziono pliku z efektem dźwiękowym.");
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
