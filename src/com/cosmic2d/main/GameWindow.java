package com.cosmic2d.main;

import javax.swing.*;

public class GameWindow extends JFrame
{
    public GameWindow()
    {
        Game game = new Game();
        game.setFocusable(true);
        game.requestFocusInWindow();

        //Player Name input (for High Score Board purposes)
        String playerName = JOptionPane.showInputDialog(this,
                "Podaj pseudonim (max 6 znaków): ", "Pseudonim gracza",
                JOptionPane.PLAIN_MESSAGE);
        if (playerName == null || playerName.equals(""))
            playerName = "GRACZ";
        else if (playerName.length() > 6)
            playerName = playerName.substring(0, 6);
        game.setPlayerName(playerName.toUpperCase());

        this.setTitle(Game.TITLE + " (Pilot: " + game.getPlayerName() + ")");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(game);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
