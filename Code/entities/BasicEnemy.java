package Code.entities;

import Code.*;

import java.awt.*;

public class BasicEnemy extends EnemyObject {

    public static int damageFactor = 1;
    private final long COOLDOWN_DURATION = 5000;
    private final Handler handler;
    private final Color hotPink = new Color(255, 60, 200);

    private boolean isHit = false;
    private Color color = Color.red;
    private long lastHitTime;


    public BasicEnemy(double x, double y, ID id, Handler handler) {
        super(x, y, id, 15);
        velX = r.nextInt(4) + 2;
        velY = r.nextInt(4) + 2;
        this.handler = handler;

    }


    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) x, (int) y, size, size);

    }

    public void tick() {

        x += velX;
        y += velY;
        if (x <= 5 || x >= Game.WIDTH - size) velX *= -1;
        if (y <= 5 || y >= Game.HEIGHT - 40) velY *= -1;


        float life = 0.05F;
        if (!Game.loadParticles) life = 0.99F;
        handler.addObject(new Trail(x, y, ID.Trail, color, size, size, life, handler));

        long currentTime = System.currentTimeMillis();
        if (isHit && currentTime - lastHitTime >= COOLDOWN_DURATION) {
            if (color == Color.cyan) {
                isHit = false;
                velX /= 2;
                velY /= 2;
                color = Color.red;
            } else if (color == hotPink) {
                velX /= 2;
                velY /= 2;
                color = Color.cyan;
                lastHitTime = currentTime;
            }
        }

        if (velX > 20 || velY > 20) {
            System.out.print("\rIndex: " + handler.object.indexOf(this) + ", Velocity X: " + velX + ", Velocity Y: " + velY);
            handler.object.remove(this);
            Game.addEnemy();
        }
    }

    @Override
    public void collisionWithPlayer() {
        if (color == Color.red) HUD.HEALTH -= damageFactor;
        if (color == Color.cyan) HUD.HEALTH -= 2 * damageFactor;
        if (color == hotPink) HUD.HEALTH -= 3 * damageFactor;
        long currentTime = System.currentTimeMillis();
        long timeSinceLastHit = currentTime - lastHitTime;

        if (timeSinceLastHit >= COOLDOWN_DURATION / 2) {
            if (color == hotPink) {
                lastHitTime = currentTime;
                isHit = true;
            } else if (color == Color.cyan) {
                lastHitTime = currentTime;
                color = hotPink;
                velX *= 2;
                velY *= 2;
                isHit = true;
            } else if (color == Color.red && timeSinceLastHit >= COOLDOWN_DURATION) {
                lastHitTime = currentTime;
                color = Color.cyan;
                velX *= 2;
                velY *= 2;
                isHit = true;
            }
        }

    }


}
