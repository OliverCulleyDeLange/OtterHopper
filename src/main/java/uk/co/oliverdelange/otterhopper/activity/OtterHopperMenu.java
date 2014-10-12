package uk.co.oliverdelange.otterhopper.activity;

import android.app.Activity;
import android.os.Bundle;
import uk.co.oliverdelange.otterhopper.R;


public class OtterHopperMenu extends Activity {

    // TODO This will become the translucent overlay menu that will appear when game is not running.
    // It will slide up and disappear when game starts
    //   ____________________
    //  | -----------------  |
    //  | |HS: 10   |F | ♫ | | Music Note is a mute button so mute ALL sounds
    //  | |►        |G+|♥ | | Heart is a rate link that lets users rate app through gogle play
    //  |  ----------------  |
    //  |____________________|
    // Will contain:  High score, Play button, Mute button, Rate button
    // Possibly contain Facebook and G+ buttons to sign in to view friends high scores -Require backend work and social integration + web service?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otter_hopper_menu);
    }
}
