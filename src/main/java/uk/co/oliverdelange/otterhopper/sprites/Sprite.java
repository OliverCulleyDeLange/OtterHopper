package uk.co.oliverdelange.otterhopper.sprites;

public class Sprite {

    private final float speed = 1;
    public final int width;
    public final int height;
    private int x, y, r, b ;

    Sprite(int x, int y, int width, int height) {
        this.height = height;
        this.width = width;
        setNewPosition(x, y);
    }

    public boolean collidesWith(Sprite sprite) {
        return  !(
                sprite.r <= this.x ||
                sprite.x > this.r  ||
                sprite.b <= this.y ||
                sprite.y > this.b
        );
    }

    public void setNewXPosition(int x) {
        this.x = x;
        this.r = x + width;
    }

    public void setNewXPosition(double xd) {
        int x = (int) Math.round(xd);
        this.x = x;
        this.r = x + width;
    }

    public void setNewYPosition(int y) {
        this.y = y;
        this.b = y + height;
    }
    public void setNewYPosition(double yd) {
        int y = (int) Math.round(yd);
        this.y = y;
        this.b = y + height;
    }

    public void setNewPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.r = x + width;
        this.b = y + height;
    }

    public int getYPosition() {
        return y;
    }

    public int getXPosition() {
        return x;
    }

    public int getRPosition() {
        return r;
    }

    public int getBPosition() {
        return b;
    }

    public float getSpeed() {
        return speed;
    }

    public void moveAlongXAxis(int xAxisVelocity) {
        this.setNewXPosition( x * xAxisVelocity);
    }
}