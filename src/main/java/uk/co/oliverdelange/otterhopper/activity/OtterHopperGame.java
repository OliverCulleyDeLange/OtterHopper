package uk.co.oliverdelange.otterhopper.activity;

import android.os.Bundle;
import uk.co.oliverdelange.otterhopper.framework.AndroidGame;
import uk.co.oliverdelange.otterhopper.framework.Screen;
import uk.co.oliverdelange.otterhopper.screen.LoadingScreen;

public class OtterHopperGame extends AndroidGame {


    @Override
    public Screen getInitScreen() {
        return new LoadingScreen(this);
    }

// IMPORTANT: Pattern is: onCreate, onStart, onResume, (Activity is now running), onPause, onStop, onDestroy.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Game Activity Created");

//        setContentView(R.layout.activity_otter_hopper_game);
//        Intent intent = getIntent();
//        TODO for the future can pass stuff into game activity such as otter costumes and high scores etc
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Game Activity Started");
    }

    @Override
    public void onResume() {
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
    public void onPause() {
        super.onPause();
        System.out.println("Game Activity Paused");
    }

    @Override
    public void onStop() {
        super.onPause();
        System.out.println("Game Activity Stopped");
    }

    @Override
    public void onDestroy() {
        super.onPause();
        System.out.println("Game Activity Destroy");
    }

    @Override
    public void onBackPressed() {
        getCurrentScreen().backButton();
    }
}
