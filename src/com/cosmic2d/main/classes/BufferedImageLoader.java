package com.cosmic2d.main.classes;

import com.cosmic2d.main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BufferedImageLoader
{
    private BufferedImage image;

    public BufferedImage loadImage(String path) throws IOException
    {
        image = ImageIO.read(Game.class.getResource(path));
        return image;
    }
}
