package uk.co.oliverdelange.otterhopper.screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.R;
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
    private SharedPreferences preferences;
    private int score = 0;
    private int highScore = 0;
    private Otter otter;
    private Background background;
    private AppearingSpriteFactory appearingSpriteFactory;

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Enemy> enemyRemoval = new ArrayList<>();


    public GameScreen(AndroidGame game) {
        super(game);

        preferences = game.getPreferences(Context.MODE_PRIVATE);
        highScore = preferences.getInt(game.getString(R.string.saved_high_score), 0);

        Graphics graphics = game.getGraphics();
        otter = new Otter(Assets.otter, graphics);
        background = new Background();
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
        background.update(appearingSpriteFactory, deltaTime);
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        for (int i = 0; i < touchEvents.size(); i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                otter.hop();
            }
        }
        otter.move(deltaTime);
        background.update(appearingSpriteFactory, deltaTime);
        updateEnemies(deltaTime);
    }

    private void updateEnemies(float deltaTime) {
        if (Enemy.shouldAppear(deltaTime)) {
            enemies.add(appearingSpriteFactory.newEnemy());
        }
        for (Enemy enemy : enemies) {
            enemy.move(deltaTime);
            if (enemy.collidesWith(otter))
                state = GameState.GameOver;
            if (enemy.hasPassed(otter)) {
                score++;
                enemy.hasBeenCounted();
            }
            if (enemy.getXPosition() < 0 - enemy.width)
                enemyRemoval.add(enemy);
        }
        for (Enemy enemy : enemyRemoval) enemies.remove(enemy);
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        for (int i = 0; i <  touchEvents.size(); i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                state = GameState.Running;
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        for (int i = 0; i < touchEvents.size(); i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (score > highScore){
                    highScore = score;
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(game.getString(R.string.saved_high_score), highScore);
                    editor.commit();
                }
                score = 0;
                enemies.clear();
                state = GameState.Ready;
            }
        }
    }

    @Override
    public void paint() {
        Graphics g = game.getGraphics();
        g.drawARGB(255,0,0,255); //wipe scren and draw blue

        background.draw(g);
        drawEnemies(g);
        otter.draw(g);

        if (state == GameState.Ready)
            drawReadyUI(g);
        if (state == GameState.Running)
            drawRunningUI(g);
        if (state == GameState.Paused)
            drawPausedUI(g);
        if (state == GameState.GameOver)
            drawGameOverUI(g);

    }

    private void drawReadyUI(Graphics g) {
        g.drawString("Tap to begin. Then tap to jump.", 640, 300, paint);
        g.drawString("High score:" + highScore, 640, 200, paint);

    }

    private void drawRunningUI(Graphics g) {
        g.drawString("Score: " + score, 640, 300, paint);
    }

    private void drawPausedUI(Graphics g) {
        g.drawString("Tap to Resume.",
                640, 300, paint);
    }

    private void drawGameOverUI(Graphics g) {
        g.drawString("GAME OVER.", 640, 300, paint);
    }

    private void drawEnemies(Graphics g) {
        for (Enemy enemy : enemies) {
            enemy.draw(g);
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