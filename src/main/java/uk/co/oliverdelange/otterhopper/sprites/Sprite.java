package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.framework.AndroidImage;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

public class Sprite {

    private float speed;
    public final int width;
    public final int height;
    private long x;
    private long y;
    private long r;
    private long b ;

    Sprite(long x, long y, int width, int height) {
        this.height = height;
        this.width = width;
        setNewPosition(x, y);
        this.speed = 1;
    }

    Sprite(int x, int y, int width, int height, float speed) {
        this.height = height;
        this.width = width;
        this.speed = speed;
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

    public void setNewXPosition(float xd) {
        setNewXPosition(Math.round(xd));
    }
    public void setNewXPosition(int x) {
        this.x = x;
        this.r = x + width;
    }

    public void setNewYPosition(float yd) {
        setNewYPosition( Math.round(yd));
    }
    public void setNewYPosition(int y) {
        this.y = y;
        this.b = y + height;
    }

    public void setNewPosition(long x, long y) {
        this.x = x;
        this.y = y;
        this.r = x + width;
        this.b = y + height;
    }

    public int getYPosition() {
        return safeLongToInt(y);
    }

    public int getXPosition() {
        return safeLongToInt(x);
    }

    public long getRPosition() {
        return r;
    }

    public long getBPosition() {
        return b;
    }

    public float getSpeed() {
        return speed;
    }

    public void moveAlongXAxis(float xAxisVelocity) {
        this.setNewXPosition( x + (speed * xAxisVelocity));
    }

    private int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    public void draw(Graphics g, AndroidImage image) {
        g.drawAndroidImage(image, getXPosition(), getYPosition());
    }
}