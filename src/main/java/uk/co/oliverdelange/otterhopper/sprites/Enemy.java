/*
 * -.- OCD -.-
 */

package uk.co.oliverdelange.otterhopper.sprites;

public class Enemy extends AppearingSprite {
    private int xAxisVelocity = -2;

    public Enemy() {
        super(200, 100, 50, 50); //TODO do this intelligently based on scale and stuff
    }

    public void move() {
        this.moveAlongXAxis(xAxisVelocity);
    }

    public static Enemy newEnemy() {
        return new Enemy();
    }
}
