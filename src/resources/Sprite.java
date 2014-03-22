package resources;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Sprite extends ImageIcon {

    private double speed = 1; 
    private double posX;
    private double posY;

    public int width;
    public int height;
    private BufferedImage img;

    public Sprite(BufferedImage i,int w, int h, double s) {
        img = i;
        setImage(img);
        height = (int) Math.round(h / s);
        width = (int) Math.round(w / s);
    }
    //Collision detection
    public boolean collides(int x, int y, int r, int b) {
        int adjX = 5;
        int adjY = 5;
        boolean result = !(
                r <= this.posX + adjX || 
                x > this.posX + this.width - adjX || 
                b <= this.posY + adjY || 
                y > this.posY + this.height - adjY
        );
        //System.out.println(result+ "  "+
//                "EnemyRight("+r+")<=PlayerLeft("+this.posX+") - "+
//                "EnemyLeft("+x+")>PlayerRight("+(this.posX + this.width)+") - "+
//                "EnemyBottom("+b+")<=PlayerTop("+this.posY+") - "+
//                "EnemyY("+y+")>PlayerBottom("+(this.posY + this.height)+") - "
//        );
        return result;
    }
    
    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getPosX() {
        return (int) Math.round(posX);
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return (int) Math.round(posY);
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}