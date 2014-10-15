package uk.co.oliverdelange.otterhopper.sprites;

import android.graphics.Color;
import uk.co.oliverdelange.otterhopper.framework.Graphics;
import uk.co.oliverdelange.otterhopper.util.Conversion;

import java.util.ArrayList;

import static java.lang.Math.round;

public class Background {

    private ArrayList<Cloud> clouds = new ArrayList<>();

    public Background() {
    }

    public void update(AppearingSpriteFactory appearingSpriteFactory, float deltaTime) {
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

    private void drawSky(Graphics g) {
        g.drawRect(0, 0, g.getWidth(), g.getHeight(), Color.CYAN, Color.WHITE);
    }

    private void drawGrass(Graphics g) {
        g.drawRect(0, Conversion.safeLongToInt(round(g.getHeight() * 0.7)), g.getWidth(), g.getHeight(), Color.GREEN);
    }

    private void drawClouds(Graphics g) {
        for (Cloud cloud : clouds) {
            cloud.draw(g);
        }
    }
}
