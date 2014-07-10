package uk.co.oliverdelange.otterhopper.sprites;

import java.util.Random;

public class Tree extends AppearingSprite {
    private int xAxisVelocity = -2;

    private Tree(int w, int h) {
        super(0, 0, w, h);
        randomifyYPosition();
        setNewXPosition(100); //TODO set based on screen width
    }

    private void randomifyYPosition() {
        Random random = new Random();
        int Low = 80; //TODO set these based on screen size
        int High = 120; //TODO set these based on screen size
        int randomPosition = (random.nextInt(High-Low) + Low) / 100;
        this.setNewYPosition(randomPosition);
    }

    public void move() {
        this.moveAlongXAxis(xAxisVelocity);
    }

    public static Tree newTree() {
        return new Tree(50,50); // TODO do this using screen size
    }
}
