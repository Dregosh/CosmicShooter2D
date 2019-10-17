package com.cosmic2d.main;

import java.awt.*;

public class GameLauncher
{
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            new GameWindow();
        });
    }
}
