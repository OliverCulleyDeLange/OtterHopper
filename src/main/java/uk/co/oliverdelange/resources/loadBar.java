package uk.co.oliverdelange.resources;

import javax.swing.JProgressBar ;
        
public class LoadBar extends JProgressBar{

    public LoadBar() {
        super(0,100); // Creates JProgressBar with min 0 and max 100
        setValue(0);
        setStringPainted(true);
    }

}
