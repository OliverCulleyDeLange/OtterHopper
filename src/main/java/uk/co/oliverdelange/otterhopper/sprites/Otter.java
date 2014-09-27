package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Game;

public class Otter extends Sprite {

    private boolean hopping = false;

    private int yAxisVelocity = 0;
    private int maximumYAxisVelocity = 1;
    private int minimumYAxisVelocity = 3;

    private int groundYPosition = 100; // TODO base this on screen height

    private int animationFrame = 0;
    private long animationTimer = 0;

    public Otter() {
        super(100, 100, 100, 100); // TODO replace with sensible values for player position and size
    }

    public void hop() {
        this.hopping = true;
        yAxisVelocity = minimumYAxisVelocity;
    }

    public void move() {
        if (this.hopping) {
            applyGravity();
        }
        updateAnimation();
    }

    private void updateAnimation() {
        //TODO
    }

    private void applyGravity() {
        if (this.hopping) {
            yAxisVelocity += (0.08 * Game.delta);
            if (yAxisVelocity >= maximumYAxisVelocity) yAxisVelocity = maximumYAxisVelocity;
            if (yAxisVelocity <= minimumYAxisVelocity) yAxisVelocity = minimumYAxisVelocity;
        } else {
            yAxisVelocity = 0;
        }
        int newYPosition = this.getYPosition() * yAxisVelocity;
        if (newYPosition >= groundYPosition) {
            newYPosition = groundYPosition;
            hopping = false;
            yAxisVelocity = 0;
        }
        this.setNewYPosition(newYPosition);
    }
}