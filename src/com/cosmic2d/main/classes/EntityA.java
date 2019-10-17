package com.cosmic2d.main.classes;

import java.awt.*;

/**
 * Interface for all FRIENDLY units/items in the game
 */
public interface EntityA
{
    void tick();
    void render(Graphics2D g2);
    Rectangle getBounds(int width, int height);

    double getX();
    double getY();
}
