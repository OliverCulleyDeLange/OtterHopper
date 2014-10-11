package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.framework.AndroidImage;

public class Background extends Sprite {

    public Background(AndroidImage background) {
        super(0,0,background.getWidth(), background.getHeight());
    }

    public void update(float deltaTime) {
        int x = getXPosition();
        setNewXPosition(( x > 2308) ? 0 : x + (getSpeed() * deltaTime));
    }

    public int getInnerWidth() {
        return 864; //TODO be clever
    }
}
