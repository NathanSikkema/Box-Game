package Code.entities;

import Code.GameObject;
import Code.Handler;
import Code.ID;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Health extends GameObject {

    private static final Random random = new Random();
    private static final long LIFETIME = random.nextInt(5000, 10000);
    private final int healthBoost;
    private final long creationTime;
    private final Handler handler;
    private int xExtra = 8;
    private int yExtra = 18;

    public Health(double x, double y, ID id, int healthBoost, Handler handler) {
        super(x, y, id, 30);

        this.healthBoost = healthBoost;
        this.handler = handler;
        this.creationTime = System.currentTimeMillis();
    }

    @Override
    public void tick() {
        if (System.currentTimeMillis() - creationTime > LIFETIME) {
            handler.removeObject(this);
        }

    }

    @Override
    public void render(Graphics g) {
        Image image = new ImageIcon("src/Assets/Heart.png").getImage();
        if (healthBoost > 9) {
            this.setSize(35);
            xExtra = 7;
            yExtra = 20;

        }
        g.drawImage(image, (int) x, (int) y, size, size, null);
        g.setColor(Color.yellow);

        g.drawString("+" + healthBoost, (int) x + xExtra, (int) y + yExtra);

    }

    public int getHealthBoost() {
        return healthBoost;
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }
}
