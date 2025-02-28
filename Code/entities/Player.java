package Code.entities;

import Code.*;
import java.awt.*;

public class Player extends GameObject {

    Handler handler;
    Color color = new Color(79, 158, 250);

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id, 35);
        this.handler = handler;

    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;


        int spacer = 5;
        x = Game.clamp(x, 5, Game.WIDTH - spacer - size-3);
        y = Game.clamp(y, 5, Game.HEIGHT - (spacer + size) * 2+15);

        handler.addObject(new Trail(x, y, ID.Trail, color, size, size, 0.1F, handler));


        collision();
    }


    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.BasicEnemy) {
                if (this.getBounds().intersects((tempObject.getBounds()))) {

                    if (((BasicEnemy) tempObject).isNotColliding()) {
                        ((BasicEnemy) tempObject).collisionWithPlayer();
                        ((BasicEnemy) tempObject).setColliding(true);
                        HUD.collisionCount++;
                    }

                } else ((BasicEnemy) tempObject).setColliding(false);
            }
            else if (tempObject.getId() == ID.TrailingEnemy && this.getBounds().intersects((tempObject.getBounds()))) {
                ((TrailingEnemy) tempObject).collisionWithPlayer();
            }
            else if (tempObject.getId() == ID.Health) {
                if (getBounds().intersects((tempObject.getBounds()))) {
                    HUD.HEALTH += ((Health) tempObject).getHealthBoost();
                    handler.object.remove(tempObject);
                }
            }
            else if (tempObject.getId() == ID.Coin) {
                if (getBounds().intersects((tempObject.getBounds()))) {
                    HUD.BANK += 1;
                    handler.object.remove(tempObject);
                }
            }
            else if (tempObject.getId() == ID.BossEnemy){
                if (this.getBounds().intersects((tempObject.getBounds()))) {
                    if (((BossEnemy) tempObject).isNotColliding()) {
                        ((BossEnemy) tempObject).collisionWithPlayer();
                        ((BossEnemy) tempObject).setColliding(true);
                        HUD.collisionCount++;
                    }
                } else ((BossEnemy) tempObject).setColliding(false);
            }
            else if (tempObject.getId() == ID.Bullet){
                if (this.getBounds().intersects((tempObject.getBounds()))) {
                    if (((BossBullet) tempObject).isNotColliding()) {
                        ((BossBullet) tempObject).collisionWithPlayer();
                        ((BossBullet) tempObject).setColliding(true);
                        HUD.collisionCount++;
                    }
                }else ((BossBullet) tempObject).setColliding(false);
            }
            else if (tempObject.getId() == ID.FastEnemy){
                if (this.getBounds().intersects((tempObject.getBounds()))) {
                    if (((FastEnemy) tempObject).isNotColliding()) {
                        ((FastEnemy) tempObject).collisionWithPlayer();
                        ((FastEnemy) tempObject).setColliding(true);
                        HUD.collisionCount++;
                    }
                }else ((FastEnemy) tempObject).setColliding(false);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, size, size);
    }
}
