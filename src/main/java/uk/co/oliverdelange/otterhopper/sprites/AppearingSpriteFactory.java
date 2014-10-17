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
    public int screenWidth;
    public int screenHeight;

    private double lowerTreeLimit = 0.4;
    private double upperTreeLimit = 0.6;
    private double lowerCloudLimit = 0;
    private double upperCloudLimit = 0.3;

    public AppearingSpriteFactory(Graphics graphics) {
        this.graphics = graphics;
        groundLevel = safeLongToInt(round(graphics.getHeight() * 0.8));
        screenWidth = graphics.getWidth();
        screenHeight = graphics.getHeight();
    }

    public Tree newTree() {
        int y = randomYPosition(lowerTreeLimit, upperTreeLimit);
        return new Tree(
            screenWidth, y,
            Assets.tree.getWidth(), Assets.tree.getHeight()
        );
    }

    public Enemy newEnemy() {
        return new Enemy(
            screenWidth ,groundLevel,
            Assets.enemy.getWidth(), Assets.enemy.getHeight()
        );
    }

    public Cloud newCloud() {
        int y = randomYPosition(lowerCloudLimit, upperCloudLimit);
        return new Cloud(
          screenWidth, y,
                random.nextInt(250) + 100, random.nextInt(150) + 100
        );
    }

    private int randomYPosition(double lowerLimit, double upperLimit) {
        long low = round( screenHeight * lowerLimit);
        long high = round( screenHeight * upperLimit);
        return round(random.nextInt(safeLongToInt(high - low)) + low);
    }
}
