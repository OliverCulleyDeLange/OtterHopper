package otterhopper;

import javax.swing.*;
import java.awt.*;
import resources.UserInput;

public class Main {
    static JFrame frame;
    static Game game = new Game();

    static public int height = 1020/2;
    static public int width = 1920/2;
    static public UserInput k;
    
    public static void main(String[] args) {
        //Frame setup
        System.out.println("FrameSetup Start");
        frame = new JFrame("OtterHopper"); // Create Windows for game
        frame.setPreferredSize(new Dimension(width, height));
        frame.setResizable(true);
        frame.setBackground(Color.BLACK);
        //frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Game setup
        frame.setContentPane(game);
        //Pack window
        frame.pack();
        frame.setVisible(true);
        System.out.println("FrameSetup Finish");

        //If resources load successfully - start game loop
        System.out.println("LoadResources Start");
        game.add(game.r.lb, BorderLayout.PAGE_END);
        if (game.loadResources()) { // Resources loaded if true
            game.loading = false;
            game.remove(game.r.lb);
            System.out.println("LoadResources Finish");
            //Sets up and starts game
            System.out.println("NewGame Start");
            
            k  = new UserInput(game);
            if (!frame.isFocusable()) {
                frame.setFocusable(true);
            }
            frame.addKeyListener(k);
            frame.setFocusTraversalKeysEnabled(false);

            game.newGame();
            System.out.println("NewGame Finish");
            //System.exit(0);
        }
        else { // Else Error message
            System.out.println("Error loading resources");
            System.exit(0);
        }
    }  
}
