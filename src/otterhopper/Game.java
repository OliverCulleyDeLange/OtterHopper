package otterhopper;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import resources.*;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class Game extends JPanel {
    //Declare the game variables
    resources r = new resources();

    private boolean gameRunning = true;
    private boolean inGame = false; // In Menus or game? Default to Menus (false)
    public boolean loading = true;
    private int score = 0; // How many jumps did he make? ++1
    private int distance = 0; // How far did the otter get? 
    private long time = 0; // Time played for

    private long treeTimer = 0;
    private long fenceTimer = 0;

    public Game() {
        setBackground(Color.BLUE);
    }
    public boolean loadResources() {
        // Load all images from above array into r.images
        for (int i = 0; i < r.imagesToLoad.length; i++) {
            try {
                r.loadImage(r.imagesToLoad[i]);
                r.setLoadPercentageComplete((100 / r.imagesToLoad.length) * (i + 1));
                System.out.println("Loaded: " + r.getLoadPercentageComplete() + "%");
                r.lb.setValue(r.getLoadPercentageComplete());
            } catch (IOException e) {
                System.out.println(e);
                return false;
            }
        }
        return true;
    }
    public void newGame() {
        r.setScale( r.images.get(1).getHeight() / getHeight());
        //Initiate Background image
        r.bg = new Sprite(
                r.images.get(1),
                this.getWidth(), 
                this.getHeight(), 
                r.getScale()
        );
        //Initiate Player Image
        r.player = new Player(
                r.images.get(0), 
                this.getWidth(), 
                this.getHeight(),
                r.getScale(),
                r.images.get(1).getWidth(),
                r.images.get(1).getHeight()
        );
        //Sets off game loop
        System.out.println("Starting Game Loop");
        startGameLoop();
    }
    public void startGameLoop() {
        // 
        long lastLoopTime = System.nanoTime();
        final int targetFps = 60;
        final long optimalTime = 1000000000 / targetFps;
        long lastFpsTime = 0;
        int fps = 0;
        
        while (gameRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)optimalTime);
            
            lastFpsTime += updateLength;
            fps++;
            
            if (lastFpsTime >= 1000000000) {
                System.out.println("FPS: " + fps);
                lastFpsTime = 0;
                fps = 0;
            }
            
            updateGame(delta, updateLength);
            repaint();

            long sysTime = System.nanoTime();
            long sleepTime = (lastLoopTime-sysTime + optimalTime)/1000000;
            //System.out.println("SleepTime = " + sleepTime);
            //try {Thread.sleep((lastLoopTime-System.nanoTime() + optimalTime)/1000000 )}; 
            try {
                if (sleepTime < 0 ){
                    System.out.println("LastLoop: " + lastLoopTime +
                            " NanoTime: " + sysTime +
                            " OptimalTime: " + optimalTime
                    );
                }
                else {
                    Thread.sleep(sleepTime);
                }

            } catch (InterruptedException e) {
                System.out.println("Thread.sleep exception: " + e);
            }
        }
    }
    public void updateGame(double delta, long timeSinceLastLoop) {
        //Move Background slowly left
        r.bg.setPosX(r.bg.getPosX()+(r.bg.getSpeed()*delta));
        //Add tree's every now and then
        if (treeTimer > 5000000000l) {
            r.trees.add(new Tree(
                    r.images.get(2),
                    getWidth(),
                    getHeight(),
                    r.getScale(),
                    r.images.get(2).getWidth(),
                    r.images.get(2).getHeight()
                )
            );
            treeTimer = 0;
        } else {
            treeTimer += timeSinceLastLoop;
        }
        //Move trees left slowly
        for (Tree tree : r.trees) {
            tree.setPosX(tree.getPosX()-(tree.getSpeed()*delta));
        }
        //Update players animation cycle
        r.player.updateAnimFrame(timeSinceLastLoop);
    }

    public void paint(Graphics g) {
        if (loading) {
            //r.lb.setValue(r.getLoadPercentageComplete());
            //System.out.println("Painted Loading bar");
        } else {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            //Draw Background
            if (r.bg.getPosX() >= 1920) r.bg.setPosX(0);
            BufferedImage bgSub = r.bg.getImg().getSubimage(
                    r.bg.getPosX(),
                    0,
                    (int) Math.round(getWidth() * r.getScale()),
                    r.bg.getImg().getHeight()
            );
            g2d.drawImage(bgSub,0,0,getWidth(),getHeight(),this );
            //Draw Trees
            for (Tree tree : r.trees){
                g2d.drawImage(
                        tree.getImg(),
                        tree.getPosX(),
                        tree.getPosY(),
                        tree.getWidth(),
                        tree.getHeight(),
                        this
                );
            }
            //Draw Fences

            //Draw Player
            BufferedImage playerSub = r.player.getImg().getSubimage(
                    r.player.animFrame * (r.player.getIconWidth() / 6),//Decides which section of sprite map to get
                    0, //Always 0 as getting from top of image
                    r.player.getIconWidth() / 6,
                    r.player.getIconHeight()
            );
            g2d.drawImage(
                    playerSub,
                    r.player.getPosX(),
                    r.player.getPosY(),
                    r.player.width,
                    r.player.height,
                    this
            );
            //System.out.println("Painted Game");
        }
    }
}
