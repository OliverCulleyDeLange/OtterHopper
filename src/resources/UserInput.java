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
    private Game g;
    
    public UserInput(Game game) {
        g = game;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.print(e.getKeyCode() + " / " + e.getKeyChar() + " Pressed");
            if (g.waitingForKeyPress) {
                g.waitingForKeyPress = false;
                g.inGame = true;
            }
            if (e.getKeyChar() == ' ') {
                if (g.r.player.getOnGround()) {
                    g.r.player.jump();
                }
            }
            if (e.getKeyChar() == 27) { // Escape key exits game
                g.inGame = false; 
                g.waitingForKeyPress = true;
                //System.exit(0);
            }
    }
}