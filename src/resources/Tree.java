package resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tree extends Sprite {

    public Tree(BufferedImage i,int w, int h) {
        super(i);
        Random r = new Random();
        int max = (int) Math.round(h*0.5);
        int min = (int) Math.round(h*0.4);
        this.setPosY(r.nextInt(max-min) + min);
        this.setPosX(w);
        this.setSpeed(0.75);
        this.setWidth(150);
        this.setHeight(200);
    }
}
