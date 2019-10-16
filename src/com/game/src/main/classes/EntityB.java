package com.game.src.main.classes;

import java.awt.*;

/**
 * Interface for all HOSTILE units/items in the game
 */
public interface EntityB
{
    void tick();
    void render(Graphics2D g2);
    Rectangle getBounds(int width, int height);

    double getX();
    double getY();
}
