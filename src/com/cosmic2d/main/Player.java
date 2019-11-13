package com.cosmic2d.main;

import com.cosmic2d.main.classes.Animation;
import com.cosmic2d.main.classes.EntityFriendly;
import com.cosmic2d.main.classes.EntityHostile;
import com.cosmic2d.main.classes.SoundFX;
import com.cosmic2d.main.states.STATE;

import java.awt.*;

public class Player extends GameObject implements EntityFriendly
{
    private Game game;
    private double velX = 0;
    private double velY = 0;
    private int health;
    private int maxAmmoAmount;
    private int currentAmmoAmount;
    private long ammoRegenTimer;
    Animation anim;

    public Player(Game game, double x, double y)
    {
        super(x, y);
        this.game = game;
        this.health = 100;
        this.maxAmmoAmount = 5;
        this.currentAmmoAmount = 5;
        anim = new Animation(game.getTex().player, 5);
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
        if (getX() <= 0)            setX(0);
        if (getX() >= 640 - 32)     setX(640 - 32);
        if (getY() <= 0)            setY(0);
        if (getY() >= 480 - 44)     setY(480 - 44);

        //Collision Check LOOP: Player with every existing Enemy
        for (int i = 0;
             i < game.getController().getEntitiesHostile().size(); i++)
        {
            EntityHostile entityHostile =
                    game.getController().getEntitiesHostile().get(i);

            if (Physics.collision(this, entityHostile))
            {
                SoundFX.EXPLOSION.play();
                game.getController().getEntitiesHostile().remove(
                        entityHostile);

                game.getController().addEntity(
                        new Explotion(game, entityHostile.getX(),
                                entityHostile.getY()));

                game.setEnemyKilled(game.getEnemyKilled() + 1);
                this.health -= 10;
            }
        }

        //In case of Player loosing the game
        if (this.health <= 0)
        {
            if (game.getCurrentPoints() >
                game.getScoreBoardState().getCurrentLowestScore())
            {
                game.getScoreBoardState().addNewHighScore();
                game.getGameOverState().setNewHighScore(true);
            }
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
            g2.drawImage(game.getTex().missile[2], (i * 15), 420, null);
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
