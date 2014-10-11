package uk.co.oliverdelange.otterhopper.screen;

import android.graphics.Color;
import android.graphics.Paint;
import uk.co.oliverdelange.otterhopper.framework.AndroidGame;
import uk.co.oliverdelange.otterhopper.framework.Graphics;
import uk.co.oliverdelange.otterhopper.framework.Screen;
import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.sprites.Background;
import uk.co.oliverdelange.otterhopper.sprites.Enemy;
import uk.co.oliverdelange.otterhopper.sprites.Otter;
import uk.co.oliverdelange.otterhopper.sprites.Tree;

import java.util.ArrayList;
import java.util.List;

import static uk.co.oliverdelange.otterhopper.framework.AndroidInput.TouchEvent;
import static uk.co.oliverdelange.otterhopper.sprites.Enemy.newEnemy;
import static uk.co.oliverdelange.otterhopper.sprites.Tree.newTree;


public class GameScreen extends Screen {
    enum GameState {
        Ready, Running, Paused, GameOver
    }

    GameState state = GameState.Ready;

    Paint paint;

    private int highScore = 999; // TODO save game high score somehow
    private int score = 0;

    private Otter otter;
    private Background background;
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();


    public GameScreen(AndroidGame game) {
        super(game);

        otter = new Otter(Assets.otter, game.getGraphics());
        background = new Background(Assets.background);

        paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if (touchEvents.size() > 0)
            state = GameState.Running;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                otter.hop();
            }
        }

        background.update(deltaTime);
        if (Tree.shouldAppear()) {
            trees.add(newTree());
        }
        if (Enemy.shouldAppear()) {
            enemies.add(newEnemy());
        }

        for (Tree tree : trees) {
            tree.move();
        }
        for (Enemy enemy : enemies) {
            enemy.move();
        }
        otter.move(deltaTime);
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 300 && event.x < 980 && event.y > 100
                        && event.y < 500) {
                    nullify();
//                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawScaledImage(Assets.background,
                0, 0, //always only draw what is on screen
                g.getWidth(), g.getHeight(),
                background.getXPosition(), 0, //the inner rectangle of the bg
                background.getInnerWidth(), background.height
        );

        // Secondly, draw the UI above the game elements.
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawString("Tap to begin. Then tap to jump.",
                640, 300, paint);

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();

        g.drawScaledImage(Assets.otter,
                otter.getXPosition(), otter.getYPosition(),
                otter.getInnerWidth(), otter.height,
                otter.getInnerRectX(), 0,
                otter.getInnerWidth(), otter.height);

        for (Tree tree : trees) {
//            g.drawAndroidImage(Assets.tree, tree.getXPosition(), tree.getYPosition());
        }
        for (Enemy enemy : enemies) {
//            g.drawAndroidImage(Assets.enemy, enemy.getXPosition(), enemy.getYPosition());
        }
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        g.drawARGB(155, 0, 0, 0);

    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER.", 640, 300, paint);

    }

    private void nullify() {
        // Set all variables to null. You will be recreating them in the
        // constructor.
        paint = null;
        // Call garbage collector to clean up memory.
        System.gc();
    }

    @Override
    public void pause() {
        if (state == GameState.Running)
            state = GameState.Paused;

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        pause();
    }
}