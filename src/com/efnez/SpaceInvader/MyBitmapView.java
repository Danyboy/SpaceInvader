package com.efnez.SpaceInvader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dany on 11.03.14.
 */
public class MyBitmapView extends View {

    private float x;
    private float y;
    public static Resources resources;
    private MySpaceRender render;
//    private Canvas canvas;

    public MyBitmapView(Context context) {
        super(context);
        resources = getResources();
        render = new MySpaceRender(this);
//        thread.start();
    }

    @Override
    protected void onFinishInflate(){
        setPosition(getHeight() / 2, getWidth() / 2); //FIX doesnt work
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        render.setCanvas(canvas);
//        this.canvas = canvas;
        render.repaint(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, we are only
        // interested in events where the touch position changed.
        setPosition(e.getX(), e.getY());

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                invalidate();
        }
        return true;
    }

    void setPosition(float x, float y) {
        this.x = x - (119 - 1) / 2; // Triangle image centering // TODO refactor with Bitmap.getWidth()
        this.y = y - 120;
    }

    public Pair<Float, Float> getPosition(){
        return new Pair<Float, Float>(x, y);
    }

    Thread thread = new Thread(){
    @Override
    public void run() {
        boolean isRunning = true;

        try {
            while (isRunning) {
//                repaint();
                invalidate();
                Thread.sleep(12000L / 60); //fps
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    };

}
