package uk.co.oliverdelange.otterhopper.framework;

import android.content.res.AssetManager;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint.Style;

import java.io.IOException;
import java.io.InputStream;


public class Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public Graphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    public static enum ImageFormat {
        ARGB8888, ARGB4444, RGB565
    }

    public AndroidImage newImage(String fileName, ImageFormat format) {
        Config config = null;
        if (format == ImageFormat.RGB565)
            config = Config.RGB_565;
        else if (format == ImageFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;


        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = ImageFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = ImageFormat.ARGB4444;
        else
            format = ImageFormat.ARGB8888;

        return new AndroidImage(bitmap, format);
    }

    public void clearScreen(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }

    public void drawRectVerticalGradient(int x, int y, int width, int height, int gradientColour1, int gradientColour2) {
        paint.setShader(new LinearGradient(x, y, x, y+height, gradientColour1, gradientColour2, Shader.TileMode.MIRROR));
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
        paint.setShader(null);
    }

    public void drawShape(int x, int y, int width, int height) {
        paint.setShader(new LinearGradient(0,0, 0, height, Color.LTGRAY, Color.WHITE, Shader.TileMode.MIRROR));
        canvas.drawRect(x, y, x + width, y + height, paint);
        Path path = new Path();
//        path.
//        canvas.drawPath(path, paint);
    }

    public void drawARGB(int a, int r, int g, int b) {
        paint.setStyle(Style.FILL);
        canvas.drawARGB(a, r, g, b);
    }

    public void drawString(String text, int x, int y, Paint paint){
        paint.setTextSize(75f);
        paint.setColor(Color.rgb(174, 164, 164));
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setAntiAlias(true);
        canvas.drawText(text, x, y, paint);
    }

    public void drawImage(AndroidImage androidImage, int x, int y, int srcX, int srcY,
                          int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;


        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;

        canvas.drawBitmap(androidImage.bitmap, srcRect, dstRect,
                null);
    }

    public void drawAndroidImage(AndroidImage image, int x, int y) {
        canvas.drawBitmap(image.bitmap, x, y, null);
    }

    public void drawScaledImage(AndroidImage image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight, int angle){

        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + width;
        dstRect.bottom = y + height;

        canvas.save();
        canvas.rotate(angle, x + (width/2), y + (height/2));
        canvas.drawBitmap(image.bitmap, srcRect, dstRect, null);
        canvas.restore();
    }

    public int getWidth() {
        return frameBuffer.getWidth();
    }

    public int getHeight() {
        return frameBuffer.getHeight();
    }
}