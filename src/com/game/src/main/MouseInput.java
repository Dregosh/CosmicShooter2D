package com.game.src.main;

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

        else if (game.getState() == STATE.GAME_OVER)
            game.getGameOverState().mousePressed(e);
    }
}
