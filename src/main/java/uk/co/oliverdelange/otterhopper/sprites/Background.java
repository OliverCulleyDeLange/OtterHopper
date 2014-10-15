package uk.co.oliverdelange.otterhopper.sprites;

import android.graphics.Color;
import uk.co.oliverdelange.otterhopper.framework.AndroidImage;
import uk.co.oliverdelange.otterhopper.framework.Graphics;
import uk.co.oliverdelange.otterhopper.util.Conversion;

import java.util.ArrayList;

import static java.lang.Math.round;

public class Background extends Sprite {

    private ArrayList<Cloud> clouds = new ArrayList<>();

    public Background(AndroidImage background) {
        super(0,0,background.getWidth(), background.getHeight());
    }

    public void update(AppearingSpriteFactory appearingSpriteFactory, float deltaTime) {
        long x = getXPosition();
        setNewXPosition((x > 2308) ? 0 : x + (getSpeed() * deltaTime));

        if (Cloud.shouldAppear(deltaTime)){
            Cloud cloud = appearingSpriteFactory.newCloud();
            clouds.add(cloud);
        }

        for (Cloud cloud : clouds) {
            cloud.setNewXPosition(cloud.getXPosition() - (cloud.getSpeed() * deltaTime));
        }
    }

    public void draw(Graphics g) {
        // TODO {Graphics] Randomise the terrain. Clouds move slower than terrain. Sometimes birds fly over. Pretty stuff :)
        drawSky(g);
        drawGrass(g);
        drawClouds(g);
    }

    private void drawGrass(Graphics g) {
        g.drawRect(0, Conversion.safeLongToInt(round(g.getHeight() * 0.7)), g.getWidth(), g.getHeight(), Color.GREEN);
    }

    private void drawClouds(Graphics g) {
        for (Cloud cloud : clouds) {
            cloud.draw(g);
        }
    }

    private void drawSky(Graphics g) {
        g.drawRect(0, 0, g.getWidth(), g.getHeight(), Color.CYAN, Color.WHITE);
    }

    private int getInnerWidth() {
        return 864; //TODO remove need for this
    }
}
