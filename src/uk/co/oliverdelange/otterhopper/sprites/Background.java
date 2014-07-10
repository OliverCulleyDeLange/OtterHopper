package uk.co.oliverdelange.otterhopper.sprites;

import uk.co.oliverdelange.otterhopper.Game;

public class Background {

    private static Sprite sprite;

    private Background() {}


    public static void update() {
        sprite.setNewXPosition( Game.delta * sprite.getSpeed());
    }
}
