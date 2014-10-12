/*
 * -.- OCD -.-
 */

package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

import java.util.Random;

public class Enemy extends Sprite {

    private boolean countedForScore;
    private int xAxisVelocity = -10;

    private static long timerCutOff = 1000;
    private static long timer = timerCutOff;

    private static Random random = new Random();

    public Enemy(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void move() {
        this.moveAlongXAxis(xAxisVelocity);
    }

    public static boolean shouldAppear(float deltaTime) {
        timer += deltaTime;
        if (timer * random.nextInt(10) > timerCutOff) { timer = 0; return true; }
        else { return false; }
    }

    public void draw(Graphics g) {
        super.draw(g, Assets.enemy); //TODO Achievements - new Enemies for different high scores
    }

    public boolean hasPassed(Otter otter) {
        return !countedForScore && getXPosition() < otter.getXPosition();
    }

    public void hasBeenCounted() {
        countedForScore = true;
    }
}
