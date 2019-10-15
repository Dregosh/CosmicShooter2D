package com.game.src.main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class GameOverState
{
    private Game game;

    public Rectangle returnButton = new Rectangle(Game.WIDTH - 100, 350,
            200 ,50);

    public GameOverState(Game game)
    {
        this.game = game;
    }

    public void render(Graphics2D g2)
    {
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g2.setFont(fnt0);
        g2.setColor(Color.WHITE);
        FontRenderContext context = g2.getFontRenderContext();

        Rectangle2D labelBounds = fnt0.getStringBounds("Koniec gry", context);
        g2.drawString("Koniec gry",
                (int)(((Game.WIDTH * Game.SCALE) / 2)
                      - labelBounds.getWidth() / 2), 100);

        Font fnt1 = new Font("arial", Font.BOLD, 20);
        g2.setFont(fnt1);
        context = g2.getFontRenderContext();

        String buttonLabel = "Powrót do menu";
        labelBounds = fnt1.getStringBounds(buttonLabel,
                context);

        g2.setColor(Color.BLACK);
        g2.fill(returnButton);

        g2.setColor(Color.WHITE);
        g2.draw(returnButton);
        g2.drawString(buttonLabel,
                (int) (returnButton.x +
                       (returnButton.width / 2 - labelBounds.getWidth() / 2)),
                (returnButton.y + 32));
    }

    public void mousePressed(MouseEvent e)
    {
        if (returnButton.contains(e.getPoint()))
            game.setRestarted(true);
    }
}
