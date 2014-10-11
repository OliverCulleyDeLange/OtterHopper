package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.AndroidImage;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

public class Background extends Sprite {

    public Background(AndroidImage background) {
        super(0,0,background.getWidth(), background.getHeight());
    }

    public void update(float deltaTime) {
        long x = getXPosition();
        setNewXPosition(( x > 2308) ? 0 : x + (getSpeed() * deltaTime));
    }

    public int getInnerWidth() {
        return 864; //TODO be clever
    }

    public void draw(Graphics g) {
        g.drawScaledImage(Assets.background,
                0, 0,
                g.getWidth(), g.getHeight(),
                getXPosition(), 0,
                getInnerWidth(), height
        );
    }
}
