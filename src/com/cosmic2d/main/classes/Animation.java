package com.cosmic2d.main.classes;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {

    private int speed;
    private int frames;
    private int index = 0;
    private int count = 0;
    //private int col;
    //private int row;
    //private BufferedImage image;
    private BufferedImage[] images;
    private BufferedImage currentImage;

    public Animation(BufferedImage[] images, int speed)
    {
        this.images = images;
        //this.image = images[0];
        this.frames = images.length;
        this.speed = speed;
        //this.col = col;
        //this.row = row;
        //images = new BufferedImage[frames];
        //fillSprites();
    }

    /*public void fillSprites()
    {
        SpriteSheet ss = new SpriteSheet(image);
        int k = 0;
        for (int i = 1; i <= row; i++)
        {
            for (int j = 1; j <= col; j++, k++)
            {
                images[k] = ss.grabImage(i, j,
                        (int) image.getWidth() / col,
                        (int) image.getHeight() / row);
            }
        }
    }*/

    public void nextFrame() {

        currentImage = images[count];
        count++;
        if (count >= frames - 1) {
            // You can modify frames - 1 to another number if your image
            // does not have sprite sheets in all the image.
            // If your image has sprite sheets in all the image you can
            // put only frames.

            count = 0;
        }
    }

    public void runAnimation() {

        index++;
        if (index > speed)
        {
            index = 0;
            nextFrame();
        }
    }

    public void drawAnimation(Graphics2D g2, double x, double y, int offset)
    {
        g2.drawImage(currentImage, (int) x - offset, (int) y, null);
    }

    /*public void setImage(BufferedImage image)
    {
        this.image = image;
    }*/
    public void setFrames(int frames) {

        this.frames = frames;
    }

    /*public void setCol(int col)
    {

        this.col = col;

    }

    public void setRow(int row)
    {

        this.row = row;

    }*/
}