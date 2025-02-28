package Code.entities;

import Code.*;

import java.awt.*;

public class FastEnemy extends EnemyObject {

    private final Handler handler;
    private final Color lightPurple = new Color(178, 96, 218);


    public FastEnemy(double x, double y, ID id, Handler handler) {
        super(x, y, id, 15);
        this.handler = handler;

        velX = 2;
        velY = r.nextInt(10) + 5;
        if (velY < 7) {
            velY += 5;
            velY *= -1;
        }
        if (velY == 0) velY = 5;

    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (x <= 0 || x >= Game.WIDTH - 32) velX *= -1;
        if (y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
        handler.addObject(new Trail(x, y, ID.Trail, lightPurple, size, size, 0.05f, handler));
    }

    @Override
    public void render(Graphics g) {
        g.setColor(lightPurple);
        g.fillRect((int) x, (int) y, size, size);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }

    public void collisionWithPlayer() {
        HUD.HEALTH -= 2;

    }
}
