package com.cosmic2d.main;

import com.cosmic2d.main.states.STATE;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter
{
    private Game game;

    public KeyInput(Game game)
    {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        //Keys always active
        if (key == KeyEvent.VK_K)
            System.exit(0);

        //STATES keys
        else if (game.getState() == STATE.GAME)
            game.keyPressed(e);

        else if (game.getState() == STATE.MENU)
            game.getMenuState().keyPressed(e);

        else if (game.getState() == STATE.SCORE_BOARD)
            game.getScoreBoardState().keyPressed(e);

        else if (game.getState() == STATE.HELP)
            game.getHelpState().keyPressed(e);

        else if (game.getState() == STATE.GAME_OVER)
            game.getGameOverState().keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        game.keyReleased(e);
    }
}
