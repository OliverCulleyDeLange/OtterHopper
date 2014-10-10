package uk.co.oliverdelange.otterhopper.framework;

import android.graphics.Bitmap;
import uk.co.oliverdelange.otterhopper.framework.Graphics.ImageFormat;

public class AndroidImage{
    Bitmap bitmap;
    Graphics.ImageFormat format;

    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public ImageFormat getFormat() {
        return format;
    }

    public void dispose() {
        bitmap.recycle();
    }
}