package uk.co.oliverdelange.otterhopper.screen;

import uk.co.oliverdelange.otterhopper.framework.AndroidGame;
import uk.co.oliverdelange.otterhopper.framework.Graphics;
import uk.co.oliverdelange.otterhopper.framework.Screen;
import uk.co.oliverdelange.otterhopper.Assets;


public class LoadingScreen extends Screen {
    public LoadingScreen(AndroidGame game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {

        Graphics g = game.getGraphics();
        Assets.tree = g.newImage("img/tree_trans.png", Graphics.ImageFormat.RGB565);
        Assets.enemy = g.newImage("img/clefable.png", Graphics.ImageFormat.RGB565);
        Assets.otter = g.newImage("img/newOtter.png", Graphics.ImageFormat.RGB565);

        game.setScreen(new GameScreen(game));
    }

    @Override
    public void paint(float deltaTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}