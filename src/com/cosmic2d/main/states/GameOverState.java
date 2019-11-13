package com.cosmic2d.main.states;

import com.cosmic2d.main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

public class GameOverState
{
    private Game game;
    private Rectangle2D returnButton;
    private boolean newHighScore;

    public GameOverState(Game game)
    {
        this.game = game;
        returnButton =
                new Rectangle2D.Double(Game.WIDTH / 2.0 - 100, 350, 200 ,50);
        newHighScore = false;
    }

    public void render(Graphics2D g2)
    {
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g2.setFont(fnt0);
        g2.setColor(Color.WHITE);
        FontRenderContext context = g2.getFontRenderContext();

        Rectangle2D labelBounds = fnt0.getStringBounds("Koniec gry", context);
        g2.drawString("Koniec gry",
                (int)(Game.WIDTH / 2 - labelBounds.getWidth() / 2), 100);

        fnt0 = new Font("arial", Font.BOLD, 20);
        g2.setFont(fnt0);
        g2.setColor(Color.YELLOW);

        StringBuilder tempMsg = new StringBuilder("Pokonane eskadry: ");
        tempMsg.append(game.getCurrentLevel() - 1);
        String msg = new String(tempMsg);
        labelBounds = fnt0.getStringBounds(msg, context);
        g2.drawString(msg,
                (int)(Game.WIDTH / 2 - labelBounds.getWidth() / 2), 210);

        tempMsg = new StringBuilder("Zdobyte punkty: ");
        tempMsg.append(game.getCurrentPoints());
        msg = new String(tempMsg);
        labelBounds = fnt0.getStringBounds(msg, context);
        g2.drawString(msg,
                (int)(Game.WIDTH / 2 - labelBounds.getWidth() / 2), 260);

        if (newHighScore == true)
        {
            g2.setColor(Color.RED);
            g2.drawString("GRATULACJE! Wynik wpisany do Tablicy Najlepszych!",
                    60, 310);
        }

        g2.setColor(Color.WHITE);
        Font fnt1 = new Font("arial", Font.BOLD, 20);
        this.drawButtonWithLabel(g2, fnt1, "Powrót do menu", returnButton);
    }

    public void mousePressed(MouseEvent e)
    {
        if (returnButton.contains(e.getPoint()))
        {
            newHighScore = false;
            game.setRestarted(true);
        }
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_ESCAPE)
        {
            newHighScore = false;
            game.setRestarted(true);
        }
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

    public boolean isNewHighScore()
    {
        return newHighScore;
    }

    public void setNewHighScore(boolean newHighScore)
    {
        this.newHighScore = newHighScore;
    }
}
