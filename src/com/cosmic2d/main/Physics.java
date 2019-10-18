package com.cosmic2d.main;

import com.cosmic2d.main.classes.EntityFriendly;
import com.cosmic2d.main.classes.EntityHostile;

public class Physics
{
    public static boolean collision(EntityFriendly entA, EntityHostile entB)
    {
        return (entA.getBounds(32, 32).intersects(entB.getBounds(32, 32)));
    }

    public static boolean collision(EntityHostile entB, EntityFriendly entA)
    {
        return (entB.getBounds(32, 32).intersects(entA.getBounds(32, 32)));
    }
}
