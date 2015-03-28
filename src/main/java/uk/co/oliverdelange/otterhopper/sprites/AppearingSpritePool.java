package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.Graphics;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.round;
import static uk.co.oliverdelange.otterhopper.util.Conversion.safeLongToInt;

public class AppearingSpritePool {

    public Graphics graphics;
    private Random random = new Random();
    public int groundLevel;
    public int screenWidth;
    public int screenHeight;

    private static final int MAXIMUM_TREES = 20;
    private static final double LOWER_TREE_LIMIT = 0.4;
    private static final double UPPER_TREE_LIMIT = 0.6;

    private static final int MAXIMUM_CLOUDS = 10;
    private static final double LOWER_CLOUD_LIMIT = 0;
    private static final double UPPER_CLOUD_LIMIT = 0.3;

    private static final int MAXIMUM_ENEMIES = 10;

    private SpritePool<Tree> treePool;
    private SpritePool<Enemy> enemyPool;
    private SpritePool<Cloud> cloudPool;

    public SpriteTimer timer;

    public AppearingSpritePool(Graphics graphics) {
        this.graphics = graphics;
        groundLevel = safeLongToInt(round(graphics.getHeight() * 0.8));
        screenWidth = graphics.getWidth();
        screenHeight = graphics.getHeight();

        ArrayList<Tree> trees = new ArrayList<>();
        for (int i = 0; i < MAXIMUM_TREES; i++) trees.add(new Tree(screenWidth, 0, Assets.tree.getWidth(), Assets.tree.getHeight()));
        treePool = new SpritePool(trees);

        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < MAXIMUM_ENEMIES; i++) enemies.add(new Enemy(screenWidth, 0, Assets.enemy.getWidth(), Assets.enemy.getHeight()));
        enemyPool = new SpritePool(enemies);

        ArrayList<Cloud> clouds = new ArrayList<>();
        for (int i = 0; i < MAXIMUM_CLOUDS; i++) clouds.add(new Cloud(screenWidth, 0, random.nextInt(250) + 100, random.nextInt(150) + 100));
        cloudPool = new SpritePool(clouds);

        this.timer = new SpriteTimer(trees,enemies,clouds);
    }

    public Tree useTree() throws IndexOutOfBoundsException {
        int y = randomYPosition(LOWER_TREE_LIMIT, UPPER_TREE_LIMIT);
        Tree tree = treePool.getSprite();
        tree.setNewYPosition(y);
        tree.setNewXPosition(screenWidth);
        return tree;
    }

    public void freeTree(Tree tree) {
        treePool.markSpriteForFreeing(tree);
    }

    public ArrayList<Tree> getTrees() {
        return treePool.getSpritesInUse();
    }

    public Enemy useEnemy() {
        Enemy enemy = enemyPool.getSprite();
        enemy.setNewYPosition(groundLevel);
        enemy.setNewXPosition(screenWidth);
        return enemy;
    }

    public void freeEnemy(Enemy enemy) {
        enemyPool.markSpriteForFreeing(enemy);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemyPool.getSpritesInUse();
    }

    public void resetEnemies() {
        for (Enemy enemy : getEnemies()) {
            freeEnemy(enemy);
        }
        enemyPool.freeSpritesToBeFreed();
    }

    public void useCloud() throws IndexOutOfBoundsException {
        int y = randomYPosition(LOWER_CLOUD_LIMIT, UPPER_CLOUD_LIMIT);
        Cloud cloud = cloudPool.getSprite();
        cloud.setNewYPosition(y);
        cloud.setNewXPosition(screenWidth);
    }

    public void freeCloud(Cloud cloud) {
        cloudPool.markSpriteForFreeing(cloud);
    }

    public ArrayList<Cloud> getClouds() {
        return cloudPool.getSpritesInUse();
    }


    private int randomYPosition(double lowerLimit, double upperLimit) {
        long low = round(screenHeight * lowerLimit);
        long high = round(screenHeight * upperLimit);
        return round(random.nextInt(safeLongToInt(high - low)) + low);
    }
}
