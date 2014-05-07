package com.efnez.SpaceInvader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Dany on 11.03.14.
 */
public class MySpaceView extends View {

    private float x;
    private float y;
    public static Resources resources;
    private MySpaceRender render;

    public final static float triangleCenter = (119 - 1) / 2;

//    private Canvas canvas;

    public MySpaceView(Context context) {
        super(context);
        resources = getResources();
        render = new MySpaceRender(this);

//        gameLoop = new Timer("Game loop");
    }

    @Override
    protected void onFinishInflate(){
        setPosition(getHeight() / 2, getWidth() / 2); //TODO FIX doesnt work
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
//                invalidate();
        }
        return true;
    }

    void setPosition(float x, float y) {
        this.x = x - triangleCenter; // Triangle image centering // TODO refactor with Bitmap.getWidth()
        this.y = y - 120;
    }

    public Pair<Float, Float> getPosition(){
        return new Pair<Float, Float>(x, y);
    }

}
