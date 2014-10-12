/*
 * -.- OCD -.-
 */

package uk.co.oliverdelange.otterhopper.sprites;

import android.graphics.Color;
import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

import java.util.Random;

public class Enemy extends Sprite {
    private int xAxisVelocity = -10;
    private static long timer = 2500;
    private static Random random = new Random();
    public boolean colliding;

    public Enemy(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void move() {
        this.moveAlongXAxis(xAxisVelocity);
    }

    public static boolean shouldAppear(float deltaTime) {
        timer += 1*deltaTime;
        if (timer * random.nextInt(10) > 2500) { timer = 0; return true; }
        else { return false; }
    }

    public void draw(Graphics g) {
        if (colliding)
            g.drawRect(this.getXPosition(), this.getYPosition(), this.width, this.height, Color.RED);
        super.draw(g, Assets.enemy);
    }
}
