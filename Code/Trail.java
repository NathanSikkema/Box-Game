package Code;

import java.awt.*;

public class Trail extends GameObject {

    private final float life;
    private final Handler handler;
    private final Color color;
    private final double width;
    private final double height;
    private float alpha = 1;


    public Trail(double x, double y, ID id, Color color, int width, int height, float life, Handler handler) {
        super(x, y, id, width);

        this.color = color;
        this.handler = handler;
        this.width = width;
        this.height = height;
        this.life = life;

    }

    @Override
    public void tick() {
        alpha -= (life - 0.00005f);
        if (alpha <= 0) handler.removeObject(this);

    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(makeTransparent(alpha));


        // Interpolate between the original color and its complementary color based on alpha
        Color trailColor = new Color(
                (int) (color.getRed() + (-color.getRed()) * (1 - alpha)),
                (int) (color.getGreen() + (-color.getGreen()) * (1 - alpha)),
                (int) (color.getBlue() + (-color.getBlue()) * (1 - alpha))
        );

        g.setColor(trailColor);
        g.fillRect((int) x, (int) y, (int) width, (int) height);

        g2d.setComposite(makeTransparent(1.0f));
    }

    private AlphaComposite makeTransparent(float alpha) {
        return AlphaComposite.getInstance(3, alpha);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }
}
