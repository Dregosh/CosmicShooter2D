package com.game.src.main;

import com.game.src.main.classes.EntityA;
import com.game.src.main.libs.Animation;

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
        y -= 5;
        anim.runAnimation();
    }

    @Override
    public void render(Graphics2D g2)
    {
        anim.drawAnimation(g2, x, y, 0);
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
        return new Rectangle((int) x + 11, (int) y, 9, 32);
    }

    @Override
    public double getX()
    {
        return x;
    }

    @Override
    public double getY()
    {
        return y;
    }
}
