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
            if (e.getKeyChar() == KeyEvent.VK_ENTER) { //Press enter
                g.setInGame(true);
                g.setHighScoreNotification(false);
            }
            if (e.getKeyChar() == ' ') {
                if (g.r.getPlayer().getOnGround()) {
                    g.r.getPlayer().jump();
                }
            }
            if (e.getKeyChar() == 27) { // Escape key exits game
                //g.endGame();
                System.exit(0);
            }
    }
}