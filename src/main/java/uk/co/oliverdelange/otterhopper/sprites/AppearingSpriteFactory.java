package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

import java.util.Random;

import static java.lang.Math.round;
import static uk.co.oliverdelange.otterhopper.util.Conversion.safeLongToInt;

public class AppearingSpriteFactory {

    public Graphics graphics;
    private Random random = new Random();
    public int groundLevel;

    private double lowerTreeLimit = 0.5;
    private double upperTreeLimit = 0.6;

    public AppearingSpriteFactory(Graphics graphics) {
        this.graphics = graphics;
        groundLevel = safeLongToInt(round(graphics.getHeight() * 0.8));
    }

    public Tree newTree() {
        long low = round( graphics.getHeight() * lowerTreeLimit);
        long high = round(graphics.getHeight() * upperTreeLimit);
        double randomPosition = random.nextInt(safeLongToInt(high - low)) + low;

        return new Tree(
            graphics.getWidth(), round(randomPosition),
            Assets.tree.getWidth(), Assets.tree.getHeight()
        );
    }

    public Enemy newEnemy() {
        return new Enemy(
            graphics.getWidth(),groundLevel,
            Assets.enemy.getWidth(), Assets.enemy.getHeight()
        );
    }
}
