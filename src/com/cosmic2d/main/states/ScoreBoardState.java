package com.cosmic2d.main.states;

import com.cosmic2d.main.Game;
import com.cosmic2d.main.classes.Music;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreBoardState
{
    private Game game;
    private Rectangle2D returnButton;
    private ArrayList<PlayerScore> scores;
    private int currentLowestScore;
    private File highScoresDataFile;

    public ScoreBoardState(Game game)
    {
        this.game = game;
        this.returnButton =
                new Rectangle2D.Double(Game.WIDTH / 2.0 - 100, 380, 200, 50);
        this.scores = new ArrayList<>();

        //HighScore Table initialization
        this.highScoresDataFile = new File("res\\highScores.dat");
        if (highScoresDataFile.exists())
        {
            //Import of HighScore data from existing highScore.dat file
            try (ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(highScoresDataFile)))
            {
                Object obj = in.readObject();
                ArrayList ar = (ArrayList) obj;
                for (Object x : ar)
                    this.scores.add((PlayerScore) x);
            }
            catch (ClassNotFoundException | IOException e)
            {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        else
        {
            //Default HighScores in case of missing highScores.dat file
            scores.add(new PlayerScore("DREG", 1, 9));
            scores.add(new PlayerScore("ROMEK", 2, 12));
            scores.add(new PlayerScore("ISA", 1, 1));
            scores.add(new PlayerScore("ROMEK", 3, 15));
            scores.add(new PlayerScore("DOMINO", 5, 21));

            //Creation of new highScores.dat file with default data
            exportHighScoreTableToFile(highScoresDataFile);
        }

        Collections.sort(scores, Comparator.comparingInt(
                PlayerScore::getKilledEnemies).reversed());
        currentLowestScore = scores.get(4).getKilledEnemies();
    }

    public void addNewHighScore()
    {
        scores.remove(4);
        scores.add(new PlayerScore(game.getPlayerName(),
                game.getCurrentLevel() - 1, game.getCurrentPoints()));
        Collections.sort(scores, Comparator.comparingInt(
                PlayerScore::getKilledEnemies).reversed());
        currentLowestScore = scores.get(4).getKilledEnemies();
        exportHighScoreTableToFile(highScoresDataFile);
    }

    public void exportHighScoreTableToFile(File destinationFile)
    {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(destinationFile)))
        {
            out.writeObject(this.scores);
        }
        catch (IOException e)
        {
            System.out.println("Błąd podczas próby zapisu do pliku z " +
                               "listą rekordów.");
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g2)
    {
        Font fnt0 = game.getFntUnispaceBold().deriveFont(30F);
        g2.setFont(fnt0);
        g2.setColor(Color.ORANGE);
        FontRenderContext context = g2.getFontRenderContext();

        String welcome = "NAJLEPSZE WYNIKI";
        Rectangle2D labelBounds = fnt0.getStringBounds(welcome, context);
        g2.drawString(welcome,
                (int) (Game.WIDTH / 2 - labelBounds.getWidth() / 2), 70);

        //Table Headers
        fnt0 = fnt0.deriveFont(25F);
        g2.setFont(fnt0);
        g2.setColor(Color.YELLOW);
        context = g2.getFontRenderContext();
        g2.drawString("MIEJSCE", 50, 135);
        g2.drawString("WYNIK", 220, 135);
        g2.drawString("ESKADRY", 370, 135);
        g2.drawString("GRACZ", 525, 135);

        Rectangle2D placeBounds = fnt0.getStringBounds("MIEJSCE", context);
        Rectangle2D squadronsBounds = fnt0.getStringBounds("ESKADRY", context);
        Rectangle2D currentTextBounds;

        for (int i = 0; i < scores.size(); i++)
        {
            switch (i)
            {
            case 0:
                g2.setColor(Color.RED);
                break;
            case 1:
                g2.setColor(Color.CYAN);
                break;
            case 2:
                g2.setColor(Color.GREEN);
                break;
            case 3:
            case 4:
                g2.setColor(Color.WHITE);
                break;
            }
            String msg = String.valueOf(i + 1);
            currentTextBounds = fnt0.getStringBounds(msg, context);
            g2.drawString(msg, (int) (50 + placeBounds.getWidth() / 2 -
                                      currentTextBounds.getWidth() / 2),
                    145 + ((i + 1) * 40));

            g2.drawString(String.valueOf(scores.get(i).getKilledEnemies()),
                    220, 145 + ((i + 1) * 40));

            msg = String.valueOf(scores.get(i).getKilledSquadrons());
            currentTextBounds = fnt0.getStringBounds(msg, context);
            g2.drawString(msg, (int) (370 + squadronsBounds.getWidth() / 2 -
                                      currentTextBounds.getWidth() / 2),
                    145 + ((i + 1) * 40));

            g2.drawString(scores.get(i).getName(), 525, 145 + ((i + 1) * 40));
        }

        g2.setColor(Color.WHITE);
        Font fnt1 = new Font("arial", Font.BOLD, 20);
        this.drawButtonWithLabel(g2, fnt1, "Powrót do menu", returnButton);
    }

    public void mousePressed(MouseEvent e)
    {
        if (returnButton.contains(e.getPoint()))
        {
            Music.HIGH_SCORES.stop();
            Music.MENU.play();
            game.setState(STATE.MENU);
        }
    }

    public void keyPressed(KeyEvent e)
    {
        Music.HIGH_SCORES.stop();
        Music.MENU.play();
        game.setState(STATE.MENU);
    }

    /**
     * Draws a rectangle button (black background, white outlines and label)
     * with user-specified label centered in the middle of the button.
     *
     * @param g2     current graphics renderer
     * @param font   font of the label
     * @param label  text of the label
     * @param button rectangle with button position and outlines
     */
    private void drawButtonWithLabel(Graphics2D g2, Font font, String label,
            Rectangle2D button)
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

    public int getCurrentLowestScore()
    {
        return currentLowestScore;
    }
}

//Simple class to hold Player HighScore record
// Excluded from ScoreBoardState class for serialization purposes.
class PlayerScore implements Serializable
{
    private String name;
    private int killedSquadrons;
    private int killedEnemies;

    public PlayerScore(String name, int killedSquadrons, int killedEnemies)
    {
        this.name = name;
        this.killedSquadrons = killedSquadrons;
        this.killedEnemies = killedEnemies;
    }

    public String getName()
    {
        return name;
    }

    public int getKilledSquadrons()
    {
        return killedSquadrons;
    }

    public int getKilledEnemies()
    {
        return killedEnemies;
    }
}