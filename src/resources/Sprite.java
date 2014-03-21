package resources;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite extends ImageIcon {

    private double speed = 0.5; // 0 = not set | 1 - 99 speed range
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