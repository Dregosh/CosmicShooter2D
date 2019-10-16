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
    private Animation anim;

    public Enemy(double x, double y, Textures tex, Controller controller, Game game)
    {
        super(x, y);
        this.tex = tex;
        this.maxSpeed = 1.0;
        this.speed = 1.0 + (r.nextDouble() * maxSpeed);
        this.controller = controller;
        this.game = game;
        anim = new Animation(tex.enemy, 5);
    }

    @Override
    public void tick()
    {
        //Enemy Movement
        setY(getY() + this.speed);

        //Enemy out of bottom screen line
        if (getY() > (Game.HEIGHT * Game.SCALE))
        {
            setX(r.nextInt(Game.WIDTH * Game.SCALE - 32));
            setY(-35);
        }

        //Check for collision with bullet
        for (int i = 0; i < game.getEntitiesA().size(); i++)
        {
            EntityA tempEntA = game.getEntitiesA().get(i);
            if (Physics.collision(this, tempEntA))
            {
                controller.removeEntity(tempEntA);
                controller.removeEntity(this);
                controller.addEntity(new Explotion(game,
                        this.getX(), this.getY()));
                game.setCurrentPoints(game.getCurrentPoints() + 1);
                game.setEnemyKilled(game.getEnemyKilled() + 1);
            }
        }
        anim.runAnimation();
    }

    @Override
    public void render(Graphics2D g2)
    {
        anim.drawAnimation(g2, getX(), getY(), 0);
    }
}
