package uk.co.oliverdelange.otterhopper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class OtterHopperMenu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_otter_hopper_menu);

//        final View controlsView = findViewById(R.id.fullscreen_content_controls);
//        final View contentView = findViewById(R.id.fullscreen_content);
    }

    public void startGame(View view) {
        System.out.println("Hop Button Pressed to start game!");

        Intent intent = new Intent(this, OtterHopperGame.class);
        startActivity(intent);
    }
}
