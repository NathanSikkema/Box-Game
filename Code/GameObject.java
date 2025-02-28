package Code;

import java.awt.*;
import java.util.Random;

public abstract class GameObject {

    protected double x;
    protected double y;
    protected ID id;
    protected double velX;
    protected double velY;
    protected int size;
    public static Random r = new Random();

    public GameObject(double x, double y, ID id, int size) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.size = size;
    }



    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public ID getId() {
        return id;
    }
    public void setId(ID id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }
    public void setVelY(double velY) {
        this.velY = velY;
    }

    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public double getVelX (){
        return this.velX;
    }
}
