package uk.co.oliverdelange.otterhopper.sprites;

import java.util.Random;

public class Tree extends Sprite {
    private float xAxisVelocity = -2f;
    private static long timer = 0;
    private static Random random = new Random();

    Tree(long x, long y, int w, int h) {
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
}
