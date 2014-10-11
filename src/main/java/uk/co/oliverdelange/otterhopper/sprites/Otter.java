package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.framework.AndroidImage;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

import static java.lang.Math.round;

public class Otter extends Sprite {

    private boolean hopping = false;

    private int yAxisVelocity = 0;
    private int maximumYAxisVelocity = 1;
    private int minimumYAxisVelocity = 3;

    private int groundYPosition;

    private int animationFrame = 0;
    private int spritesOnMap = 6;
    private float animationTimer = 0;

    public Otter(AndroidImage otter, Graphics graphics) {
        super(graphics.getWidth() / 5, (int) round(graphics.getHeight() * 0.8), otter.getWidth(), otter.getHeight());
        groundYPosition = (int) round(graphics.getHeight() * 0.8);
    }

    public void hop() {
        if (!hopping) {
            this.hopping = true;
            yAxisVelocity = minimumYAxisVelocity;
        }
    }

    public void move(float delta) {
        if (this.hopping) {
            applyGravity();
        }
        updateAnimation(delta);
    }

    private void updateAnimation(float delta) {
        animationTimer += 1 * delta;
        if (animationTimer > 10) {
            if (!hopping) {
                if (animationFrame == 0) {
                    animationFrame = spritesOnMap -1;
                } else {
                    animationFrame--;
                }
                animationTimer= 0;
            }
        }
    }

    private void applyGravity() {
        if (this.hopping) {
            yAxisVelocity += (0.08); //TODO deltaTime dependent
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

    public int getInnerRectX() {
        return getInnerWidth() * animationFrame;
    }

    public int getInnerWidth() {
        return width / spritesOnMap;
    }
}