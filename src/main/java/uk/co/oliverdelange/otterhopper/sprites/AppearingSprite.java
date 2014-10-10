package uk.co.oliverdelange.otterhopper.sprites;

import java.util.Random;

public class AppearingSprite extends Sprite {

    private static long timer;
    private static Random random = new Random();

    public static boolean shouldAppear() {
        int timeIncrease = random.nextInt() * 100;
        if (timer > timeIncrease) {
            timer = 0;
            return true;
        } else {
            timer += timeIncrease   ;
            return false;
        }
    }

    public AppearingSprite(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
