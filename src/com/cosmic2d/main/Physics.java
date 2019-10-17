package com.cosmic2d.main;

import com.cosmic2d.main.classes.EntityA;
import com.cosmic2d.main.classes.EntityB;

public class Physics
{
    public static boolean collision(EntityA entA, EntityB entB)
    {
        return (entA.getBounds(32, 32).intersects(entB.getBounds(32, 32)));
    }

    public static boolean collision(EntityB entB, EntityA entA)
    {
        return (entB.getBounds(32, 32).intersects(entA.getBounds(32, 32)));
    }
}
