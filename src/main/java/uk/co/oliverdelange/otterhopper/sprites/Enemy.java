/*
 * -.- OCD -.-
 */

package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

public class Enemy extends AppearingSprite {

    private boolean countedForScore;
    private int xAxisVelocity = -8;

    public Enemy(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void move(float deltaTime) {
        this.moveAlongXAxis(xAxisVelocity, deltaTime);
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
