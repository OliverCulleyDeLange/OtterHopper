package otterhopper;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import resources.UserInput;

public class Main {
    static JFrame frame;
    static Game game = new Game();
    static public UserInput k  = new UserInput(game);

    static final public int height = 1020/2;
    static final public int width = 1920/2;

    public static void main(String[] args) {
        Runnable jFrameSetUp = new Runnable() {
            public void run() {
                frame = new JFrame("OtterHopper"); // Create Windows for game
                frame.setPreferredSize(new Dimension(width, height));
                frame.setResizable(true);
                frame.setBackground(Color.BLACK);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setContentPane(game);
                if (!frame.isFocusable()) {
                    frame.setFocusable(true);
                }
                frame.addKeyListener(k);
                frame.setFocusTraversalKeysEnabled(false);
                frame.pack();
                frame.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(jFrameSetUp);

        try {// Try to load resources
            game.add(game.r.getLb(), BorderLayout.PAGE_END);
            game.loadResources();
            game.remove(game.r.getLb());
        } catch (IOException e) { // Exit if error
            System.out.println("Error loading resources:");
            e.printStackTrace();
            System.exit(0);
        }
        game.newGame();
    }  
}
