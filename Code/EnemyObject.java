package Code;

public abstract class EnemyObject extends GameObject {
    protected boolean colliding = false;

    public EnemyObject(double x, double y, ID id, int size) {
        super(x, y, id, size);
    }

    public void collisionWithPlayer() {
    }

    public boolean isNotColliding() {
        return !colliding;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }
}
