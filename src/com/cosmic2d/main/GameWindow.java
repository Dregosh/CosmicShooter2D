package com.cosmic2d.main;

import javax.swing.*;

public class GameWindow extends JFrame
{
    public GameWindow()
    {
        Game game = new Game();
        game.setFocusable(true);
        game.requestFocusInWindow();

        this.setTitle(Game.TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(game);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
