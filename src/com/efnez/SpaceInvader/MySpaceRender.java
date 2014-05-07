package com.efnez.SpaceInvader;

import android.graphics.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dany on 02.05.14.
 */
public class MySpaceRender extends Thread {

    private float x;
    private float y;

    private Paint paint;
    private MySpaceView view;
    private Bitmap triangle;
//    private Canvas canvas;

    private ConcurrentHashMap<Integer, Bullet> bullets;
    int number = 0;

    public MySpaceRender(MySpaceView view) {
        this.view = view;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        GreenTriangleShip greenTriangleShip = new GreenTriangleShip(0, 0);
        triangle = greenTriangleShip.getBitmap();
        bullets = new ConcurrentHashMap<Integer, Bullet>();
    }

    public void addBullet() {
//        for (int i = 0; i < number; i++) {
            bullets.put(number++, new Bullet(x + MySpaceView.triangleCenter, y));
//        }
    }

    public void repaint(Canvas canvas) {
//        TODO dont repaint font
        canvas.drawPaint(paint);

//      TODO rewrite with View.getX or with interface getPair
        x = view.getPosition().first;
        y = view.getPosition().second;
        canvas.drawBitmap(triangle, x, y, paint);

        if (bullets == null || bullets.isEmpty()) {
            addBullet();
        }

        for (Bullet bullet : bullets.values()) {
            if (bullet != null) {
                canvas.drawBitmap(bullet.bitmap, bullet.x - bullet.getTriangleCenter(), bullet.y, paint);
                bullet.y -= 5;
                if (bullet.y < 0) {
                    bullets.remove(bullet);
                }
            }

        }
    }
}