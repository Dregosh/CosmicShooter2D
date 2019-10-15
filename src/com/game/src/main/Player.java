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
    private int maxAmmoAmount;
    private int currentAmmoAmount;
    private long ammoRegenTimer;
    Animation anim;

    public Player(double x, double y, Textures tex, Game game)
    {
        super(x, y);
        this.tex = tex;
        this.game = game;
        this.health = 100;
        this.maxAmmoAmount = 5;
        this.currentAmmoAmount = 5;
        anim = new Animation(tex.player, 5);
    }

    @Override
    public void tick()
    {
        //Player ammo regen
        if (currentAmmoAmount < maxAmmoAmount)
        {
            if (ammoRegenTimer == 0)
                ammoRegenTimer = System.currentTimeMillis();
            else if (System.currentTimeMillis() >= ammoRegenTimer + 1000)
            {
                currentAmmoAmount++;
                ammoRegenTimer = 0;
            }
        }

        //Player movement
        setX(getX() + velX);
        setY(getY() + velY);

        //Player movement bounds
        if (getX() <= 0) setX(0);
        if (getX() >= 640 - 32) setX(640 - 32);
        if (getY() <= 0) setY(0);
        if (getY() >= 480 - 44) setY(480 - 44);

        //Player with Enemy collision check
        for (int i = 0; i < game.getEntitiesB().size(); i++)
        {
            EntityB tempEntB = game.getEntitiesB().get(i);

            if (tempEntB.getClass().equals(Enemy.class) &&
                Physics.collision(this, tempEntB))
            {
                game.getEntitiesB().remove(tempEntB);
                game.getEntitiesB().add(new Explotion(game,
                        tempEntB.getX(), tempEntB.getY()));
                game.setEnemyKilled(game.getEnemyKilled() + 1);
                this.health -= 10;
            }
            if (this.health <= 0)
                game.setState(STATE.GAME_OVER);
        }

        //Player Animation
        anim.runAnimation();
    }

    @Override
    public void render(Graphics2D g2)
    {
        //Ammo amount draw
        for (int i = 0; i < currentAmmoAmount; i++)
        {
            g2.drawImage(tex.missile[2], (i * 15), 420, null);
        }

        //Player animation draw
        anim.drawAnimation(g2, getX(), getY(), 0);
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
    public int getMaxAmmoAmount()
    {
        return maxAmmoAmount;
    }
    public void setMaxAmmoAmount(int maxAmmoAmount)
    {
        this.maxAmmoAmount = maxAmmoAmount;
    }
    public int getCurrentAmmoAmount()
    {
        return currentAmmoAmount;
    }
    public void setCurrentAmmoAmount(int currentAmmoAmount)
    {
        this.currentAmmoAmount = currentAmmoAmount;
    }
}
