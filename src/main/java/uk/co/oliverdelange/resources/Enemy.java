/*
 * -.- OCD -.-
 */

package uk.co.oliverdelange.resources;

import java.awt.image.BufferedImage;

/**
 *
 * @author OCulley1
 */
public class Enemy extends Sprite{
    public Enemy(BufferedImage i,int w, int h, double s, int imgWidth, int imgHeight) {
        super(i, imgWidth,imgHeight, s);
        this.setPosY(h * 0.7);
        this.setPosX(w);
        this.setSpeed(5);
    }
    
    
}
