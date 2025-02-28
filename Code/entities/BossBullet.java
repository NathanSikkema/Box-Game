package Code.entities;

import Code.*;

import java.awt.*;


public class BossBullet extends EnemyObject {


    private final Handler handler;


    public BossBullet(double x, double y, ID id, Handler handler) {
        super(x, y, id, 10);

        this.handler = handler;


        velX = (r.nextInt(5 + 5) - 5);
        velY = 10;

    }


    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }

    public void render(Graphics g) {
        g.setColor(new Color(255, 40, 147));
        g.fillOval((int) x, (int) y, size, 2 * size);

    }

    public void tick() {

        x += velX;
        y += velY;

        if (y > Game.HEIGHT) handler.removeObject(this);
//        handler.addObject(new Trail(x,y,ID.Trail, Color.red, size, size, 0.02F, handler));


    }

    public void collisionWithPlayer() {
        HUD.HEALTH -= 5;
        handler.object.remove(this);

    }
}
