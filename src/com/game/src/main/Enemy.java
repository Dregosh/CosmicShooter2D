package com.game.src.main;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;
import com.game.src.main.libs.Animation;

import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject implements EntityB
{
    private Game game;
    private Textures tex;
    Random r = new Random();
    private double maxSpeed;
    private double speed;
    private int pointValue;
    private Animation anim;

    public Enemy(Game game, double x, double y)
    {
        super(x, y);
        this.game = game;
        this.tex = game.getTex();
        this.maxSpeed = 1.0 * (game.getCurrentLevel() * 0.2);
        this.speed = 1.0 + (r.nextDouble() * maxSpeed);
        this.pointValue = 3;
        anim = new Animation(tex.enemy3p, 5);
    }

    @Override
    public void tick()
    {
        //Enemy Movement
        setY(getY() + this.speed);

        //Enemy out of bottom screen line
        if (getY() > (Game.HEIGHT * Game.SCALE))
        {
            if (pointValue > 0)
                this.pointValue--;
            switch (pointValue)
            {
            case 2:
                anim = new Animation(tex.enemy2p, 5);
                break;
            case 1:
                anim = new Animation(tex.enemy1p, 5);
                break;
            case 0:
                anim = new Animation(tex.enemy0p, 5);
                break;
            }
            setX(r.nextInt(Game.WIDTH * Game.SCALE - 32));
            setY(-35);
        }

        //Check for collision with bullet
        for (int i = 0; i < game.getEntitiesA().size(); i++)
        {
            EntityA tempEntA = game.getEntitiesA().get(i);
            if (Physics.collision(this, tempEntA))
            {
                game.getController().removeEntity(tempEntA);
                game.getController().removeEntity(this);
                game.getController().addEntity(new Explotion(game,
                        this.getX(), this.getY()));
                game.setCurrentPoints(game.getCurrentPoints() + this.pointValue);
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
