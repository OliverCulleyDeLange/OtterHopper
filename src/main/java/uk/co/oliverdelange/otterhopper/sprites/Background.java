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

    public void draw(Graphics g) {
        //TODO Don't draw image on loop, draw boxes with gradients and draw clouds over the top etc.
        // Randomise the terrain. Clouds move slower than terrain. Sometimes birds fly over. Pretty stuff :)
        g.drawScaledImage(Assets.background,
                0, 0,
                g.getWidth(), g.getHeight(),
                getXPosition(), 0,
                getInnerWidth(), height
        );
    }

    private int getInnerWidth() {
        return 864; //TODO remove need for this
    }
}
