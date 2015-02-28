package uk.co.oliverdelange.otterhopper.sprites;

import android.graphics.Color;
import uk.co.oliverdelange.otterhopper.framework.Graphics;
import uk.co.oliverdelange.otterhopper.util.Conversion;

import static java.lang.Math.round;

public class Background {
    
    private final AppearingSpritePool appearingSpritePool;

    public Background(AppearingSpritePool appearingSpritePool) {
        this.appearingSpritePool = appearingSpritePool;
    }

    public void update(float deltaTime) {
        updateClouds(deltaTime);
        updateTrees(deltaTime);
    }

    private void updateClouds(float deltaTime) {
        if (Cloud.shouldAppear(deltaTime)){
            try {
                appearingSpritePool.useCloud();
            } catch (IndexOutOfBoundsException e) {
//                logger.log("Ran out of trees to use! Maybe consider increasing the maximum!");
                //TODO Logging!
            }
        }

        for (Cloud cloud : appearingSpritePool.getClouds()) {
            cloud.move(deltaTime);
            if (cloud.getXPosition() < 0 - cloud.width) {
                appearingSpritePool.freeCloud(cloud);
            }
        }
    }

    private void updateTrees(final float deltaTime) {
        if (Tree.shouldAppear(deltaTime)) {
            try {
                appearingSpritePool.useTree();
            } catch (IndexOutOfBoundsException e) {
//                logger.log("Ran out of trees to use! Maybe consider increasing the maximum!");
                //TODO Logging!
            }
        }
        for (Tree tree : appearingSpritePool.getTrees()) {
            tree.move(deltaTime);
            if (tree.getXPosition() < 0 - tree.width) {
                appearingSpritePool.freeTree(tree);
            }
        }
    }


    public void draw(Graphics g) {
        // TODO {Graphics] Randomise the terrain. Clouds move slower than terrain. Sometimes birds fly over. Pretty stuff :)
        drawSky(g);
        drawGrass(g);
        drawClouds(g);
        drawTrees(g);
    }

    private void drawSky(Graphics g) {
        g.drawRectVerticalGradient(0, 0, g.getWidth(), g.getHeight(), Color.CYAN, Color.WHITE);
    }

    private void drawGrass(Graphics g) {
        g.drawRectVerticalGradient(0, Conversion.safeLongToInt(round(g.getHeight() * 0.7)), g.getWidth(), g.getHeight(), Color.GREEN, Color.WHITE);
    }

    private void drawTrees(Graphics g) {
        for (Tree tree : appearingSpritePool.getTrees()) {
            tree.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        for (Cloud cloud : appearingSpritePool.getClouds()) {
            cloud.draw(g);
        }
    }
}
