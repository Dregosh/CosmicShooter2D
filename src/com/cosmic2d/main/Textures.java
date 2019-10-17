package com.cosmic2d.main;

import com.cosmic2d.main.classes.SpriteSheet;

import java.awt.image.BufferedImage;

public class Textures
{
    private SpriteSheet ss;
    public BufferedImage[] player = new BufferedImage[3];
    public BufferedImage[] missile = new BufferedImage[3];
    public BufferedImage[] enemy3p = new BufferedImage[3];
    public BufferedImage[] explosion = new BufferedImage[3];
    public BufferedImage[] enemy2p = new BufferedImage[3];
    public BufferedImage[] enemy1p = new BufferedImage[3];
    public BufferedImage[] enemy0p = new BufferedImage[3];

    public Textures(Game game)
    {
        ss = new SpriteSheet(game.getSpriteSheet());
        getTextures();
    }

    private void getTextures()
    {
        player[0] = ss.grabImage(1, 1, 32, 32);
        player[1] = ss.grabImage(1, 2, 32, 32);
        player[2] = ss.grabImage(1, 3, 32, 32);

        missile[0] = ss.grabImage(2, 1, 32, 32);
        missile[1] = ss.grabImage(2, 2, 32, 32);
        missile[2] = ss.grabImage(2, 3, 32, 32);

        enemy3p[0] = ss.grabImage(3, 1, 32, 32);
        enemy3p[1] = ss.grabImage(3, 2, 32, 32);
        enemy3p[2] = ss.grabImage(3, 3, 32, 32);

        explosion[0] = ss.grabImage(4, 1, 32, 32);
        explosion[1] = ss.grabImage(4, 2, 32, 32);
        explosion[2] = ss.grabImage(4, 3, 32, 32);

        enemy2p[0] = ss.grabImage(5, 1, 32, 32);
        enemy2p[1] = ss.grabImage(5, 2, 32, 32);
        enemy2p[2] = ss.grabImage(5, 3, 32, 32);

        enemy1p[0] = ss.grabImage(6, 1, 32, 32);
        enemy1p[1] = ss.grabImage(6, 2, 32, 32);
        enemy1p[2] = ss.grabImage(6, 3, 32, 32);

        enemy0p[0] = ss.grabImage(7, 1, 32, 32);
        enemy0p[1] = ss.grabImage(7, 2, 32, 32);
        enemy0p[2] = ss.grabImage(7, 3, 32, 32);
    }
}
