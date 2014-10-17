package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

import java.util.Random;

public class Tree extends Sprite {
    private float xAxisVelocity = -2f;
    private static long timer = 2500;
    private static Random random = new Random();

    Tree(long x, long y, int w, int h) {
        super(x, y, w, h);
    }

    public void move(float deltaTime) {
        this.moveAlongXAxis(xAxisVelocity, deltaTime);
    }

    public static boolean shouldAppear(float deltaTime) {
        timer += Math.round(deltaTime); // deltaTime;
        if (timer * random.nextInt(10) > 2500) { timer = 0; return true; }
        else { return false; }
    }

    public void draw(Graphics g) {
        super.draw(g, Assets.tree);
    }
}
