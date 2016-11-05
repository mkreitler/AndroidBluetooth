package com.sphero.kreitler.legobluetooth;

/**
 * Created by mark on 11/3/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CanvasView extends SurfaceView implements SurfaceHolder.Callback {
    public static Paint PAINT = new Paint();

    private Canvas canvas = null;
    private Context appContext = null;
    private SurfaceHolder surfaceHolder = null;

    public CanvasView(Context context) {
        super(context);

        appContext = context;
        getHolder().addCallback(this);

        // Make the GamePanel focusable so it can handle events.
        setFocusable(true);
    }

    public Canvas lock() {
        return surfaceHolder != null ? surfaceHolder.lockCanvas() : null;
    }

    public void unlock(Canvas canvas) {
        if (surfaceHolder != null) {
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void surfaceCreated(SurfaceHolder holder) {
        surfaceHolder = holder;
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        if (canvas != null) {
            PAINT.setARGB(255, 0, 0, 0);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), PAINT);

            PAINT.setARGB(255, 0, 255, 0);
            canvas.drawRect(canvas.getWidth() / 4, canvas.getHeight() / 4, 3 * canvas.getWidth() / 4, 3 * canvas.getHeight() / 4, PAINT);
        }
    }
}
