package com.efnez.SpaceInvader;

import android.graphics.*;
import android.view.View;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dany on 02.05.14.
 */
public class MySpaceRender implements Render {

    private float x;
    private float y;

    private Paint paint;
    private View view;

    private ConcurrentHashMap<Integer, Bullet> objects;
    int number = 10;

    public MySpaceRender(MyBitmapView view, Canvas canvas){
        this.view = view;

        Bitmap triangle;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        canvas.drawPaint(paint);

        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#CD5C5C"));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

//        TODO get res without view
        triangle = BitmapFactory.decodeResource(MyBitmapView.resources, R.drawable.green_triangle);

        x = view.getPosition().first;
        y = view.getPosition().second;
        canvas.drawBitmap(triangle, x, y, paint);

        if (objects == null || objects.isEmpty()) {
            init();
        }
    }


    public void init() { //create new objects
        objects = new ConcurrentHashMap<Integer, Bullet>();
        for (int i = 0; i < number; i++) {
            objects.put(i, new Bullet(x, y));
        }
    }

    public void repaint(Canvas canvas) {
        if (objects != null && !objects.isEmpty()) {
            Bullet bullet;
            for (int i = 0; i < number; i++) {
                bullet = objects.get(i);
                if (bullet != null) {
                    canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
                    bullet.y -= 5;
                    if (bullet.y < 0) {
                        objects.remove(i);
//                        number--;     //TODO FIX to two var
                    }
                }
            }
        } else {
            init();
        }
    }




}
