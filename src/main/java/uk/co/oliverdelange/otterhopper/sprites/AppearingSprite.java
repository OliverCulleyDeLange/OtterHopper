package uk.co.oliverdelange.otterhopper.sprites;

import java.util.Random;

public abstract class AppearingSprite extends Sprite {
    protected static Random random = new Random();

    private long lowMillisecondGap = 1000;
    private float randomMultiplier;
    private long lastAppeared;

    public AppearingSprite(int x, int y, int w, int h) {
        super(x,y,w,h);
    }
    public AppearingSprite(long x, long y, int w, int h) {
        super(x,y,w,h);
    }

    public void setMillisecondsBetweenAppearance(long millis) {
        this.lowMillisecondGap = millis;
    }

    public boolean shouldAppear(float deltaTime) {
        long time = System.currentTimeMillis(); //1000 millis to a second

        if ((time - lastAppeared) > lowMillisecondGap * randomMultiplier) {
            lastAppeared = time;
            randomMultiplier = 1 + random.nextFloat();
            return true;
        }
        else {
            return false;
        }
    }

}
