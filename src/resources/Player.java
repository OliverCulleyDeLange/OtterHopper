/*
 * -.- OCD -.-
 */

package resources;

import java.awt.image.BufferedImage;

/**
 *
 * @author OCulley1
 */
public class Player extends Sprite {
    private boolean onGround = true; // Is sprite
    public int animFrame = 5;
    public long animTimer = 0;
    public Player(BufferedImage i,int w, int h, double s, int imgWidth, int imgHeight) {
        super(i, imgWidth,imgHeight, s);
        this.setPosX(w/6);
        this.setPosY(h*0.75);
    }
    public void updateAnimFrame(long time) {
        animTimer += time;
        if (animTimer > 100000000) {
            //System.out.println(animTimer);
            if (animFrame == 0) {
                animFrame = 5;
            } else {
                animFrame--;
            }
            animTimer = 0;
        }
    }
    
}
