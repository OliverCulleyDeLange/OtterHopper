package uk.co.oliverdelange.otterhopper;

import android.app.Activity;
import android.os.Bundle;
import uk.co.oliverdelange.otterhopper.sprites.Background;
import uk.co.oliverdelange.otterhopper.sprites.Enemy;
import uk.co.oliverdelange.otterhopper.sprites.Otter;
import uk.co.oliverdelange.otterhopper.sprites.Tree;

import java.util.ArrayList;

import static uk.co.oliverdelange.otterhopper.sprites.Enemy.newEnemy;
import static uk.co.oliverdelange.otterhopper.sprites.Tree.newTree;

public class OtterHopperGame extends Activity {
    public static double delta;
    public static long timeSinceLastLoop;

    private boolean playingGame = true;
    private int highScore = 999; // TODO save game high score somehow
    private int score = 0;

    private Otter otter;
    private ArrayList<Tree> trees;
    private ArrayList<Enemy> enemies;

// IMPORTANT: Pattern is: onCreate, onStart, onResume, (Activity is now running), onPause, onStop, onDestroy.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Game Activity Created");

        setContentView(R.layout.activity_otter_hopper_game);
//        Intent intent = getIntent();
//        TODO for the future can pass stuff into game activity such as otter costumes and high scores etc

        newGame();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Game Activity Started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Game Activity Resumed");

        //TODO how to resume the game?
//        if (!playingGame) {
//            Button resume = new Button(this);
//            resume.setText("Resume!");
//            this.setContentView(resume);
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Game Activity Paused");
        playingGame = false;
    }

    public void newGame() {
        this.score = 0;
        this.otter = new Otter();
//        startGameLoop();
    }

    public void endGame() {
        if (score > highScore) {
            saveHighScore(score);
            highScore = score;
        }
        this.playingGame = false;
        displayMenu();
    }

    public void resumeGame() {
        System.out.println("Game resumed");
    }

    public void startGameLoop() {
        long lastLoopTime = System.nanoTime();
        final int targetFps = 60;
        final long optimalTime = 1000000000 / targetFps;
        long lastFpsTime = 0;
        int fps = 0;

        while (playingGame) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)optimalTime);
            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1000000000) {
                System.out.println("FPS: " + fps);
                lastFpsTime = 0;
                fps = 0;
            }

            updateGame();

            long sysTime = System.nanoTime();
            long sleepTime = (lastLoopTime - sysTime + optimalTime)/1000000;
            try {
                if (sleepTime < 0 ){
                    System.out.println("BadSleep: " + sleepTime + " LastLoop: " + lastLoopTime +
                            " NanoTime: " + sysTime +
                            " OptimalTime: " + optimalTime
                    );
                }
                else {
                    Thread.sleep(sleepTime); //TODO DESTROY ALL THREAD.SLEEPS
                }
            } catch (InterruptedException e) {
                System.out.println("Thread.sleep exception: " + e);
            }
        }
    }

    public void updateGame() {
        Background.update();

        if (playingGame) {
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

            otter.move();
        }
    }

    private void displayMenu() {
        //TODO
    }

    public void saveHighScore(int score) {
        //TODO figure out game save
    }
}
