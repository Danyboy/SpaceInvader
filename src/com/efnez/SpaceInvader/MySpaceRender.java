package com.efnez.SpaceInvader;

import android.graphics.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dany on 02.05.14
 */
public class MySpaceRender {

    private final ConcurrentHashMap<Integer, Triangle> warriors;
    private final ConcurrentHashMap<Integer, Triangle> redBullets;
    private ConcurrentHashMap<Integer, Triangle> greenBullets;
    private float x;
    private float y;

    private Paint paint;
    private MySpaceView view;

    int greenBulletQuantity = 0;
    int warriorId = 0;
    private int deadWarriorId = 0;
    private int redBulletQuantity;
    private Canvas canvas;
    private int greenHealth = 0;
    float greenTriangleLength = 100;

    public MySpaceRender(MySpaceView view) {
        this.view = view;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        greenBullets = new ConcurrentHashMap<Integer, Triangle>();
        redBullets = new ConcurrentHashMap<Integer, Triangle>();
        warriors = new ConcurrentHashMap<Integer, Triangle>();
    }

    public void addGreenBullet() {
        greenBullets.put(greenBulletQuantity++, new GreenBullet(x, y));
    }

    public void addRedBullet(Float x, Float y) {
        redBullets.put(redBulletQuantity++, new RedBullet(x, y));
    }

    public void addWarrior() {
        warriors.put(warriorId++, new Warrior(getRandomFloat(0, view.getWidth()), 10));
    }

    public float getRandomFloat(float start, float end){
        return (float) Math.random() * (start - end) + end;
    }

    public void repaint(Canvas canvas) {
        this.canvas = canvas;
        canvas.drawPaint(paint); //        TODO dont repaint font

        x = view.getPosition().first; //      TODO rewrite with interface View.getX or with interface getPair
        y = view.getPosition().second;

        drawTriangle(Color.GREEN, greenTriangleLength, x, y); //ship
        drawWarriors();
        moveTriangle(greenBullets, -5);
        moveTriangle(redBullets, 3);
        moveTriangle(warriors, 1);
        checkIntersection();
        checkGreenIntersection();

        addWarriors();
        drawText(Color.RED, deadWarriorId + "", 10, 80);
        drawText(Color.GREEN, greenHealth+"", 10, view.getHeight() - 80);
    }

    private void drawWarriors() { // todo move war and minimize on breake
        for (Triangle<Float> warrior : warriors.values()) {
            drawReverseTriangle(warrior.getColor(), warrior.getLength(), warrior.x, warrior.y);
        }
    }

    private void drawText(int color, String text, float x, float y) {
        paint.setColor(color);
        paint.setTextSize(100);
        canvas.drawText(text, x, y, paint);
        paint.setColor(Color.BLACK);
    }

    public void addRedBullets(){
        for (Triangle<Float> warrior : warriors.values()) {
            addRedBullet(warrior.x, warrior.y);
        }
    }

    private void moveTriangle(ConcurrentHashMap<Integer, Triangle> triangles, float step) { //TODO rewrite with (path) some.draw
        for (Integer integer : triangles.keySet()) {
            Triangle<Float> triangle = triangles.get(integer);
            if (triangle != null) {
                if (triangle.getColor() == Color.GREEN){
                    drawTriangle(triangle.getColor(), triangle.getLength(), triangle.x, triangle.y);
                } else {
                    drawReverseTriangle(triangle.getColor(), triangle.getLength(), triangle.x, triangle.y);
                }
                triangle.y += step;
                if (! isInBorder(triangle.x, triangle.y)){
                    triangles.remove(integer);
                }
            }
        }
    }

    private boolean isInBorder(Float x, Float y) {
        return (0 < x && x < view.getWidth() && 0 < y && y < view.getHeight());
    }

    private void addWarriors() {
        if (warriors.isEmpty() || warriors.size() < 5){
            for (int i = 0; i < 5; i++) {
                addWarrior();
            }
        }
    }

    void checkIntersection(){ //ConcurrentHashMap<Integer, Bullet> greenBullets, ConcurrentHashMap<Integer, Warrio> 
        for (Triangle<Float> bullet : greenBullets.values()) {
            for (Integer id : warriors.keySet()) {
                Triangle<Float> warrior = warriors.get(id);
                if (getDistance(bullet.x, bullet.y, warrior.x, warrior.y) <
                        bullet.getLength() / 2 + warrior.getLength() / 2){
                    warriors.remove(id);
                    deadWarriorId++;
                }
            }
        }
    }

    void checkGreenIntersection(){ //ConcurrentHashMap<Integer, Bullet> greenBullets, ConcurrentHashMap<Integer, Warrior>
        for (Integer integer : redBullets.keySet()) {
            Triangle <Float> bullet = redBullets.get(integer);
            if (getDistance(bullet.x, bullet.y, x, y) <
                    bullet.getLength() / 2 + greenTriangleLength / 2){
                    redBullets.remove(integer);
                    greenHealth++;
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
        Point c = new Point((int) x, (int) (y + triangleLength / 2));

        drawTriangle(triangleColor, a, b, c);

    }



    private void drawTriangle(int triangleColor, float triangleLength, float x , float y){

        Point a = new Point((int) (x - triangleLength / 2), (int) (y + triangleLength / 2));
        Point b = new Point((int) (x + triangleLength / 2), (int) (y + triangleLength / 2));
        Point c = new Point((int) x, (int) (y - triangleLength / 2));

        drawTriangle(triangleColor, a, b, c);
    }
}