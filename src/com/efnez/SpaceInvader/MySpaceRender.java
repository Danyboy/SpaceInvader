package com.efnez.SpaceInvader;

import android.graphics.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dany on 02.05.14
 */
public class MySpaceRender {

    private final ConcurrentHashMap<Integer, Warrior> warriors;
    private float x;
    private float y;

    private Paint paint;
    private MySpaceView view;
    private Bitmap triangle;
//    private Canvas canvas;

    private ConcurrentHashMap<Integer, Bullet> bullets;
    int bulletQuantity = 0;
    int warriorId = 0;

    public MySpaceRender(MySpaceView view) {
        this.view = view;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        bullets = new ConcurrentHashMap<Integer, Bullet>();
        warriors = new ConcurrentHashMap<Integer, Warrior>();
    }

    public void addBullet() {
        bullets.put(bulletQuantity++, new Bullet(Color.GREEN, x, y));
    }

    public void addWarrior() {
        warriors.put(warriorId++, new Warrior(100, getRandomFloat(0, view.getWidth() / 2), getRandomFloat(0, view.getHeight())));
    }

    public float getRandomFloat(float start, float end){
        return (float) Math.random() * (start - end) + end;
    }

    public void repaint(Canvas canvas) {
//        TODO dont repaint font
        canvas.drawPaint(paint);

//      TODO rewrite with interface View.getX or with interface getPair
        x = view.getPosition().first;
        y = view.getPosition().second;
        drawTriangle(canvas, Color.GREEN, 100, x, y); //ship

        for (Warrior warrior : warriors.values()) {
            if (warrior.isAlive){
                drawTriangle(canvas, warrior.getTriangleColor(), warrior.getTriangleLength(), warrior.x, warrior.y);
            }
        }

        if (bullets == null || bullets.isEmpty()) {
            addBullet();
        }

        for (Bullet bullet : bullets.values()) {
            if (bullet != null) {
                drawTriangle(canvas, bullet.getTriangleColor(), bullet.getTriangleLength(), bullet.x, bullet.y);
                bullet.y -= 5;
                if (bullet.y < 0) {
                    bullets.remove(bullet);
                }
            }
        }

        checkIntersection();
//        for (Warrior warrior : warriors.values()) {
//            if (warrior.isAlive == false){
//                warriors.remove(warrior);
//            }
//        }

        if (warriors == null || warriors.isEmpty() || warriors.size() < 5){
            for (int i = 0; i < 5; i++) {
                addWarrior();
            }
        }
    }

    void checkIntersection(){
        for (Bullet bullet : bullets.values()) {
            for (Warrior warrior : warriors.values()) {
                if (getDistance(bullet.x, bullet.y, warrior.x, warrior.y) <
                        bullet.getTriangleLength() / 2 + warrior.getTriangleLength() / 2
                        && warrior.isAlive){
                    warrior.isAlive = false;
                    warriors.remove(warrior);
                }
            }
        }
    }

    private float getDistance(float ax, float ay, float bx, float by){
        return (float) Math.sqrt(Math.pow((ay - by), 2) + Math.pow(ax - bx, 2));
    }

    private void drawTriangle(Canvas canvas, int color, float length, float x , float y){
        Paint trianglePaint = new Paint();

        trianglePaint.setStrokeWidth(4);
        trianglePaint.setColor(color);
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