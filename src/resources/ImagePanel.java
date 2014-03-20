/*
 * -.- OCD -.-
 */

package resources;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    BufferedImage bi;
    public ImagePanel(BufferedImage img) {
        bi = img;
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bi, 0, 0, null); // see javadoc for more info on the parameters            
    }

}
