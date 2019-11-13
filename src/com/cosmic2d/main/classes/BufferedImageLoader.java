package com.cosmic2d.main.classes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BufferedImageLoader
{
    private BufferedImage image;

    public BufferedImage loadImage(String path) throws IOException
    {
        //image = ImageIO.read(getClass().getResource(path));
        image = ImageIO.read(new File(path));
        return image;
    }
}
