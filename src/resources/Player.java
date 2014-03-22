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
    private int groundCoordY;

    private double velY = 0; //Y Axis velocity
    private double[] velYMaxMin = {1.5,-2.5}; // Max and Min Velocity

    public int animFrame = 5;
    public long animTimer = 0;
<<<<<<< HEAD

    public Player(BufferedImage i,int w, int h, double s, int imgWidth, int imgHeight) {
        super(i, imgWidth,imgHeight, s);
        setPosX(w / 8);
        setPosY(h * 0.7);
        groundCoordY = getPosY();
=======
    public Player(BufferedImage i,int w, int h, double s, int imgWidth, int imgHeight) {
        super(i, imgWidth,imgHeight, s);
        this.setPosX(w/6);
        this.setPosY(h*0.75);
>>>>>>> 8649a03ad52fe9d2325eb5786149482051238bf8
    }
    public void updateAnimFrame(long time) {
        animTimer += time;
        if (animTimer > 100000000) {
            if (onGround) {
                if (animFrame == 0) {
                    animFrame = 5;
                } else {
                    animFrame--;
                }
                animTimer = 0;
            }
        }
    }
    public void jump() {
        setOnGround(false);
        velY = velYMaxMin[1];
    }
    public void setOnGround(boolean og){
        onGround = og;
    }
    public boolean getOnGround() {
        return onGround;
    }
    public void autoSetPosY(double d) { //delta
        if (onGround) {
            velY = 0;
        } else { //Jumping
            velY += 0.08;
            //System.out.println(velY + "  = VelY");
            if (velY >= velYMaxMin[0]) velY = velYMaxMin[0];
            if (velY <= velYMaxMin[1]) velY = velYMaxMin[1];
        }
        double newPos = getPosY() + (velY * 5);
        if (newPos >= groundCoordY) {
            newPos = groundCoordY;
            setOnGround(true);
            velY = 0;
        }
        //System.out.println("newPos = " + newPos);
        super.setPosY(newPos);
    }
}
