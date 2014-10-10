package uk.co.oliverdelange.otterhopper.framework;

public interface Game {

    public AndroidInput getInput();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();
}