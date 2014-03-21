package otterhopper;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import resources.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel {
    //Declare the game variables
    public resources r = new resources();
    
    private boolean gameRunning = true; // Controlls game loop
    public boolean inGame = false; // In Menus or game? Default to Menus (false)
    public boolean loading = true; // Controls drawing
    private int score = 0; // How many jumps did he make? ++1
    private int distance = 0; // How far did the otter get? 
    private long time = 0; // Time played for
    private int difficulty = 100;
    private long treeTimer = 1;
    private long enemyTimer = 1;
    
    public  Random rnd = new Random();
    public long treeRnd = 50;
    public long enemyRnd = 50;
    
    public Game() {
        setBackground(Color.BLUE);
    }
    public boolean loadResources() {
        // Load all images from above array into r.images
        for (int i = 0; i < r.imagesToLoad.length; i++) {
            try {
                r.loadImage(r.imagesToLoad[i]);
                r.setLoadPercentageComplete((100 / r.imagesToLoad.length) * (i + 1));
                //System.out.println("Loaded: " + r.getLoadPercentageComplete() + "%");
                r.lb.setValue(r.getLoadPercentageComplete());
            } catch (IOException e) {
                //System.out.println(e);
                return false;
            }
        }
        return true;
    }
    public void newGame() {
        r.setScale( r.images.get(1).getHeight() / getHeight());
        //System.out.println("Scale = " + r.getScale());
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
                r.images.get(0).getWidth()/6,
                r.images.get(0).getHeight()
        );
        //Sets off game loop
        //System.out.println("Starting Game Loop");
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
            //System.out.print(" Delta=" + delta);
            lastFpsTime += updateLength;
            fps++;
            
            if (lastFpsTime >= 1000000000) {
                //System.out.println("FPS: " + fps);
                lastFpsTime = 0;
                fps = 0;
            }
            
            updateGame(delta, updateLength);
            repaint();

            long sysTime = System.nanoTime();
            long sleepTime = (lastLoopTime - sysTime + optimalTime)/1000000;
            //System.out.println("SleepTime = " + sleepTime);
            //try {Thread.sleep((lastLoopTime-System.nanoTime() + optimalTime)/1000000 )}; 
            try {
                if (sleepTime < 0 ){
                    System.out.println("BadSleep: " + sleepTime + " LastLoop: " + lastLoopTime +
                            " NanoTime: " + sysTime +
                            " OptimalTime: " + optimalTime
                    );
                }
                else {
                    //System.out.println("Sleep: " + sleepTime);
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
        // Only add trees and enemies if in game
        if (inGame) {
            //Add tree's every now and then
            //TODO
           //System.out.println("treeRnd = " + treeRnd);
           if (treeTimer > treeRnd * 100000000) {
               //System.out.println(treeTimer + "(treeTimer) > " + treeRnd + "(treeRnd)");
                r.trees.add(new Tree(
                       r.images.get(2),
                       getWidth(),
                       getHeight(),
                       r.getScale(),
                       r.images.get(2).getWidth(),
                       r.images.get(2).getHeight()
                   )
               );
               treeTimer = 1;
               treeRnd = rnd.nextInt(20) + 30;
           } else {
               treeTimer += timeSinceLastLoop * delta;
           }   
           //Add enemies randomly
            if (enemyTimer > enemyRnd * 10000000) {
                //System.out.println(enemyTimer + "(enemyTimer) > " + enemyRnd + "(enemyRnd)");
                r.enemies.add(new Enemy(
                        r.images.get(3),
                        getWidth(),
                        getHeight(),
                        r.getScale(),
                        r.images.get(3).getWidth(),
                        r.images.get(3).getHeight()
                )
                );
                enemyTimer = 0;
                enemyRnd = rnd.nextInt(100) + difficulty;
                difficulty--;
            } else {
                enemyTimer += timeSinceLastLoop * delta;
            }
        }
        
        //Move trees left slowly & remove if off screen
        ArrayList<Integer> tmpRemovals = new ArrayList();
        for (Tree tree : r.trees) {
            tree.setPosX(tree.getPosX()-(tree.getSpeed()*delta));
            if (tree.getPosX() < 0 - tree.width) {
                tmpRemovals.add(r.trees.indexOf(tree));
            }
        } 
        for (Integer i : tmpRemovals) {
            r.trees.remove((int) i);
        }
        
        tmpRemovals.clear(); // Clear out removal array
        
        //Move enemies left & remove if off screen
        for (Enemy enemy : r.enemies) {
            enemy.setPosX(enemy.getPosX()-(enemy.getSpeed()*delta));
            if (enemy.getPosX() < 0 - enemy.width) {
                tmpRemovals.add(r.enemies.indexOf(enemy));
            }
        }
        for (Integer i : tmpRemovals) {
            r.enemies.remove((int) i);
        }        
        //Update Otter Y pos
        r.player.autoSetPosY(delta);
        //Update Otter animation cycle
        r.player.updateAnimFrame(timeSinceLastLoop);
        //Player -> enemies collision detection
        for (Enemy e : r.enemies) {
            //                int x, int y, int r, int b
            if (r.player.collides(e.getPosX(), e.getPosY(), e.width, e.height)) {
                System.out.println("Collision-> " + e);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        if (r.images.size() == r.imagesToLoad.length) {
            super.paint(g);
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
            for (Enemy fence : r.enemies){
                g2d.drawImage(
                        fence.getImg(),
                        fence.getPosX(),
                        fence.getPosY(),
                        fence.getWidth(),
                        fence.getHeight(),
                        this
                );
            }
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
            //Draw menu overlay if in menu... Obvious really!?
            if (!inGame) {
                g2d.setColor(r.menuOverlayBg);
                g2d.fillRect(0 , 0 , getWidth(), getHeight());
            }
        }
    }
}
