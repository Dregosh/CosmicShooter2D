package com.game.src.main;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Controller
{
    private LinkedList<EntityA> entitiesA = new LinkedList<>();
    private LinkedList<EntityB> entitiesB = new LinkedList<>();

    private EntityA entityA;
    private EntityB entityB;

    private Textures tex;
    private Random r = new Random();
    private Game game;

    public Controller(Textures tex, Game game)
    {
        this.tex = tex;
        this.game = game;
    }

    public void createEnemy(int enemyCount)
    {
        for (int i = 0; i < enemyCount; i++)
        {
            addEntity(new Enemy(r.nextInt(Game.WIDTH * Game.SCALE - 32), -35,
                    this.tex, this, game));
        }
    }

    public void tick()
    {
        //EntityA (Friendly)
        for (int i = 0; i < entitiesA.size(); i++)
        {
            entityA = entitiesA.get(i);

            //Usuwamy z Listy te pociski, ktore wylecialy poza ekran u gory
            if (entityA.getClass().equals(Bullet.class) && entityA.getY() < -32)
                entitiesA.remove(entityA);

            entityA.tick();
        }

        //EntityB (Hostile)
        for (int i = 0; i < entitiesB.size(); i++)
        {
            entityB = entitiesB.get(i);

            entityB.tick();
        }
    }

    public void render(Graphics2D g2)
    {
        //EntityA
        for (int i = 0; i < entitiesA.size(); i++)
        {
            entityA = entitiesA.get(i);
            entityA.render(g2);
        }

        //EntityB
        for (int i = 0; i < entitiesB.size(); i++)
        {
            entityB = entitiesB.get(i);
            entityB.render(g2);
        }
    }

    //Przeladowane (overloaded) funkcje:
    public void addEntity(EntityA block)
    {
        entitiesA.add(block);
    }
    public void addEntity(EntityB block)
    {
        entitiesB.add(block);
    }

    public void removeEntity(EntityA block)
    {
        entitiesA.remove(block);
    }
    public void removeEntity(EntityB block)
    {
        entitiesB.remove(block);
    }

    public LinkedList<EntityA> getEntitiesA()
    {
        return entitiesA;
    }
    public LinkedList<EntityB> getEntitiesB()
    {
        return entitiesB;
    }
}