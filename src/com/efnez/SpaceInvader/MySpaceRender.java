package com.efnez.SpaceInvader;

import android.content.Context;
import android.graphics.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dany on 02.05.14
 */
public class MySpaceRender {

    private float x;
    private float y;

    private Paint paint;
    private MySpaceView view;
    private Bitmap triangle;
//    private Canvas canvas;

    private ConcurrentHashMap<Integer, Bullet> bullets;
    int bulletQuantity = 0;

    public MySpaceRender(MySpaceView view) {
        this.view = view;
//        paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.BLACK);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        GreenTriangleShip greenTriangleShip = new GreenTriangleShip(0, 0);
        triangle = greenTriangleShip.getBitmap();
        bullets = new ConcurrentHashMap<Integer, Bullet>();
    }

    public void addBullet() {
            bullets.put(bulletQuantity++, new Bullet(x + MySpaceView.triangleCenter, y));
    }

    public void repaint(Canvas canvas) {
//        TODO dont repaint font
//        canvas.drawPaint(paint);

//      TODO rewrite with interface View.getX or with interface getPair
        x = view.getPosition().first;
        y = view.getPosition().second;
        canvas.drawBitmap(triangle, x, y, paint);
        TriangleDrawer td = new TriangleDrawer();
        td.drawTriangle(canvas, x, y, 100); //ship

        if (bullets == null || bullets.isEmpty()) {
            addBullet();
        }

        for (Bullet bullet : bullets.values()) {
            if (bullet != null) {
//                canvas.drawBitmap(bullet.bitmap, bullet.x - bullet.getTriangleCenter(), bullet.y, paint);
                TriangleDrawer triangleDrawer = new TriangleDrawer();
                triangleDrawer.drawTriangle(canvas, bullet.x, bullet.y, 10);
                bullet.y -= 5;
                if (bullet.y < 0) {
                    bullets.remove(bullet);
                }
            }
        }
    }
//    private void drawTriangle(Canvas canvas){
//        drawTriangle(canvas, 0, 0);
//    }


    class TriangleDrawer {
        float length, x, y;

        public TriangleDrawer(Canvas canvas, float x , float y, float length){
            this.length = length;
            this.x = x;
            this.y = y;
        }

        TriangleDrawer(){

        }

        public void drawTriangle(Canvas canvas, float x , float y, float length){
//        float length = 100;
            Paint trianglePaint = new Paint();
            trianglePaint.setColor(android.graphics.Color.BLACK);
            canvas.drawPaint(trianglePaint);

            trianglePaint.setStrokeWidth(4);
            trianglePaint.setColor(Color.GREEN);
            trianglePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            trianglePaint.setAntiAlias(true);

            float myX = x, myY = y;
            Point a = new Point((int) (myX - length / 2), (int) (myY + length / 2));
            Point b = new Point((int) (myX + length / 2), (int) (myY + length / 2));
            Point c = new Point((int) myX, (int) (myY - length / 2));

            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(b.x, b.y);
            path.lineTo(b.x, b.y);
            path.lineTo(c.x, c.y);
            path.lineTo(a.x, a.y);
            path.lineTo(b.x, b.y);
            path.close();

            canvas.drawPath(path, trianglePaint);
        }
    }

}