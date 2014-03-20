package resources;

import javax.swing.JProgressBar ;
        
public class loadBar extends JProgressBar{

    public loadBar() {
        super(0,100); // Creates JProgressBar with min 0 and max 100
        setValue(0);
        setStringPainted(true);
    }

}
