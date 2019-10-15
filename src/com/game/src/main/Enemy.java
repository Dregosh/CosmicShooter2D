package com.game.src.main;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import com.game.src.main.libs.Animation;

import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject implements EntityB
{
    private Textures tex;
    Random r = new Random();
    private Game game;
    private Controller controller;
    private double maxSpeed;
    private double speed;
    private double vertModifier;
    private Animation anim;

    public Enemy(double x, double y, Textures tex, Controller controller, Game game)
    {
        super(x, y);
        this.tex = tex;
        this.maxSpeed = 1.0;
        this.speed = 1.0 + (r.nextDouble() * maxSpeed);
        this.vertModifier = (r.nextInt(3) - 1) / 2.0;
        this.controller = controller;
        this.game = game;
        anim = new Animation(tex.enemy, 5);
    }

    @Override
    public void tick()
    {
        y += this.speed;
        //x += this.vertModifier;

        /*if ((x <= -25) || (x >= (Game.WIDTH * Game.SCALE - 7)))
        {
            vertModifier = -vertModifier;
        }*/

        if (y > (Game.HEIGHT * Game.SCALE))
        {
            x = r.nextInt(Game.WIDTH * Game.SCALE - 32);
            y = -35;
            /*if (r.nextInt(2) == 1)
                vertModifier = -vertModifier;*/
        }

        for (int i = 0; i < game.getEntitiesA().size(); i++)
        {
            EntityA tempEntA = game.getEntitiesA().get(i);
            if (Physics.collision(this, tempEntA))
            {
                controller.removeEntity(tempEntA);
                controller.removeEntity(this);
                game.setEnemyKilled(game.getEnemyKilled() + 1);
            }
        }
        anim.runAnimation();
    }

    @Override
    public void render(Graphics2D g2)
    {
        //g2.rotate(45, x + 16, y + 16);
        anim.drawAnimation(g2, x, y, 0);
        //g2.rotate(-45, x + 16, y + 16);
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
}
