package uk.co.oliverdelange.otterhopper.framework;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public abstract class AndroidGame extends Activity implements Game {
    AndroidView renderView;
    Graphics graphics;
    AndroidInput input;
    Screen screen;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
            int frameBufferWidth = isPortrait ? 800: 1280;
            int frameBufferHeight = isPortrait ? 1280: 800;
            Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                    frameBufferHeight, Bitmap.Config.RGB_565);

            float scaleX = (float) frameBufferWidth
                    / getWindowManager().getDefaultDisplay().getWidth();
            float scaleY = (float) frameBufferHeight
                    / getWindowManager().getDefaultDisplay().getHeight();

            renderView = new AndroidView(this, frameBuffer);
            graphics = new Graphics(getAssets(), frameBuffer);
            input = new AndroidInput(this, renderView, scaleX, scaleY);
            screen = getInitScreen();
            setContentView(renderView);

        }

        @Override
        public void onResume() {
            super.onResume();
            screen.resume();
            renderView.resume();
        }

        @Override
        public void onPause() {
            super.onPause();
            renderView.pause();
            screen.pause();

            if (isFinishing())
                screen.dispose();
        }

        public AndroidInput getInput() {
            return input;
        }

        public Graphics getGraphics() {
            return graphics;
        }

        public void setScreen(Screen screen) {
            if (screen == null)
                throw new IllegalArgumentException("Screen must not be null");

            this.screen.pause();
            this.screen.dispose();
            screen.resume();
            screen.update(0);
            this.screen = screen;
        }

        public Screen getCurrentScreen() {
            return screen;
        }
}