package com.game.src.main.classes;

import java.awt.*;

public interface EntityA
{
    void tick();
    void render(Graphics2D g2);
    Rectangle getBounds(int width, int height);

    double getX();
    double getY();
}
