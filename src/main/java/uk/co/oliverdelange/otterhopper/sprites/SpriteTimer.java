package uk.co.oliverdelange.otterhopper.sprites;

import java.util.ArrayList;

public class SpriteTimer {

    Tree tree;
    Enemy enemy;
    Cloud cloud;

    public SpriteTimer(ArrayList<Tree> trees, ArrayList<Enemy> enemies, ArrayList<Cloud> clouds) {
        this.cloud = clouds.get(0);
        this.enemy = enemies.get(0);
        this.tree = trees.get(0);
    }

    public boolean enemyShouldAppear(float deltaTime) {
        return enemy.shouldAppear(deltaTime);
    }

    public boolean treeShouldAppear(float deltaTime) {
        return tree.shouldAppear(deltaTime);
    }

    public boolean cloudShouldAppear(float deltaTime) {
        return cloud.shouldAppear(deltaTime);
    }
}
