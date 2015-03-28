package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

public class Tree extends AppearingSprite {
    private float xAxisVelocity = -2.5f;

    Tree(long x, long y, int w, int h) {
        super(x, y, w, h);
        this.setMillisecondsBetweenAppearance(900);
    }

    public void move(float deltaTime) {
        this.moveAlongXAxis(xAxisVelocity, deltaTime);
    }

    public void draw(Graphics g) {
        super.draw(g, Assets.tree);
    }

    @Override
    public void reset() {

    }
}
