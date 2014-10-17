package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.AndroidImage;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

import static java.lang.Math.round;

public class Otter extends Sprite {

    private boolean hopping = false;

    private float yAxisVelocity = 0;
    private float maximumYAxisVelocity = 2f;
    private float minimumYAxisVelocity = -4f;

    private int groundYPosition;

    private int animationFrame = 0;
    private int spritesOnMap = 6;
    private float animationTimer = 0;

    public Otter(AndroidImage otter, Graphics graphics) {
        super(
                graphics.getWidth() / 5,
                (int) round(graphics.getHeight() * 0.8),
                otter.getWidth() / 6,
                otter.getHeight(),
                5
        );
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
            applyGravity(delta);
        }
        updateAnimation(delta);
    }

    private void updateAnimation(float delta) {
        animationTimer += 1 * delta;
        if (animationTimer > 10) {
            if (!hopping) {
                if (animationFrame == 0) {
                    animationFrame = spritesOnMap - 1;
                } else {
                    animationFrame--;
                }
                animationTimer = 0;
            }
        }
    }

    private void applyGravity(float delta) {
        if (this.hopping) {
            yAxisVelocity += (0.13 * delta);
            if (yAxisVelocity >= maximumYAxisVelocity) yAxisVelocity = maximumYAxisVelocity;
            if (yAxisVelocity <= minimumYAxisVelocity) yAxisVelocity = minimumYAxisVelocity;
        } else {
            yAxisVelocity = 0;
        }
        float newYPosition = this.getYPosition() + (getSpeed() * yAxisVelocity) * delta;
        if (newYPosition >= groundYPosition) {
            newYPosition = groundYPosition;
            hopping = false;
            yAxisVelocity = 0;
        }
        this.setNewYPosition(newYPosition);
    }

    public void draw(Graphics g) {
        g.drawScaledImage(Assets.otter,
                getXPosition(), getYPosition(),
                width, height,
                width * animationFrame, 0,
                width, height);
    }
}