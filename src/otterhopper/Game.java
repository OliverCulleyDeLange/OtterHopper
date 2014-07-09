package otterhopper;

import java.awt.*;
import resources.*;

import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel {
    public resources r = new resources();
    
    private boolean gameRunning = true; // Controls game loop
    private boolean inGame = false; // In Menus or game? Default to Menus (false)

    private int score = 0;
    private int highScore;
    private String highScoreFile = "highScore.txt";
    private boolean highScoreNotification = false;

    private int difficulty = 100;

    private long treeTimer = 1;
    private long enemyTimer = 1;
    private Random rnd = new Random();
    private long treeRnd = 50;
    private long enemyRnd = 50;

    public void loadResources() throws IOException {
        // Load all images from above array into r.images
        for (int i = 0; i < r.getImagesToLoad().length; i++) {
            r.loadImage(r.getImagesToLoad()[i]);
            r.setLoadPercentageComplete((100 / r.getImagesToLoad().length) * (i + 1));
            r.getLb().setValue(r.getLoadPercentageComplete());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Sleep Exception error in Load Resources:");
                e.printStackTrace();
            }
        }
    }
    public void newGame() {
        readHighScore();
        r.setScale( r.getImages().get(1).getHeight() / getHeight());
        //System.out.println("Scale = " + r.getScale());
        //Initiate Background image
        r.setBg(new Sprite(
            r.getImages().get(1),
            this.getWidth(),
            this.getHeight(),
            r.getScale()
            )
        );
        //Initiate Player Image
        r.setPlayer(new Player(
            r.getImages().get(0),
            this.getWidth(),
            this.getHeight(),
            r.getScale(),
            r.getImages().get(0).getWidth()/6,
            r.getImages().get(0).getHeight()
            )
        );
        //Sets off game loop
        //System.out.println("Starting Game Loop");
        startGameLoop();
    }
    public void endGame() {
        if (score > getHighScore()) {
            saveHighScore(score);
            highScore = score;
            highScoreNotification = true;
        }
        
        this.inGame = false;
        this.difficulty = 100;
        this.score = 0;
        this.r.getEnemies().clear();
    }
    public void readHighScore() {
        try (FileReader fr = new FileReader(highScoreFile)) {
            StringBuilder sb = new StringBuilder();
            int tmp = fr.read();
            while (tmp != -1) {
                sb.append((char) tmp);
                tmp = fr.read();
            }
            highScore = new Integer(sb.toString());
        } catch (IOException e) {
           System.out.println("File Not Found: " + e);
        }
    }
    public void saveHighScore(int Score) {
        try {
            PrintWriter pr = new PrintWriter(highScoreFile);    
            pr.print(Score);
            pr.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("No such file exists.");
        }
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
                System.out.println("FPS: " + fps);
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
        r.getBg().setPosX(r.getBg().getPosX() + (r.getBg().getSpeed() * delta));
        if (r.getBg().getPosX() >= 2308) r.getBg().setPosX(0);
        // Only add trees and enemies if in game
        if (inGame) {
           //System.out.println("treeRnd = " + treeRnd);
           if (treeTimer > treeRnd * 100000000) {
               //System.out.println(treeTimer + "(treeTimer) > " + treeRnd + "(treeRnd)");
                r.getTrees().add(new Tree(
                                r.getImages().get(2),
                                getWidth(),
                                getHeight(),
                                r.getScale(),
                                r.getImages().get(2).getWidth(),
                                r.getImages().get(2).getHeight()
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
                r.getEnemies().add(new Enemy(
                                r.getImages().get(3),
                                getWidth(),
                                getHeight(),
                                r.getScale(),
                                r.getImages().get(3).getWidth(),
                                r.getImages().get(3).getHeight()
                        )
                );
                enemyTimer = 0;
                enemyRnd = rnd.nextInt(50) + difficulty;
                difficulty--;
            } else {
                enemyTimer += timeSinceLastLoop * delta;
            }
        }
        
        //Move trees left slowly & remove if off screen
        ArrayList<Sprite> tmpRemovals = new ArrayList<>();
        for (Tree tree : r.getTrees()) {
            tree.setPosX(tree.getPosX()-(tree.getSpeed()*delta));
            if (tree.getPosX() < 0 - tree.getWidth()) {
                tmpRemovals.add(tree);
            }
        } 
        for (Sprite i : tmpRemovals) {
            r.getTrees().remove(i);
        }
        
        tmpRemovals.clear(); // Clear out removal array
        
        //Move enemies left & remove if off screen
        for (Enemy enemy : r.getEnemies()) {
            enemy.setPosX(enemy.getPosX()-(enemy.getSpeed()*delta));
            if (enemy.getPosX()+enemy.getWidth() / 2 < r.getPlayer().getPosX()+r.getPlayer().getWidth() / 2 &&
                    !r.getCountedEnemies().contains(enemy)) {
                    r.getCountedEnemies().add(enemy); // add enemy to countedEnemies so doesn't increase score next loop
                    score++; // Score point
            }
            if (enemy.getPosX() < 0 - enemy.getWidth()) {
                tmpRemovals.add(enemy);
            }
        }
        for (Sprite i : tmpRemovals) {
            r.getEnemies().remove(i);
        }        
        //Update Otter Y pos
        r.getPlayer().autoSetPosY(delta);
        //Update Otter animation cycle
        r.getPlayer().updateAnimFrame(timeSinceLastLoop);
        //Player -> enemies collision detection
        if (r.getEnemies().size() > 0 ){
            for (Enemy e : r.getEnemies()) {
                //                int x, int y, int r, int b
                if (r.getPlayer().collides(e.getPosX(), e.getPosY(),
                        e.getPosX() + e.getWidth(), e.getPosY() + e.getHeight())) {
                    //System.out.println("Collision");
                    endGame();
                    break;
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        if (r.getImages().size() == r.getImagesToLoad().length) {
            super.paint(g);
            //Draw Background
            BufferedImage bgSub = r.getBg().getImg().getSubimage(
                    r.getBg().getPosX(),
                    0,
                    (int) Math.round(getWidth() * r.getScale()),
                    r.getBg().getImg().getHeight()
            );
            g2d.drawImage(bgSub,0,0,getWidth(),getHeight(),this );
            //Draw Trees
            for (Tree tree : r.getTrees()){
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
            for (Enemy enemy : r.getEnemies()){
                g2d.drawImage(
                        enemy.getImg(),
                        enemy.getPosX(),
                        enemy.getPosY(),
                        enemy.getWidth(),
                        enemy.getHeight(),
                        this
                );
            }
            //Draw Player
            BufferedImage playerSub = r.getPlayer().getImg().getSubimage(
                    r.getPlayer().getAnimFrame() * (r.getPlayer().getIconWidth() / 6),//Decides which section of sprite map to get
                    0, //Always 0 as getting from top of image
                    r.getPlayer().getIconWidth() / 6,
                    r.getPlayer().getIconHeight()
            );
            g2d.drawImage(
                    playerSub,
                    r.getPlayer().getPosX(),
                    r.getPlayer().getPosY(),
                    r.getPlayer().getWidth(),
                    r.getPlayer().getHeight(),
                    this
            );
            //Draw menu overlay if in menu... Obvious really!?
            g2d.setFont(new Font("Helvetica", Font.PLAIN, 35));
            if (!inGame) {
                g2d.setColor(r.getMenuOverlayBg());
                g2d.fillRect(0 , 0 , getWidth(), getHeight());
                g2d.setColor(Color.ORANGE);
                g2d.drawString("Press ENTER to start game", 50, 50);
                
                if (highScoreNotification) {
                    g2d.drawString("Congratulations, you got a new high score: " + highScore, 50, (this.getHeight() /2));
                } else {
                    g2d.drawString("Current High Score is: " + highScore, 50, 100);
                }
            } else { // Draw current score in Game
                g2d.setColor(Color.ORANGE);
                g2d.drawString("Score: " + Integer.toString(score), (int) (this.getWidth()*0.1), 50);
            }
        }
    }
    public int getHighScore() {
        return highScore;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public boolean isHighScoreNotification() {
        return highScoreNotification;
    }

    public void setHighScoreNotification(boolean highScoreNotification) {
        this.highScoreNotification = highScoreNotification;
    }


}
