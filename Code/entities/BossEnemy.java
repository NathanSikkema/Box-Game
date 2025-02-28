package Code.entities;

import Code.*;

import java.awt.*;


public class BossEnemy extends EnemyObject {


    private final Handler handler;
    private boolean colliding = false;


    private int timer = 75;
    private int timer2 = 50;
    private int departTimer = r.nextInt(200) + 200;
    private boolean departing = false;

    public BossEnemy(double x, double y, ID id, Handler handler) {
        super(x, y, id, 100);

        this.handler = handler;

        velX = 0;


        velY = 2;

    }


    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) x, (int) y, size, size);
        g.setColor(Color.black);
        int eyeSize = 20;

        g.fillRect((int) (x + 96 / 3) - 10, (int) (y + 20), eyeSize, eyeSize);
        g.fillRect((int) (x + 96 / 3 * 2) - 10, (int) (y + 20), eyeSize, eyeSize);

        int arcWidth = 80;
        int arcHeight = 40;
        int arcX = (int) x + (size - arcWidth) / 2;
        int arcY = (int) y + size - 40;

        g.fillArc(arcX, arcY, arcWidth, arcHeight, 0, 180);
    }

    public void tick() {

        x += velX;
        y += velY;


        if (timer <= 0) {
            velY = 0;
            timer2--;
        } else timer--;

        if (timer2 <= 0 && !departing) {
            departTimer--;
            int xD = r.nextInt(10);
            if (velX == 0) {
                if (xD >= 5) velX = -4;
                else velX = 4;
            }
            int spawn = r.nextInt(20);
            if (spawn <= 5) handler.addObject(new BossBullet((int) x + 50, (int) y + 80, ID.Bullet, handler));

        }
        if (departTimer <= 0) {

            if (x > 550 && !departing) velX = -2;
            else if (x < 550 && !departing) velX = 2;
            else {
                velX = 0;
                velY = -2;
                departing = true;
            }
            if (y < -120) handler.removeObject(this);
        }

        if (x <= 5 || x >= Game.WIDTH - size) velX *= -1;


    }
    public void collisionWithPlayer() {
        HUD.HEALTH -= 25;

    }

    public boolean isNotColliding() {
        return !colliding;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }
}
