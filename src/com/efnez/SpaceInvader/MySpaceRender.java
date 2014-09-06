package com.efnez.SpaceInvader;

import android.graphics.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dany on 02.05.14
 */
public class MySpaceRender {

    private final ConcurrentHashMap<Integer, Warrior> warriors;
    private final ConcurrentHashMap<Integer, Warrior> deadWarriors;
    private final ConcurrentHashMap<Integer, Bullet> redBullets;
    private float x;
    private float y;

    private Paint paint;
    private MySpaceView view;
    private Bitmap triangle;
//    private Canvas canvas;

    private ConcurrentHashMap<Integer, Bullet> greenBullets;
    int greenBulletQuantity = 0;
    int warriorId = 0;
    private int deadWarriorId = 0;
    private int redBulletQuantity;
    private Canvas canvas;

    public MySpaceRender(MySpaceView view) {
        this.view = view;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        greenBullets = new ConcurrentHashMap<Integer, Bullet>();
        redBullets = new ConcurrentHashMap<Integer, Bullet>();
        warriors = new ConcurrentHashMap<Integer, Warrior>();
        deadWarriors = new ConcurrentHashMap<Integer, Warrior>();
    }

    public void addGreenBullet() {
        greenBullets.put(greenBulletQuantity++, new GreenBullet(x, y));
    }

    public void addRedBullet(Float x, Float y) {
        greenBullets.put(redBulletQuantity++, new RedBullet(x, y));
    }

    public void addWarrior() {
        warriors.put(warriorId++, new Warrior(getRandomFloat(0, view.getWidth()), getRandomFloat(0, view.getHeight() / 2)));
    }

    public float getRandomFloat(float start, float end){
        return (float) Math.random() * (start - end) + end;
    }

    public void repaint(Canvas canvas) {
        this.canvas = canvas;
        canvas.drawPaint(paint); //        TODO dont repaint font

        x = view.getPosition().first; //      TODO rewrite with interface View.getX or with interface getPair
        y = view.getPosition().second;

        drawTriangle(Color.GREEN, 100, x, y); //ship

        for (Warrior warrior : warriors.values()) {
            drawReverseTriangle(warrior.getColor(), warrior.getLength(), warrior.x, warrior.y);
            addRedBullet(warrior.x, warrior.y);
        }

        addGreenBullets();

        moveBullets(greenBullets, 5, 0); //step in one iteration and border
        moveBullets(redBullets, 3, view.getHeight());
        checkIntersection();

        addWarriors();

        paint.setColor(Color.RED);
        paint.setTextSize(100);
        canvas.drawText(deadWarriorId+"", 10, 80, paint);
        paint.setColor(Color.BLACK);
    }

    private void moveBullets(ConcurrentHashMap<Integer, Bullet> bullets, float step, float border) {
        for (Integer integer : bullets.keySet()) {
            Bullet bullet = bullets.get(integer);
            if (bullet != null) {
                drawTriangle(bullet.getColor(), bullet.getLength(), bullet.x, bullet.y);
                bullet.y -= step;
                if (bullet.y < border) {
                    bullets.remove(integer);
                }
            }
        }
    }

    private void addGreenBullets() {
        if (greenBullets == null || greenBullets.isEmpty()) {
            addGreenBullet();
        }
    }

    private void addWarriors() {
        if (warriors == null || warriors.isEmpty() || warriors.size() < 5){
            for (int i = 0; i < 5; i++) {
                addWarrior();
            }
        }
    }

    void checkIntersection(){
        for (Bullet bullet : greenBullets.values()) {
            for (Integer id : warriors.keySet()) {
                Warrior warrior = warriors.get(id);
                if (getDistance(bullet.x, bullet.y, warrior.x, warrior.y) <
                        bullet.getLength() / 2 + warrior.getLength() / 2){
                    warriors.remove(id);
                    deadWarriorId++;
                }
            }
        }
    }

    private float getDistance(float ax, float ay, float bx, float by){
        return (float) Math.sqrt(Math.pow((ay - by), 2) + Math.pow(ax - bx, 2));
    }

    private void drawTriangle(int color, Point a, Point b, Point c){
        Paint trianglePaint = new Paint();

        trianglePaint.setStrokeWidth(4);
        trianglePaint.setColor(color);
        trianglePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        trianglePaint.setAntiAlias(true);

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

    private void drawReverseTriangle(int triangleColor, float triangleLength, float x, float y) {
        Point a = new Point((int) (x - triangleLength / 2), (int) (y - triangleLength / 2));
        Point b = new Point((int) (x + triangleLength / 2), (int) (y - triangleLength / 2));
        Point c = new Point((int) x, (int) (y - triangleLength / 2));

        drawTriangle(triangleColor, a, b, c);

    }



    private void drawTriangle(int triangleColor, float triangleLength, float x , float y){

        Point a = new Point((int) (x - triangleLength / 2), (int) (y + triangleLength / 2));
        Point b = new Point((int) (x + triangleLength / 2), (int) (y + triangleLength / 2));
        Point c = new Point((int) x, (int) (y - triangleLength / 2));

        drawTriangle(triangleColor, a, b, c);
    }
}