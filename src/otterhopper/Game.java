package otterhopper;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import resources.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel {
    //Declare the game variables
    public resources r = new resources();
    
    private boolean gameRunning = true; // Controlls game loop
    public boolean inGame = false; // In Menus or game? Default to Menus (false)
    public boolean loading = true; // Controls drawing
    private int score = 0; // How many jumps did he make? ++1
    private int difficulty = 100;
    private long treeTimer = 1;
    private long enemyTimer = 1;
    public boolean waitingForKeyPress = true;
    
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
<<<<<<< HEAD
        //System.out.println("Scale = " + r.getScale());
=======
>>>>>>> 8649a03ad52fe9d2325eb5786149482051238bf8
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
<<<<<<< HEAD
                r.images.get(0).getWidth()/6,
                r.images.get(0).getHeight()
=======
                r.images.get(1).getWidth(),
                r.images.get(1).getHeight()
>>>>>>> 8649a03ad52fe9d2325eb5786149482051238bf8
        );
        //Sets off game loop
        //System.out.println("Starting Game Loop");
        startGameLoop();
    }
    public void endGame() {
        this.inGame = false;
        this.waitingForKeyPress = true;
        this.difficulty = 100;
        this.score = 0;
        this.r.enemies.clear();
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
<<<<<<< HEAD
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
=======
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
>>>>>>> 8649a03ad52fe9d2325eb5786149482051238bf8
        }
        
        //Move trees left slowly & remove if off screen
        ArrayList<Sprite> tmpRemovals = new ArrayList();
        for (Tree tree : r.trees) {
            tree.setPosX(tree.getPosX()-(tree.getSpeed()*delta));
            if (tree.getPosX() < 0 - tree.width) {
                tmpRemovals.add(tree);
            }
        } 
        for (Sprite i : tmpRemovals) {
            r.trees.remove((Tree) i);
        }
        
        tmpRemovals.clear(); // Clear out removal array
        
        //Move enemies left & remove if off screen
        for (Enemy enemy : r.enemies) {
            enemy.setPosX(enemy.getPosX()-(enemy.getSpeed()*delta));
            if (enemy.getPosX()+enemy.getWidth() / 2 < r.player.getPosX()+r.player.getWidth() / 2 &&
                    !r.countedEnemies.contains(enemy)) {
                    r.countedEnemies.add(enemy); // add enemy to countedEnemies so doesn't increase score next loop
                    score++; // Score point
            }
            if (enemy.getPosX() < 0 - enemy.width) {
                tmpRemovals.add(enemy);
            }
        }
        for (Sprite i : tmpRemovals) {
            r.enemies.remove((Enemy) i);
        }        
        //Update Otter Y pos
        r.player.autoSetPosY(delta);
        //Update Otter animation cycle
        r.player.updateAnimFrame(timeSinceLastLoop);
        //Player -> enemies collision detection
        if (r.enemies.size() > 0 ){
            for (Enemy e : r.enemies) {
                //                int x, int y, int r, int b
                if (r.player.collides(e.getPosX(), e.getPosY(), 
                    e.getPosX() + e.width, e.getPosY() + e.height)) {
                    System.out.println("Collision");
                    endGame();
                    break;
                }
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
            //Draw Enemies
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
            g2d.setFont(new Font("Helvetica", Font.PLAIN, 50));
            if (!inGame) {
                g2d.setColor(r.menuOverlayBg);
                g2d.fillRect(0 , 0 , getWidth(), getHeight());
                g2d.setColor(Color.ORANGE);
                g2d.drawString("Press any key to start game", 50, 50);
                g2d.drawString("Press ESC to pause / cheat", 50, 100);
            }
            g2d.setColor(Color.ORANGE);
            g2d.drawString(new Integer(score).toString(), (int) (this.getWidth()*0.9), 50);
        }
    }
}
