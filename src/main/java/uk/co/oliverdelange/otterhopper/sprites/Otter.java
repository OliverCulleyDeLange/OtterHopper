package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.AndroidImage;
import uk.co.oliverdelange.otterhopper.framework.AndroidInput;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

import java.util.List;

import static java.lang.Math.round;

public class Otter extends Sprite {

    private boolean hopping = false;
    private boolean falling;
    private boolean fallen;

    private float yAxisVelocity = 0;
    private float maximumYAxisVelocity = 2f;
    private float minimumYAxisVelocity = -4f;

    private int groundYPosition;
    private int groundXPosition;
    private int XPosition;
    private int angle = 0;

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
        groundXPosition = graphics.getWidth() / 5;
    }

    public void move(float delta, List<AndroidInput.TouchEvent> touchEvents) {
        for (AndroidInput.TouchEvent event : touchEvents) {
            if (event.type == AndroidInput.TouchEvent.TOUCH_DOWN) {
                hop();
            }
        }
        if (this.hopping) {
            applyGravity(delta);
        }
        if (falling&&!fallen) {
            if (angle < 180) {
                angle = angle + 15;
                setNewXPosition(getXPosition() + 5);
            } else {
                fallen = true;
                falling = false;
            }
        } else if (!fallen) {
            updateAnimation(delta);
        }
    }

    public void hop() {
        if (!hopping) {
            this.hopping = true;
            yAxisVelocity = minimumYAxisVelocity;
        }
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
                width, height,
                angle);
    }

    @Override
    public void reset() {
        fallen = false;
        falling = false;
        angle = 0;
        setNewXPosition(groundXPosition);
    }

    public void fall() {
        if (!fallen) {
            falling=true;
        }
    }
}