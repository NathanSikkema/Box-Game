package Code.entities;

import Code.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TrailingEnemy extends EnemyObject {
    public static int damageFactor = 1;
    private final Handler handler;
    private final Color darkPurple = new Color(47, 23, 86);

    public TrailingEnemy(double x, double y, ID id, Handler handler) {
        super(x, y, id, 50);


        Random random = new Random();
        velX = random.nextInt(4) + 2;
        velY = random.nextInt(4) + 2;
        this.handler = handler;

    }

    @Override
    public void tick() {
        GameObject target = handler.object.getFirst();
        double targetX = target.getX();
        double targetY = target.getY();
        double diffX = targetX + ((double) target.getSize() / 2) - x - (double) size / 2;
        double diffY = targetY + ((double) target.getSize() / 2) - y - (double) size / 2;
        double distance = Math.sqrt(diffX * diffX + diffY * diffY);
        if (distance > 1) {
            double velFactor = 3 / distance;
            velX = (int) (diffX * velFactor);
            velY = (int) (diffY * velFactor);
        } else {
            velX = 0;
            velY = 0;
        }
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, Game.WIDTH - 5 - size);
        y = Game.clamp(y, 0, Game.HEIGHT - size * 2);

    }


    @Override
    public void render(Graphics g) {

        handler.addObject(new Trail(x, y, ID.Trail, darkPurple, size, size, 0.02F, handler));
        Image image = new ImageIcon("src/Assets/Enderman.png").getImage();
        g.drawImage(image, (int) x, (int) y, size, size, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }

    public void collisionWithPlayer() {
        HUD.HEALTH -= 0.1 * damageFactor;
    }
}
