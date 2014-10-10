package uk.co.oliverdelange.otterhopper.framework;

import android.view.View;

import java.util.List;

import static uk.co.oliverdelange.otterhopper.framework.AndroidInput.TouchEvent;

public interface TouchHandler extends View.OnTouchListener {
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}