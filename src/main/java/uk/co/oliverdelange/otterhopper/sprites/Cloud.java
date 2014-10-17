package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.framework.Graphics;

import java.util.Random;

public class Cloud extends Sprite{

    Cloud(int x, int y, int width, int height) {
        super(x, y, width, height, 2);
    }

    private float xAxisVelocity = -1f;
    private static long timer = 2500;
    private static Random random = new Random();

    public void move(float deltaTime) {
        this.moveAlongXAxis(xAxisVelocity, deltaTime);
    }

    public static boolean shouldAppear(float deltaTime) {
        timer += Math.round(deltaTime); // deltaTime;
        if (timer * random.nextInt(10) > 2500) { timer = 0; return true; }
        else { return false; }
    }

    public void draw(Graphics g) {
        g.drawShape(getXPosition(), getYPosition(), width, height);
    }
}
