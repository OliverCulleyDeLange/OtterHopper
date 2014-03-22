package resources;


import java.awt.*;
import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

public class resources {
    //Paths
    final public String[] imagesToLoad = {
            "img/otter_strip.png",
            "img/landscape.png",
            "img/tree_trans.png",
            "img/clefable.png"
    };
    //Raw bufferedImages & Sounds
    public ArrayList<BufferedImage> images = new ArrayList();
    //public ArrayList<SoundOfSomeSort???> sounds = new ArrayList();
    //Sprites
    public Sprite bg;
    public ArrayList<Tree> trees = new ArrayList();
    public ArrayList<Enemy> enemies = new ArrayList();
    public ArrayList<Enemy> countedEnemies = new ArrayList();
    public Player player;
    //loadBar
    public loadBar lb = new loadBar();
    //Vars
    private int loadPercentageComplete = 0;
    private double scale;
    public Color menuOverlayBg = new Color(140, 150, 150, 150 );

    public void loadImage(String imgPath) throws IOException {
        try {
            //BufferedImage img = ImageIO.read(new File(imgPath));l
            InputStream is = ClassLoader.getSystemResourceAsStream(imgPath);
            //ImageInputStream iis = ImageIO.createImageInputStream(imgPath);
            BufferedImage img = ImageIO.read(is);
            images.add(img);
        } catch (IOException e) {
            throw new IOException("2-Can't read file: " + imgPath);
        }
    }


    public void setLoadPercentageComplete(int loadPercentageComplete) {
        this.loadPercentageComplete = loadPercentageComplete;
    }
    public int getLoadPercentageComplete() {
        return loadPercentageComplete;
    }
    public void setScale(double s) {
        scale = s;
    }
    public double getScale() {
        return scale;
    }
}
