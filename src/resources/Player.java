package resources;

import java.awt.image.BufferedImage;


public class Player extends Sprite {
    private boolean onGround = true; // Is sprite
    private int groundCoordY;

    private double velY = 0; //Y Axis velocity
    private double[] velYMaxMin = {1.5,-2.5}; // Max and Min Velocity

    private int animFrame = 5;
    private long animTimer = 0;

    public Player(BufferedImage i,int w, int h, double s, int imgWidth, int imgHeight) {
        super(i, imgWidth,imgHeight, s);
        setPosX(w / 8);
        setPosY(h * 0.7);
        groundCoordY = getPosY();
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
    public void autoSetPosY(double d) { //delta
        if (onGround) {
            velY = 0;
        } else { //Jumping
            velY += (0.08 * d);
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

    public boolean getOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public int getGroundCoordY() {
        return groundCoordY;
    }

    public void setGroundCoordY(int groundCoordY) {
        this.groundCoordY = groundCoordY;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double[] getVelYMaxMin() {
        return velYMaxMin;
    }

    public void setVelYMaxMin(double[] velYMaxMin) {
        this.velYMaxMin = velYMaxMin;
    }

    public int getAnimFrame() {
        return animFrame;
    }

    public void setAnimFrame(int animFrame) {
        this.animFrame = animFrame;
    }

    public long getAnimTimer() {
        return animTimer;
    }

    public void setAnimTimer(long animTimer) {
        this.animTimer = animTimer;
    }
}