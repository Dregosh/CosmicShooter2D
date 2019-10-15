package com.game.src.main;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import com.game.src.main.libs.Animation;

import java.awt.*;

public class Player extends GameObject implements EntityA
{
    private double velX = 0;
    private double velY = 0;
    private Textures tex;
    private Game game;
    private int health;

    Animation anim;

    public Player(double x, double y, Textures tex, Game game)
    {
        super(x, y);
        this.tex = tex;
        this.game = game;
        this.health = 100;
        anim = new Animation(tex.player, 5);
    }

    @Override
    public void tick()
    {
        x += velX;
        y += velY;

        //Player movement bounds
        if (x <= 0) x = 0;
        if (x >= 640 - 32) x = 640 - 32;
        if (y <= 0) y = 0;
        if (y >= 480 - 44) y = 480 - 44;

        for (int i = 0; i < game.getEntitiesB().size(); i++)
        {
            EntityB tempEntB = game.getEntitiesB().get(i);

            if (Physics.collision(this, tempEntB))
            {
                game.getEntitiesB().remove(tempEntB);
                game.setEnemyKilled(game.getEnemyKilled() + 1);
                this.health -= 10;
            }
            if (this.health <= 0)
                game.setState(STATE.GAME_OVER);
        }
        anim.runAnimation();
    }

    @Override
    public void render(Graphics2D g2)
    {
        anim.drawAnimation(g2, x, y, 0);
    }

    @Override
    public double getX()
    {
        return x;
    }
    public void setX(double x)
    {
        this.x = x;
    }

    @Override
    public double getY()
    {
        return y;
    }
    public void setY(double y)
    {
        this.y = y;
    }

    public void setVelX(double velX)
    {
        this.velX = velX;
    }
    public void setVelY(double velY)
    {
        this.velY = velY;
    }

    public int getHealth()
    {
        return health;
    }
}
