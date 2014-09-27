package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Game;

import java.util.Random;

public class AppearingSprite extends Sprite {

    private static long timer;
    private static Random random = new Random();

    public static boolean shouldAppear() {
        if (timer > random.nextInt() * 100000000) {
            timer = 0;
            return true;
        } else {
            timer += Game.timeSinceLastLoop * Game.delta;
            return false;
        }
    }

    public AppearingSprite(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
