package uk.co.oliverdelange.otterhopper.framework;

public abstract class Screen {
    protected final AndroidGame game;

    public Screen(AndroidGame game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public abstract void paint();

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    public abstract void backButton();
}