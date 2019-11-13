package com.cosmic2d.main.states;

import com.cosmic2d.main.Game;
import com.cosmic2d.main.classes.Music;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

public class MenuState
{
    private Game game;
    private Rectangle2D playButton;
    private Rectangle2D scoreBoardButton;
    private Rectangle2D helpButton;
    private Rectangle2D quitButton;

    public MenuState(Game game)
    {
        this.game = game;
        this.playButton =
                new Rectangle2D.Double(Game.WIDTH / 2.0 - 75, 150, 150, 50);
        this.scoreBoardButton = new Rectangle2D.Double(145, 250, 150, 50);
        this.helpButton = new Rectangle2D.Double(345, 250, 150, 50);
        this.quitButton =
                new Rectangle2D.Double(Game.WIDTH / 2.0 - 75, 350, 150, 50);
    }

    public void render(Graphics2D g2)
    {
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g2.setFont(fnt0);
        g2.setColor(Color.WHITE);
        FontRenderContext context = g2.getFontRenderContext();

        Rectangle2D labelBounds = fnt0.getStringBounds(Game.TITLE, context);
        g2.drawString(Game.TITLE,
                (int)(Game.WIDTH / 2 - labelBounds.getWidth() / 2), 100);

        Font fnt1 = new Font("arial", Font.BOLD, 20);
        this.drawButtonWithLabel(g2, fnt1, "Nowa Gra", playButton);
        this.drawButtonWithLabel(g2, fnt1, "Najlepsi", scoreBoardButton);
        this.drawButtonWithLabel(g2, fnt1, "Pomoc", helpButton);
        this.drawButtonWithLabel(g2, fnt1, "Koniec", quitButton);
    }

    public void mousePressed(MouseEvent e)
    {
        if (playButton.contains(e.getPoint()))
        {
            Music.MENU.stop();
            Music.GAMEPLAY.play();
            game.setState(STATE.GAME);
        }
        else if (scoreBoardButton.contains(e.getPoint()))
        {
            Music.MENU.stop();
            Music.HIGH_SCORES.play();
            game.setState(STATE.SCORE_BOARD);
        }
        else if (helpButton.contains(e.getPoint()))
            game.setState(STATE.HELP);
        else if (quitButton.contains(e.getPoint()))
            System.exit(0);
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ENTER)
        {
            Music.MENU.stop();
            Music.GAMEPLAY.play();
            game.setState(STATE.GAME);
        }
        else if (key == KeyEvent.VK_N)
        {
            Music.MENU.stop();
            Music.HIGH_SCORES.play();
            game.setState(STATE.SCORE_BOARD);

        }
        else if (key == KeyEvent.VK_P)
            game.setState(STATE.HELP);
        else if (key == KeyEvent.VK_ESCAPE)
            System.exit(0);
    }

    /**
     * Draws a rectangle button with user-specified label centered in the
     * middle of the button.
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
        g2.drawString(label,
                (int) (button.getX() + button.getWidth() / 2 -
                                    labelBounds.getWidth() / 2),
                (int) (button.getY() + yOffset + metrics.getAscent()));
        g2.draw(button);
    }
}
