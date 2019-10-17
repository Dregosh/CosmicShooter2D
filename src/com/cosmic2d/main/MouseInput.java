package com.cosmic2d.main;

import com.cosmic2d.main.states.STATE;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter
{
    private Game game;

    public MouseInput(Game game)
    {
        this.game = game;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (game.getState() == STATE.MENU)
            game.getMenuState().mousePressed(e);

        else if (game.getState() == STATE.SCORE_BOARD ||
                 game.getState() == STATE.HELP)
            game.getHelpState().mousePressed(e);

        else if (game.getState() == STATE.GAME_OVER)
            game.getGameOverState().mousePressed(e);

    }
}
