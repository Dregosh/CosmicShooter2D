package com.cosmic2d.main.classes;

import java.awt.*;

/**
 * Interface for all HOSTILE units/items in the game
 */
public interface EntityHostile
{
    void tick();
    void render(Graphics2D g2);
    Rectangle getBounds(int width, int height);

    double getX();
    double getY();
}
