package resources;


import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

public class resources {
    //Paths
    final private String[] imagesToLoad = {
            "img/otter_strip.png",
            "img/landscape.png",
            "img/tree_trans.png",
            "img/clefable.png"
    };
    //Raw bufferedImages & Sounds
    private ArrayList<BufferedImage> images = new ArrayList<>();
    //private ArrayList<SoundOfSomeSort???> sounds = new ArrayList<>();
    //Sprites
    private Sprite bg;
    private ArrayList<Tree> trees = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Enemy> countedEnemies = new ArrayList<>();
    private Player player;
    //loadBar
    private loadBar lb = new loadBar();
    //Vars
    private int loadPercentageComplete = 0;
    private double scale;
    private Color menuOverlayBg = new Color(140, 150, 150, 150 );

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

    public Color getMenuOverlayBg() {
        return menuOverlayBg;
    }

    public void setMenuOverlayBg(Color menuOverlayBg) {
        this.menuOverlayBg = menuOverlayBg;
    }

    public ArrayList<BufferedImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<BufferedImage> images) {
        this.images = images;
    }

    public Sprite getBg() {
        return bg;
    }

    public void setBg(Sprite bg) {
        this.bg = bg;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public void setTrees(ArrayList<Tree> trees) {
        this.trees = trees;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Enemy> getCountedEnemies() {
        return countedEnemies;
    }

    public void setCountedEnemies(ArrayList<Enemy> countedEnemies) {
        this.countedEnemies = countedEnemies;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public loadBar getLb() {
        return lb;
    }

    public void setLb(loadBar lb) {
        this.lb = lb;
    }

    public int getLoadPercentageComplete() {
        return loadPercentageComplete;
    }

    public void setLoadPercentageComplete(int loadPercentageComplete) {
        this.loadPercentageComplete = loadPercentageComplete;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public String[] getImagesToLoad() {
        return imagesToLoad;
    }
}
