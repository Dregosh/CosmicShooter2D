package com.game.src.main;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

public class Game extends Canvas implements Runnable
{
    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 2;
    public static final String TITLE = "Kosmiczna Strzelanina 2D";

    private boolean running = false;
    private Thread thread;
    Graphics2D g2;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
        BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;
    private BufferedImage background = null;

    private Textures tex;
    private Player player;
    private Controller controller;

    private int enemyCount;
    private int enemyKilled;
    private int additionalEnemiesOnNextLvl;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean isShooting;
    private boolean isRestarted;

    private LinkedList<EntityA> entitiesA;
    private LinkedList<EntityB> entitiesB;

    private STATE state;
    private MenuState menuState;
    private GameOverState gameOverState;

    public void init()
    {
        BufferedImageLoader loader = new BufferedImageLoader();
        try
        {
            spriteSheet = loader.loadImage("/sprite_sheet.png");
            background = loader.loadImage("/space_background.png");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        tex = new Textures(this);

        this.addKeyListener(new KeyInput(this));
        this.addMouseListener(new MouseInput(this));
        this.requestFocus();

        this.restart();
    }

    public void restart()
    {
        enemyCount = 5;
        enemyKilled = 0;
        additionalEnemiesOnNextLvl = 2;

        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        isShooting = false;
        isRestarted = false;

        player = new Player((WIDTH * SCALE / 2) - 16, (HEIGHT * SCALE) - 40,
                tex, this);
        controller = new Controller(tex, this);

        entitiesA = controller.getEntitiesA();
        entitiesB = controller.getEntitiesB();

        state = STATE.GAME; //Debug Mode: STATE.GAME, Retail: STATE.MENU
        menuState = new MenuState(this);
        gameOverState = new GameOverState(this);

        controller.createEnemy(enemyCount);
    }

    private synchronized void start()
    {
        if (running) return;

        running = true;
        thread = new Thread(this);
        thread.start();     //to uruchamia automatycznie metodę run()
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

    //Ta metoda wykona sie AUTOMATYCZNIE gdy wlaczymy Thread.
    // Tutaj bedzie wiec glowna petla gry.
    @Override
    public void run()
    {
        init();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1_000_000_000 / amountOfTicks;

        //DELTA..
        //(now - lastTime) gives the elapsed time since the run method was
        // entered. Then, we can think of the division of amountOfTicks as
        // being two parts. We first divide the elapsed time by 1000000000,
        // since System.nanoTime () gives the time in nanoseconds, meaning:
        //nanoseconds / 1000000000 = seconds.
        //So basically we have the elapsed time in seconds. We then multiply
        // this by a number, in this case 60. So we still have the elapsed
        // time in seconds, just multiplied by 60. Thus, when delta is
        // greater or equal to 1, 1/60 seconds have passed, Because again, 1
        // second is actually given the value 60 by the multiplication we did.
        // And after 1/60 second we want to call the tick() method. If we
        // weren't to multiply by 60, then delta would be equal to 1 after 1
        // second.
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns; //See the explanation up
            lastTime = now;
            if (delta >= 1)
            {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            //FPS Counter..
            //Wyswietlamy w Konsoli ilosc Ticks i FPS , ale aby nie spamowac
            // jej przy kazdym wykonaniu petli, to ustawiamy wyswietlanie
            // tylko gdy minie 1 sekunda czasu rzeczywistego (a wiec rowniez
            // wtedy, gdy wykona sie +-60 razy tick(). )
            /*if (System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                System.out.println(updates + " Ticks, FPS " + frames);
                updates = 0;
                frames = 0;
            }*/
            if (isRestarted)
            {
                this.restart();
            }
        }
        stop();
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
                controller.createEnemy(enemyCount);
            }
        }
    }

    private void render()
    {
        //wyciagamy BS z nadklasy Canvas.
        BufferStrategy bs = this.getBufferStrategy();
        //bs zainicjalizowane powyzej po raz pierwszy bedzie mialo wartosc null
        //dlatego ponizej tworzymy if, ktory zadziala tylko RAZ, na poczatku:
        if (bs == null)
        {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        //////Render here
        g2 = (Graphics2D) g;

        g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g2.drawImage(background, 0, 0, this);

        if (state == STATE.GAME)
        {
            g2.setColor(Color.WHITE);
            g2.drawString("EntitiesA: " + controller.getEntitiesA().size(),
                    10, 100);
            g2.drawString("EntitiesB: " + controller.getEntitiesB().size(),
                    10, 120);

            player.render(g2);
            controller.render(g2);

            g2.setColor(Color.GRAY);
            g2.fillRect(10, 10, 100, 20);
            g2.setColor(Color.GREEN);
            g2.fillRect(10, 10, player.getHealth(), 20);
            g2.setColor(Color.WHITE);
            g2.drawRect(10, 10, player.getHealth(), 20);
        }

        else if (state == STATE.MENU)
            menuState.render(g2);

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
                    (int)(((Game.WIDTH * Game.SCALE) / 2)
                          - labelBounds.getWidth() / 2), 230);

            fnt0 = new Font("arial", Font.PLAIN, 20);
            g2.setFont(fnt0);
            g2.drawString("Wciśnij dowolny klawisz by kontynuować", 150, 270);
        }

        //////Render ends
        g2.dispose();
        bs.show();
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();

        //Keys always active
        if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_K)
            System.exit(0);

        //MENU_STATE keys
        else if (state == STATE.MENU)
        {
            if (key == KeyEvent.VK_ENTER)
                this.state = STATE.GAME;
        }

        //GAME_STATE keys
        else if (state == STATE.GAME)
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
                    player.setCurrentAmmoAmount(player.getCurrentAmmoAmount() - 1);
                    controller.addEntity(new Bullet(player.getX(),
                            player.getY() + 10, tex, this));
                }
            }
            else if (key == KeyEvent.VK_P)
            {
                this.state = STATE.PAUSE;
            }
        }

        //PAUSE_STATE keys
        else if (this.state == STATE.PAUSE)
        {
            this.state = STATE.GAME;
        }

        //GAME_OVER_STATE keys
        else if (this.state == STATE.GAME_OVER)
        {
            if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE)
                this.isRestarted = true;
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

    public static void main(String[] args)
    {
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        JFrame frame = new JFrame(Game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }

    public BufferedImage getSpriteSheet()
    {
        return spriteSheet;
    }

    public Textures getTex()
    {
        return tex;
    }

    public Player getPlayer()
    {
        return player;
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
    public void setRestarted(boolean restarted)
    {
        isRestarted = restarted;
    }
    public LinkedList<EntityA> getEntitiesA()
    {
        return entitiesA;
    }
    public LinkedList<EntityB> getEntitiesB()
    {
        return entitiesB;
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
    public GameOverState getGameOverState()
    {
        return gameOverState;
    }
}