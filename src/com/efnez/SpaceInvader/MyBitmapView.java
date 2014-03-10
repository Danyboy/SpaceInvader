package com.efnez.SpaceInvader;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Dany on 11.03.14.
 */
public class MyBitmapView extends View {

    private float x;
    private float y;

    public MyBitmapView(Context context) {
        super(context);
        setPosition(getHeight() / 2, getWidth() / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap triangle;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#CD5C5C"));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        triangle = BitmapFactory.decodeResource(getResources(), R.drawable.green_triangle);

        canvas.drawBitmap(triangle, x, y, paint);
    }

    void setPosition(float x, float y){
        this.x = x - (119 - 1) / 2; // Image centering // TODO with Bitmap.getWidth()
        this.y = y - 120;
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
}
