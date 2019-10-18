package com.cosmic2d.main.classes;

import java.awt.*;

/**
 * Interface for all NEUTRAL units/items (i.e. explotions, power-ups)
 */
public interface EntityNeutral
{
    void tick();
    void render(Graphics2D g2);
    Rectangle getBounds(int width, int height);

    double getX();
    double getY();

    long getCreationTime();
}
