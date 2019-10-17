package com.cosmic2d.main;

import com.cosmic2d.main.classes.EntityA;
import com.cosmic2d.main.classes.Animation;

import java.awt.*;

public class Bullet extends GameObject implements EntityA
{
    private Textures tex;
    private Game game;
    private Animation anim;

    public Bullet(double x, double y, Textures tex, Game game)
    {
        super(x, y);
        this.tex = tex;
        this.game = game;
        anim = new Animation(tex.missile, 5);
    }

    @Override
    public void tick()
    {
        setY(getY() - 5);
        anim.runAnimation();
    }

    @Override
    public void render(Graphics2D g2)
    {
        anim.drawAnimation(g2, getX(), getY(), 0);
    }

    /**
     * Overridden method, because bullet is smaller than regular 32x32px
     * sprite (currently: 9x32px positioned in the middle of 32x32px square)
     * @param width regular sprite width, NOT used in this method
     * @param height regular sprite height, NOT used in this method
     * @return bounds of the missile graphic (rectangle 9x32 px).
     */
    @Override
    public Rectangle getBounds(int width, int height)
    {
        return new Rectangle((int) getX() + 11, (int) getY(), 9, 32);
    }
}
