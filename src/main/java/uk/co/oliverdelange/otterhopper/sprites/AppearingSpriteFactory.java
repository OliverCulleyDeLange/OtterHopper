package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

import java.util.Random;

import static java.lang.Math.round;

public class AppearingSpriteFactory {

    public Graphics graphics;
    Random random;

    public AppearingSpriteFactory(Graphics graphics) {
        this.graphics = graphics;
        random = new Random();
    }

    public Tree newTree() {
        return new Tree(
            graphics.getWidth(), randomYPosition(),
            Assets.tree.getWidth(), Assets.tree.getHeight()
        );
    }

    public Enemy newEnemy() {
        return new Enemy(
            graphics.getWidth(), (int) round(graphics.getHeight() * 0.8),
            Assets.tree.getWidth(), Assets.tree.getHeight()
        );
    }

    private int randomYPosition() {
        double Low = graphics.getHeight() * 0.5;
        double High = graphics.getHeight() * 0.6;
        double randomPosition = random.nextInt((int) (High-Low)) + Low;
        return (int) Math.round(randomPosition);
    }
}
