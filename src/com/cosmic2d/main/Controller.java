package com.cosmic2d.main;

import com.cosmic2d.main.classes.EntityFriendly;
import com.cosmic2d.main.classes.EntityHostile;
import com.cosmic2d.main.classes.EntityNeutral;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Controller
{
    private Game game;
    private LinkedList<EntityFriendly> entitiesFriendly = new LinkedList<>();
    private LinkedList<EntityHostile> entitiesHostile = new LinkedList<>();
    private LinkedList<EntityNeutral> entitiesNeutral = new LinkedList<>();
    private EntityFriendly entityFriendly;
    private EntityHostile entityHostile;
    private EntityNeutral entityNeutral;
    private Random r = new Random();

    public Controller(Game game)
    {
        this.game = game;
    }

    public void createEnemy(int enemyCount)
    {
        for (int i = 0; i < enemyCount; i++)
        {
            addEntity(new Enemy(game, r.nextInt(Game.WIDTH - 32), -32));
        }
    }

    public void tick()
    {
        //All FRIENDLY Entities update
        for (int i = 0; i < entitiesFriendly.size(); i++)
        {
            entityFriendly = entitiesFriendly.get(i);

            //Removal of friendly bullets which are beyond upper screen border
            if (entityFriendly.getY() < -35)
                    entitiesFriendly.remove(entityFriendly);

            entityFriendly.tick();
        }

        //All HOSTILE Entities update
        for (int i = 0; i < entitiesHostile.size(); i++)
        {
            entityHostile = entitiesHostile.get(i);
            entityHostile.tick();
        }

        //All NEUTRAL Entities update
        for (int i = 0; i < entitiesNeutral.size(); i++)
        {
            entityNeutral = entitiesNeutral.get(i);
            if (System.currentTimeMillis() >
                entityNeutral.getCreationTime() + 200)
                    entitiesNeutral.remove(entityNeutral);

            entityNeutral.tick();
        }
    }

    public void render(Graphics2D g2)
    {
        //All FRIENDLY Entities rendering
        for (int i = 0; i < entitiesFriendly.size(); i++)
            entitiesFriendly.get(i).render(g2);

        //All HOSTILE Entities rendering
        for (int i = 0; i < entitiesHostile.size(); i++)
            entitiesHostile.get(i).render(g2);

        //All NEUTRAL Entities rendering
        for (int i = 0; i < entitiesNeutral.size(); i++)
            entitiesNeutral.get(i).render(g2);
    }

    //Przeladowane (overloaded) funkcje:
    public void addEntity(EntityFriendly item)
    {
        entitiesFriendly.add(item);
    }
    public void addEntity(EntityHostile item)
    {
        entitiesHostile.add(item);
    }
    public void addEntity(EntityNeutral item) { entitiesNeutral.add(item); }

    public void removeEntity(EntityFriendly item)
    {
        entitiesFriendly.remove(item);
    }
    public void removeEntity(EntityHostile item)
    {
        entitiesHostile.remove(item);
    }
    public void removeEntity(EntityNeutral item)
    {
        entitiesNeutral.remove(item);
    }

    public LinkedList<EntityFriendly> getEntitiesFriendly()
    {
        return entitiesFriendly;
    }
    public LinkedList<EntityHostile> getEntitiesHostile()
    {
        return entitiesHostile;
    }
    public LinkedList<EntityNeutral> getEntitiesNeutral()
    {
        return entitiesNeutral;
    }
}