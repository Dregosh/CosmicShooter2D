package com.cosmic2d.main;

import com.cosmic2d.main.classes.BufferedImageLoader;
import com.cosmic2d.main.classes.Music;
import com.cosmic2d.main.classes.SoundFX;
import com.cosmic2d.main.states.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JPanel
{
    public static final String TITLE = "Kosmiczna Strzelanina 2D";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    private boolean running = false;
    private Thread thread;
    private Graphics2D g2;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
        BufferedImage.TYPE_INT_RGB);

    private BufferedImage spriteSheet = null;
    private BufferedImage background = null;
    private Font fntUnispaceBold = null;

    private Textures tex;
    private Player player;
    private Controller controller;

    private String playerName;
    private int enemyCount;
    private int enemyKilled;
    private int additionalEnemiesOnNextLvl;
    private int currentPoints;
    private int currentLevel;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean isShooting;
    private boolean isRestarted;

    private STATE state;
    private MenuState menuState;
    private ScoreBoardState scoreBoardState;
    private HelpState helpState;
    private GameOverState gameOverState;

    private int fps = 60;
    private int frameCount = 0;

    @Override
    public void addNotify()
    {
        super.addNotify();
        if (running) return;

        running = true;
        thread = new Thread(() -> gameLoop());
        thread.start();     //gameLoop() starts here
    }

    public void init()
    {
        BufferedImageLoader loader = new BufferedImageLoader();
        try
        {
            spriteSheet = loader.loadImage("res/sprite_sheet.png");
            background = loader.loadImage("res/space_background.png");

            InputStream is = getClass()
                    .getResourceAsStream("res/unispace_bd.ttf");
            fntUnispaceBold = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
        }
        catch (IOException | FontFormatException e)
        {
            e.printStackTrace();
        }

        fntUnispaceBold = fntUnispaceBold.deriveFont(16F);

        tex = new Textures(this);

        menuState = new MenuState(this);
        scoreBoardState = new ScoreBoardState(this);
        helpState = new HelpState(this);
        gameOverState = new GameOverState(this);

        this.addKeyListener(new KeyInput(this));
        this.addMouseListener(new MouseInput(this));

        this.restart();
    }

    private void restart()
    {
        Music.MENU.play();

        enemyCount = 5;
        enemyKilled = 0;
        additionalEnemiesOnNextLvl = 2;
        currentPoints = 0;
        currentLevel = 1;

        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        isShooting = false;
        isRestarted = false;

        player = new Player(this, WIDTH / 2.0 - 16, HEIGHT - 40);
        controller = new Controller(this);

        state = STATE.MENU; //Debug Mode: STATE.GAME, Retail: STATE.MENU

        controller.createEnemy(enemyCount);
    }

    //Game Loop
    public void gameLoop()
    {
        init();

        final double GAME_HERTZ = 60.0;
        final double TIME_BETWEEN_UPDATES = 1_000_000_000 / GAME_HERTZ;

        final int MAX_UPDATES_BEFORE_RENDER = 5;
        double lastUpdateTime = System.nanoTime();
        double lastRenderTime = System.nanoTime();

        final double TARGET_FPS = 60;
        final double TARGET_TIME_BETWEEN_RENDERS = 1_000_000_000 / TARGET_FPS;

        int lastSecondTime = (int) (lastUpdateTime / 1_000_000_000);

        while(running)
        {
            double now = System.nanoTime();
            int updateCount = 0;

            while (now - lastUpdateTime > TIME_BETWEEN_UPDATES &&
                   updateCount < MAX_UPDATES_BEFORE_RENDER)
            {
                tick();
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
            }

            if (now - lastUpdateTime > TIME_BETWEEN_UPDATES)
                lastUpdateTime = now - TIME_BETWEEN_UPDATES;

            repaint();
            lastRenderTime = now;

            //FPS counter
            int thisSecond = (int) (lastUpdateTime / 1_000_000_000);
            if (thisSecond > lastSecondTime)
            {
                //System.out.println(
                //        "NEW SECOND " + thisSecond + " " + frameCount);
                fps = frameCount;
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS &&
                   now - lastUpdateTime < TIME_BETWEEN_UPDATES)
            {
                Thread.yield();
                try { Thread.sleep(1); }
                catch (Exception e) { e.printStackTrace(); }

                now = System.nanoTime();
            }

            if (isRestarted)
            {
                Music.stopAllMusic();
                this.restart();
            }
        }
        stop();
    }

    private synchronized void stop()
    {
        if (!running) return;

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    private void tick()
    {
        if (state == STATE.GAME)
        {
            player.tick();
            controller.tick();

            if (enemyKilled >= enemyCount)
            {
                enemyCount += additionalEnemiesOnNextLvl;
                enemyKilled = 0;
                SoundFX.NEW_SQUADRON.play();
                currentLevel++;
                controller.createEnemy(enemyCount);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g2.drawImage(background, 0, 0, this);

        if (state == STATE.GAME)
        {
            //Level / Points UI display
            g2.setColor(Color.WHITE);
            g2.setFont(fntUnispaceBold);
            g2.drawString("Eskadra: " + currentLevel, 500, 430);
            g2.drawString("Punkty: " + currentPoints, 500, 455);

            //Player space ship display
            player.render(g2);

            //Other Objects (enemies, missiles etc) display
            controller.render(g2);

            //Player health bar display
            g2.setColor(Color.GRAY);
            g2.fillRect(10, 10, 100, 20);
            if (player.getHealth() > 80)
                g2.setColor(Color.GREEN);
            else if (player.getHealth() > 50)
                g2.setColor(Color.YELLOW);
            else if (player.getHealth() >= 30)
                g2.setColor(Color.ORANGE);
            else
                g2.setColor(Color.RED);
            g2.fillRect(10, 10, player.getHealth(), 20);
            g2.setColor(Color.WHITE);
            g2.drawRect(10, 10, player.getHealth(), 20);
        }

        else if (state == STATE.MENU)
            menuState.render(g2);

        else if (state == STATE.SCORE_BOARD)
            scoreBoardState.render(g2);

        else if (state == STATE.HELP)
            helpState.render(g2);

        else if (state == STATE.GAME_OVER)
            gameOverState.render(g2);

        else if (state == STATE.PAUSE)
        {
            Font fnt0 = new Font("arial", Font.BOLD, 30);
            g2.setFont(fnt0);
            g2.setColor(Color.WHITE);
            FontRenderContext context = g2.getFontRenderContext();
            Rectangle2D labelBounds = fnt0.getStringBounds(
                    "PAUZA", context);
            g2.drawString("PAUZA",
                    (int)(Game.WIDTH / 2 - labelBounds.getWidth() / 2), 230);
            fnt0 = new Font("arial", Font.PLAIN, 20);
            g2.setFont(fnt0);
            g2.drawString("Wciśnij dowolny klawisz by kontynuować", 150, 270);
        }

        //g.drawString("FPS: " + fps, 10, 50);
        frameCount++;
        g2.dispose();
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        //GAME_STATE keys
        if (state == STATE.GAME)
        {
            if (key == KeyEvent.VK_RIGHT)
            {
                rightPressed = true;
                player.setVelX(5);
            }
            else if (key == KeyEvent.VK_LEFT)
            {
                leftPressed = true;
                player.setVelX(-5);
            }
            else if (key == KeyEvent.VK_DOWN)
            {
                downPressed = true;
                player.setVelY(5);
            }
            else if (key == KeyEvent.VK_UP)
            {
                upPressed = true;
                player.setVelY(-5);
            }
            else if (key == KeyEvent.VK_SPACE && !isShooting)
            {
                if (player.getCurrentAmmoAmount() > 0)
                {
                    isShooting = true;
                    SoundFX.MISSILE_SHOT.play();
                    player.setCurrentAmmoAmount(
                            player.getCurrentAmmoAmount() - 1);
                    controller.addEntity(new Bullet(player.getX(),
                            player.getY() + 10, tex, this));
                }
            }
            else if (key == KeyEvent.VK_P)
            {
                this.state = STATE.PAUSE;
            }
            else if (key == KeyEvent.VK_ESCAPE)
            {
                this.isRestarted = true;
            }
        }

        //PAUSE_STATE keys
        else if (this.state == STATE.PAUSE)
        {
            this.state = STATE.GAME;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if (this.state == STATE.GAME)
        {
            if (key == KeyEvent.VK_RIGHT)
            {
                rightPressed = false;
                if (leftPressed)
                { player.setVelX(-5); }
                else
                { player.setVelX(0); }
            }
            else if (key == KeyEvent.VK_LEFT)
            {
                leftPressed = false;
                if (rightPressed)
                { player.setVelX(5); }
                else
                { player.setVelX(0); }
            }
            else if (key == KeyEvent.VK_DOWN)
            {
                downPressed = false;
                if (upPressed)
                { player.setVelY(-5); }
                else
                { player.setVelY(0); }
            }
            else if (key == KeyEvent.VK_UP)
            {
                upPressed = false;
                if (downPressed)
                { player.setVelX(5); }
                else
                { player.setVelY(0); }
            }
            else if (key == KeyEvent.VK_SPACE)
            {
                isShooting = false;
            }
        }
    }

    public BufferedImage getSpriteSheet()
    {
        return spriteSheet;
    }

    public Font getFntUnispaceBold()
    {
        return fntUnispaceBold;
    }

    public Textures getTex()
    {
        return tex;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Controller getController()
    {
        return controller;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public int getEnemyCount()
    {
        return enemyCount;
    }
    public void setEnemyCount(int enemyCount)
    {
        this.enemyCount = enemyCount;
    }

    public int getEnemyKilled()
    {
        return enemyKilled;
    }

    public void setEnemyKilled(int enemyKilled)
    {
        this.enemyKilled = enemyKilled;
    }

    public int getCurrentPoints()
    {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints)
    {
        this.currentPoints = currentPoints;
    }

    public int getCurrentLevel()
    {
        return currentLevel;
    }
    public void setRestarted(boolean restarted)
    {
        isRestarted = restarted;
    }

    public STATE getState()
    {
        return state;
    }

    public void setState(STATE state)
    {
        this.state = state;
    }

    public MenuState getMenuState()
    {
        return menuState;
    }

    public ScoreBoardState getScoreBoardState()
    {
        return scoreBoardState;
    }

    public HelpState getHelpState()
    {
        return helpState;
    }

    public GameOverState getGameOverState()
    {
        return gameOverState;
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(WIDTH, HEIGHT);
    }
}