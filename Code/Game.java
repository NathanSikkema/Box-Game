package Code;

import Code.entities.BasicEnemy;
import Code.entities.MenuParticle;
import Code.entities.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    private static Random r;
    private static Handler handler;
    private final HUD hud;
    private final Spawn spawner;
    private final Menu menu;
    private static final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private Thread thread;

    public static boolean running = false;
    public static boolean loadParticles = true;
    public static STATE gameState = STATE.MainMenu;
    public static int WIDTH = (int) size.getWidth() - 10;
    public static int HEIGHT = (int) size.getHeight() - 60;


    public static void main(String[] args) {
        new Game();
    }


    public Game() {

        handler = new Handler();
        hud = new HUD(handler);
        r = new Random();
        menu = new Menu(handler);
        this.addMouseListener(menu);
        this.addKeyListener(new KeyInput(handler));
        new Window(WIDTH, HEIGHT, "GAME", this);
        spawner = new Spawn(handler, hud);

        if (gameState == STATE.RUNNING) {
            handler.addObject(new Player(WIDTH / 2 - 16, HEIGHT / 2 - 16, ID.Player, handler));
        }

    }

    public static double clamp(double var, int min, int max) {
        if (var >= max) return max;
        else if (var <= min) return min;
        return var;
    }


    public static void addEnemy() {
        new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;


    }

    public synchronized void stop() {
        try {
            thread.join();
            System.out.println(hud.getLevel());
            running = false;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void tick() {

        handler.tick();
        if (gameState == STATE.RUNNING) {

            spawner.tick();
            hud.tick();
        } else if (gameState == STATE.MainMenu) {
            menu.tick();

        }


//        if (HUD.HEALTH == 0) running = false;

    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 35.0;
        double nsPerTick = 1000000000.0 / amountOfTicks;
        double nsPerFrame = 1000000000.0 / 65.0; // Frame duration for 65 FPS
        double delta = 0;
        long timer = System.currentTimeMillis();
        int ticks = 0;
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            boolean shouldRender = false;

            while (delta >= 1) {
                tick(); // Limit tick processing to once per frame
                ticks++;
                delta--;
                shouldRender = true;
            }

            if (shouldRender) {
                render();
                frames++;
            }


            long timeTaken = System.nanoTime() - now;
            long sleepTime = (long) (nsPerFrame - timeTaken) / 1000000;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.print("\rTicks: " + ticks + ", Frames: " + frames);
                ticks = 0;
                if (frames <= 20) {
                    loadParticles = false;
                    System.out.println();
                } else if (frames >= 35) loadParticles = true;

                frames = 0;
            }
        }
        stop();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        handler.render(g);

        if (gameState == STATE.RUNNING) {
            hud.render(g);

        } else {

            menu.render(g);


        }

        g.dispose();
        bs.show();
    }


}
