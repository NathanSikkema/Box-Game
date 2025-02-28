package Code.entities;

import Code.GameObject;
import Code.Handler;
import Code.ID;

import java.awt.*;

public class Coin extends GameObject {
    private final Handler handler;
    private final long creationTime;
    private static final long LIFETIME = 10000;

    public Coin(double x, double y, ID id, Handler handler) {
        super(x, y, id, 10);
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
        g.setColor(Color.yellow);
        g.drawOval((int) x, (int) y, size, size);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }
}
