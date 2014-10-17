package uk.co.oliverdelange.otterhopper.sprites;

import android.graphics.Color;
import uk.co.oliverdelange.otterhopper.framework.Graphics;
import uk.co.oliverdelange.otterhopper.util.Conversion;

import java.util.ArrayList;

import static java.lang.Math.round;

public class Background {

    private final ArrayList<Cloud> clouds = new ArrayList<>();
    private final ArrayList<Cloud> cloudRemoval = new ArrayList<>();

    private final ArrayList<Tree> trees = new ArrayList<>();
    private final ArrayList<Tree> treeRemoval = new ArrayList<>();

    public void update(AppearingSpriteFactory appearingSpriteFactory, float deltaTime) {
        updateClouds(appearingSpriteFactory, deltaTime);
        updateTrees(appearingSpriteFactory, deltaTime);
    }

    private void updateClouds(AppearingSpriteFactory appearingSpriteFactory, float deltaTime) {
        if (Cloud.shouldAppear(deltaTime)){
            clouds.add(appearingSpriteFactory.newCloud());
        }

        for (Cloud cloud : clouds) {
            cloud.move(deltaTime);
            if (cloud.getXPosition() < 0 - cloud.width) {
                cloudRemoval.add(cloud);
            }
        }
        for (Cloud cloud : cloudRemoval) clouds.remove(cloud);
    }

    private void updateTrees(AppearingSpriteFactory appearingSpriteFactory, final float deltaTime) {
        if (Tree.shouldAppear(deltaTime)) {
            trees.add(appearingSpriteFactory.newTree());
        }
        for (Tree tree : trees) {
            tree.move(deltaTime);
            if (tree.getXPosition() < 0 - tree.width) {
                treeRemoval.add(tree);
            }
        }
        for (Tree tree : treeRemoval) trees.remove(tree);
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
        for (Tree tree : trees) {
            tree.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        for (Cloud cloud : clouds) {
            cloud.draw(g);
        }
    }
}
