package com.cosmic2d.main.states;

import com.cosmic2d.main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.util.StringTokenizer;

public class HelpState
{
    private Game game;
    private Rectangle2D returnButton;

    public HelpState(Game game)
    {
        this.game = game;
        this.returnButton =
                new Rectangle2D.Double(Game.WIDTH / 2.0 - 100, 380, 200 ,50);
    }

    public void render(Graphics2D g2)
    {
        Font fnt0 = new Font("arial", Font.BOLD, 22);
        g2.setFont(fnt0);
        g2.setColor(Color.WHITE);
        FontRenderContext context = g2.getFontRenderContext();

        String welcome = "Witamy w grze \"Kosmiczna Strzelanina 2D\"";
        Rectangle2D labelBounds = fnt0.getStringBounds(welcome, context);
        g2.drawString(welcome,
                (int)(Game.WIDTH / 2 - labelBounds.getWidth() / 2), 70);

        fnt0 = new Font("arial", Font.BOLD, 16);
        g2.setFont(fnt0);
        g2.setColor(Color.YELLOW);

        String msg =
                "Twoim zadaniem jest zdobycie jak największej ilości " +
                "punktów\n " +
                "poprzez likwidowanie przeciwników, którzy przybywają w " +
                "eskadrach.\n " +
                "Każdy szwadron wroga jest liczniejszy i szybszy od " +
                "poprzedniego.\n " +
                "Jeżeli nieprzyjacielska jednostka przedostanie się za linię " +
                "Twojej obrony,\n" +
                " wróg wysyła w ramach tej samej eskadry kolejny statek, " +
                "który jest wart\n " +
                "o jeden punkt mniej od przepuszczonego i różni się kolorem" +
                ".\n" +
                "Punktacja za zestrzelenie wrogich jednostek:\n" +
                "Czerwony: 3, Pomarańczowy: 2, Żółty: 1, Szary: 0.";
        int line = 0;
        StringTokenizer tokenizer = new StringTokenizer(msg, "\n");
        while (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            labelBounds = fnt0.getStringBounds(token, context);
            g2.drawString(token,
                    (int)(Game.WIDTH / 2 - labelBounds.getWidth() / 2),
                    125 + line);
            line += 30;
        }

        g2.setColor(Color.WHITE);
        Font fnt1 = new Font("arial", Font.BOLD, 20);
        this.drawButtonWithLabel(g2, fnt1, "Powrót do menu", returnButton);
    }

    public void mousePressed(MouseEvent e)
    {
        if (returnButton.contains(e.getPoint()))
            game.setState(STATE.MENU);
    }

    public void keyPressed(KeyEvent e)
    {
        game.setState(STATE.MENU);
    }

    /**
     * Draws a rectangle button (black background, white outlines and label)
     * with user-specified label centered in the middle of the button.
     * @param g2 current graphics renderer
     * @param font font of the label
     * @param label text of the label
     * @param button rectangle with button position and outlines
     */
    private void drawButtonWithLabel(Graphics2D g2, Font font,
            String label, Rectangle2D button)
    {
        g2.setColor(Color.BLACK);
        g2.fill(button);

        g2.setColor(Color.WHITE);
        g2.setFont(font);
        FontRenderContext context = g2.getFontRenderContext();

        Rectangle2D labelBounds = font.getStringBounds(label, context);
        LineMetrics metrics = font.getLineMetrics(label, context);
        double fontHeight = metrics.getAscent() + metrics.getDescent();

        double yOffset = (button.getHeight() - fontHeight) / 2;
        g2.drawString(label, (int) (button.getX() + button.getWidth() / 2 -
                                    labelBounds.getWidth() / 2),
                (int) (button.getY() + yOffset + metrics.getAscent()));
        g2.draw(button);
    }
}