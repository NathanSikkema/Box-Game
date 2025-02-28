package Code.entities;

import Code.*;

import java.awt.*;

public class MenuParticle extends EnemyObject {

    private final Handler handler;
    private Color color;
    private float alpha;

    public MenuParticle(double x, double y, ID id, Handler handler) {
        super(x, y, id, 15);
        this.handler = handler;

        velX = r.nextInt(10) - 5;
        velY = r.nextInt(10) - 5;
        if (!(velX >= 1 || velX <= -1)) velX = 5;
        if (!(velY >= 1 || velY <= -1)) velY = 5;
        alpha = 1f;
        color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255), (int) (alpha * 255));


    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (x <= 0 || x >= Game.WIDTH - size) velX *= -1;
        if (y <= 0 || y >= Game.HEIGHT - 40) velY *= -1;
        handler.addObject(new Trail(x, y, ID.Trail, color, size, size, 0.05f, handler));

    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, size, size);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size);
    }
    public double getVelX (){
        return this.velX;
    }
    public double getVelY (){
        return this.velY;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
