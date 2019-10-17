package com.cosmic2d.main;

import com.cosmic2d.main.classes.Animation;
import com.cosmic2d.main.classes.EntityB;

import java.awt.*;

public class Explotion extends GameObject implements EntityB
{
    private Game game;
    private long creationTime;
    Animation anim;

    public Explotion(Game game, double x, double y)
    {
        super(x, y);
        this.game = game;
        this.creationTime = System.currentTimeMillis();
        anim = new Animation(game.getTex().explosion, 5);
    }

    @Override
    public void tick()
    {
        anim.runAnimation();
    }

    @Override
    public void render(Graphics2D g2)
    {
        anim.drawAnimation(g2, getX(), getY(), 0);
    }

    public long getCreationTime()
    {
        return creationTime;
    }
}
