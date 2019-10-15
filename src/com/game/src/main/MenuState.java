package com.game.src.main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class MenuState
{
    private Game game;

    public Rectangle playButton = new Rectangle(Game.WIDTH - 75, 150,
            150 ,50);
    public Rectangle helpButton = new Rectangle(Game.WIDTH - 75, 250,
            150 ,50);
    public Rectangle quitButton = new Rectangle(Game.WIDTH - 75, 350,
            150 ,50);

    public MenuState(Game game)
    {
        this.game = game;
    }

    public void render(Graphics2D g2)
    {
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g2.setFont(fnt0);
        g2.setColor(Color.WHITE);
        FontRenderContext context = g2.getFontRenderContext();

        Rectangle2D labelBounds = fnt0.getStringBounds(Game.TITLE, context);
        g2.drawString(Game.TITLE,
                (int)(((Game.WIDTH * Game.SCALE) / 2)
                      - labelBounds.getWidth() / 2), 100);

        Font fnt1 = new Font("arial", Font.BOLD, 20);
        g2.setFont(fnt1);
        context = g2.getFontRenderContext();

        String buttonLabel = "Nowa Gra";
        labelBounds = fnt1.getStringBounds(buttonLabel,
                context);
        g2.drawString(buttonLabel, (int) (playButton.x +
                (playButton.width / 2 - labelBounds.getWidth() / 2)),
                (playButton.y + 32));
        g2.draw(playButton);

        buttonLabel = "Pomoc";
        labelBounds = fnt1.getStringBounds(buttonLabel,
                context);
        g2.drawString(buttonLabel, (int) (helpButton.x +
                (helpButton.width / 2 - labelBounds.getWidth() / 2)),
                (helpButton.y + 32));
        g2.draw(helpButton);

        buttonLabel = "Koniec";
        labelBounds = fnt1.getStringBounds(buttonLabel,
                context);
        g2.drawString(buttonLabel,
                (int) (quitButton.x +
                       (quitButton.width / 2 - labelBounds.getWidth() / 2)),
                (quitButton.y + 32));
        g2.draw(quitButton);
    }

    public void mousePressed(MouseEvent e)
    {
        if (playButton.contains(e.getPoint()))
            game.setState(STATE.GAME);
        else if (quitButton.contains(e.getPoint()))
            System.exit(0);
    }
}
