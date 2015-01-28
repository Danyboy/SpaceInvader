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
    public MySpaceRender mySpaceRender;

    public MySpaceView(Context context) {
        super(context);
        resources = getResources();
        mySpaceRender = new MySpaceRender(this);
        setPosition(MenuActivity.X / 2, MenuActivity.Y / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mySpaceRender.repaint(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, we are only
        // interested in events where the touch position changed.
        setPosition(e.getX(), e.getY());

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
        }
        return true;
    }

    void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Pair<Float, Float> getPosition(){
        return new Pair<Float, Float>(x, y);
    }

    public boolean getGameOver() {
        return mySpaceRender.gameOver;
    }

    public int getLevel(){
        return mySpaceRender.getMyLevel();
    }

}
