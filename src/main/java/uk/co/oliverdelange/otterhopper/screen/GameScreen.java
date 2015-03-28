package uk.co.oliverdelange.otterhopper.screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import uk.co.oliverdelange.otterhopper.Assets;
import uk.co.oliverdelange.otterhopper.R;
import uk.co.oliverdelange.otterhopper.framework.AndroidGame;
import uk.co.oliverdelange.otterhopper.framework.Graphics;
import uk.co.oliverdelange.otterhopper.framework.Screen;
import uk.co.oliverdelange.otterhopper.sprites.AppearingSpritePool;
import uk.co.oliverdelange.otterhopper.sprites.Background;
import uk.co.oliverdelange.otterhopper.sprites.Enemy;
import uk.co.oliverdelange.otterhopper.sprites.Otter;

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
    private AppearingSpritePool appearingSpritePool;

    private long enemyTracker;

    public GameScreen(AndroidGame game) {
        super(game);

        preferences = game.getPreferences(Context.MODE_PRIVATE);
        highScore = preferences.getInt(game.getString(R.string.saved_high_score), 0);

        Graphics graphics = game.getGraphics();
        otter = new Otter(Assets.otter, graphics);
        appearingSpritePool = new AppearingSpritePool(graphics);
        background = new Background(appearingSpritePool);

        paint = new Paint();
        paint.setTypeface(Typeface.createFromAsset(game.getAssets(), "fonts/Lobster_1.3.otf"));
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

        otter.move(deltaTime, touchEvents);
        background.update(deltaTime);
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        otter.move(deltaTime, touchEvents);
        background.update(deltaTime);
        updateEnemies(deltaTime);
    }

    private void updateEnemies(float deltaTime) {
        if (appearingSpritePool.timer.enemyShouldAppear(deltaTime)) {
            long time = System.currentTimeMillis();
            System.out.println("A wild clefairy appeared after " + (time - enemyTracker) + " milliseconds!");
            enemyTracker = time;
            appearingSpritePool.useEnemy();
        }
        for (Enemy enemy : appearingSpritePool.getEnemies()) {
            enemy.move(deltaTime);
            if (enemy.boxCollidesWith(otter))
                if (enemy.detailCollidesWith(otter)) {
                    state = GameState.GameOver;
                }
            if (enemy.hasPassed(otter)) {
                score++;
                enemy.hasBeenCounted();
            }
            if (enemy.getXPosition() < 0 - enemy.width)
                appearingSpritePool.freeEnemy(enemy);
        }
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
                appearingSpritePool.resetEnemies();
                state = GameState.Ready;
            }
        }
    }

    @Override
    public void paint() {
        Graphics g = game.getGraphics();
        g.drawARGB(255, 0, 0, 255); //wipe scren and draw blue

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
        g.drawString("Tap to begin. Then tap to jump.", 20, 70, paint);
        g.drawString("High score:" + highScore, 20, 200, paint);

    }

    private void drawRunningUI(Graphics g) {
        g.drawString("Score: " + score, 20, 70, paint);
    }

    private void drawPausedUI(Graphics g) {
        g.drawString("Tap to Resume.",
                20, 200, paint);
    }

    private void drawGameOverUI(Graphics g) {
        g.drawString("GAME OVER.", 20, 70, paint);
    }

    private void drawEnemies(Graphics g) {
        for (Enemy enemy : appearingSpritePool.getEnemies()) {
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