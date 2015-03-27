/*
 * -.- OCD -.-
 */

package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

import java.util.Random;

public class Enemy extends Sprite {

    private boolean countedForScore;
    private int xAxisVelocity = -8;

    private static long lowMillisecondGap = 1000;
    private static float randomMultiplier;
    private static long lastAppeared;

    private static Random random = new Random();

    public Enemy(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void move(float deltaTime) {
        this.moveAlongXAxis(xAxisVelocity, deltaTime);
    }

    public static boolean shouldAppear(float deltaTime) {
        long time = System.currentTimeMillis(); //1000 millis to a second

        if ((time - lastAppeared) > lowMillisecondGap * randomMultiplier) {
            lastAppeared = time;
            randomMultiplier = 1 + random.nextFloat();
            return true;
        }
        else {
            return false;
        }
    }

    public void draw(Graphics g) {
//        g.drawRect(getXPosition(), getYPosition(), width, height, Color.RED);
        super.draw(g, Assets.enemy); //TODO Achievements - new Enemies for different high scores
    }

    public boolean hasPassed(Otter otter) {
        return !countedForScore && getXPosition() < otter.getXPosition();
    }

    public void hasBeenCounted() {
        countedForScore = true;
    }

    public void reset() {
        this.countedForScore = false;
    }
}
