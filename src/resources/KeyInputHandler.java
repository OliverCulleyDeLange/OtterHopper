/*
 * -.- OCD -.-
 */

package resources;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

    public class KeyInputHandler extends KeyAdapter {
        private boolean waitingForKeyPress = true; // At start and end of game = press any key to start game
        private boolean playerJump = false; // True if player pressed jump button
        private int pressCount = 1;

        @Override
        public void keyPressed(KeyEvent e) {
                if (waitingForKeyPress) {
                        return;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        playerJump = true;
                }
        } 
        @Override
        public void keyReleased(KeyEvent e) {
                // if we're waiting for an "any key" typed then we don't 
                // want to do anything with just a "released"
                if (waitingForKeyPress) {
                        return;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        playerJump = false;
                }
        }
        @Override
        public void keyTyped(KeyEvent e) {
                if (waitingForKeyPress) {
                        if (pressCount == 1) {
                                waitingForKeyPress = false;
                                // Set inGame = True to start game
                                //inGame = true;
                                pressCount = 0;
                        } else {
                                pressCount++;
                        }
                }
                if (e.getKeyChar() == 27) { // Escape key exits game
                        System.exit(0);
                }
        }
    }
