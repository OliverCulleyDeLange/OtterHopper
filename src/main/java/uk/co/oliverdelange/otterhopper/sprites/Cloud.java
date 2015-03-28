package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.framework.Graphics;

public class Cloud extends AppearingSprite{

    private float xAxisVelocity = -0.7f;

    Cloud(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setMillisecondsBetweenAppearance(5000);
    }


    public void move(float deltaTime) {
        this.moveAlongXAxis(xAxisVelocity, deltaTime);
    }

    public void draw(Graphics g) {
        g.drawShape(getXPosition(), getYPosition(), width, height);
    }

    @Override
    public void reset() {

    }
}
