package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.OtterHopperGame;

public class Background {

    private static Sprite sprite;

    private Background() {}


    public static void update() {
        sprite.setNewXPosition( OtterHopperGame.delta * sprite.getSpeed());
    }
}
