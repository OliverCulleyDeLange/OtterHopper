package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

public class Cloud extends AppearingSprite{

    private float xAxisVelocity = -1f;

    private int animationFrame = 0;

    Cloud(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setMillisecondsBetweenAppearance(7000);
        this.animationFrame = random.nextInt(5);
    }


    public void move(float deltaTime) {
        this.moveAlongXAxis(xAxisVelocity, deltaTime);
    }

    public void draw(Graphics g) {
        g.drawScaledImage(Assets.weather,
                getXPosition(), getYPosition(),
                width, height,
                width * animationFrame, 0,
                width, height);
    }


    @Override
    public void reset() {

    }
}
