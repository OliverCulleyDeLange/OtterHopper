/*
 * -.- OCD -.-
 */

package resources;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import otterhopper.Game;

/**
 *
 * @author OCulley1
 */
public class UserInput extends KeyAdapter{
    private boolean waitingForKeyPress = true;
    private int pressCount = 1;
    private Game g;
    
    public UserInput(Game game) {
        g = game;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.print(e.getKeyCode() + " / " + e.getKeyChar() + " Pressed");
            if (waitingForKeyPress) {
                    if (pressCount == 1) {
                            waitingForKeyPress = false;
                            // Set inGame = True to start game
                            g.inGame = true;
                            pressCount = 0;
                    } else {
                            pressCount++;
                    }
            }
            if (e.getKeyChar() == ' ') {
                if (g.r.player.getOnGround()) {
                    g.r.player.jump();
                }

            }
            if (e.getKeyChar() == 27) { // Escape key exits game
                g.inGame = false; 
                waitingForKeyPress = true;
                //System.exit(0);
            }
    }
}