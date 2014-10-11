package uk.co.oliverdelange.otterhopper.screen;

import android.graphics.Color;
import android.graphics.Paint;
import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.framework.AndroidGame;
import uk.co.oliverdelange.otterhopper.framework.Graphics;
import uk.co.oliverdelange.otterhopper.framework.Screen;
import uk.co.oliverdelange.otterhopper.sprites.*;

import java.util.ArrayList;
import java.util.List;

import static uk.co.oliverdelange.otterhopper.framework.AndroidInput.TouchEvent;


public class GameScreen extends Screen {


    enum GameState {
        Ready, Running, Paused, GameOver;
    }

    GameState state = GameState.Ready;

    Paint paint;

    private int highScore = 999; // TODO save game high score somehow

    private int score = 0;
    private Otter otter;

    private Background background;
    private AppearingSpriteFactory appearingSpriteFactory;
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Tree> treeRemoval = new ArrayList<>();
    private final ArrayList<Enemy> enemyRemoval = new ArrayList<>();


    public GameScreen(AndroidGame game) {
        super(game);

        Graphics graphics = game.getGraphics();
        otter = new Otter(Assets.otter, graphics);
        background = new Background(Assets.background);
        appearingSpriteFactory = new AppearingSpriteFactory(graphics);
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
            updateReady(touchEvents, deltaTime);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents, float deltaTime) {
        if (touchEvents.size() > 0)
            state = GameState.Running;

        otter.move(deltaTime);
        background.update(deltaTime);
        updateTrees(deltaTime);
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        for (int i = 0; i < touchEvents.size(); i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                otter.hop();
            }
        }
        otter.move(deltaTime);
        background.update(deltaTime);
        updateTrees(deltaTime);
        updateEnemies(deltaTime);
    }

    private void updateEnemies(float deltaTime) {
        if (Enemy.shouldAppear(deltaTime)) {
            enemies.add(appearingSpriteFactory.newEnemy());
        }
        for (Enemy enemy : enemies) {
            enemy.move();
            if (enemy.getXPosition() < 0 - enemy.width) {
                enemyRemoval.add(enemy);
            }
        }
        for (Enemy enemy : enemyRemoval) enemies.remove(enemy);
    }

    private void updateTrees(float deltaTime) {
        if (Tree.shouldAppear(deltaTime)) {
            trees.add(appearingSpriteFactory.newTree());
        }
        for (Tree tree : trees) {
            tree.move();
            if (tree.getXPosition() < 0 - tree.width) {
                treeRemoval.add(tree);
            }
        }
        for (Tree tree : treeRemoval) trees.remove(tree);
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
        drawOtter(g);
        drawTrees(g);

        g.drawString("Tap to begin. Then tap to jump.",
                640, 300, paint);

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        drawTrees(g);
        drawEnemies(g);
        drawOtter(g);
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

    private void drawOtter(Graphics g) {
        g.drawScaledImage(Assets.otter,
                otter.getXPosition(), otter.getYPosition(),
                otter.getInnerWidth(), otter.height,
                otter.getInnerRectX(), 0,
                otter.getInnerWidth(), otter.height);
    }

    private void drawTrees(Graphics g) {
        for (Tree tree : trees) {
            g.drawAndroidImage(Assets.tree, tree.getXPosition(), tree.getYPosition());
        }
    }

    private void drawEnemies(Graphics g) {
        for (Enemy enemy : enemies) {
            g.drawAndroidImage(Assets.enemy, enemy.getXPosition(), enemy.getYPosition());
        }
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