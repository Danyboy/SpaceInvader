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
    private Triangle greenTriangle;

    private Paint paint;
    private MySpaceView view;

    int greenBulletQuantity = 0;
    int warriorId = 0;
    private int deadWarriorId = 0;
    private int redBulletQuantity;
    private Canvas canvas;
    private int greenHealth = 0;
    private ConcurrentHashMap<Integer, Triangle> deadWarriors;

    public MySpaceRender(MySpaceView view) {
        this.view = view;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        greenTriangle = new GreenTriangleShip(MenuActivity.X / 2, MenuActivity.Y / 2);

        deadWarriors = new ConcurrentHashMap<Integer, Triangle>();
        greenBullets = new ConcurrentHashMap<Integer, Triangle>();
        redBullets = new ConcurrentHashMap<Integer, Triangle>();
        warriors = new ConcurrentHashMap<Integer, Triangle>();
    }

    public void addGreenBullet() {
        greenBullets.put(greenBulletQuantity++, new GreenBullet(greenTriangle.x, greenTriangle.y));
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

        greenTriangle.x = view.getPosition().first; //      TODO rewrite with interface View.getX or with interface getPair
        greenTriangle.y = view.getPosition().second;
//        greenTriangle.x = this.getCogetIntent().getIntExtra("X", view);


        drawTriangle(greenTriangle); //ship
        drawWarriors();
        moveTriangles(greenBullets, -5);
        moveTriangles(redBullets, 3);
        moveTriangles(warriors, 1);
        checkIntersection();
        checkGreenIntersection();

        minimizeTriangle(deadWarriors, 1);
        moveTriangles(deadWarriors, 0);

        addWarriors();
        drawText(Color.RED, deadWarriorId + "", 10, 80);
        drawText(Color.GREEN, greenHealth+"", 10, view.getHeight() - 80);
    }



    private void minimizeTriangle(ConcurrentHashMap<Integer, Triangle> triangles, int i) {
        for (Integer integer : triangles.keySet()) {
            Triangle triangle = triangles.get(integer);
            if (triangle.getLength() > 1 ) {
                triangle.setTriangleLength(triangle.getLength() - i);
            } else {
                triangles.remove(integer);
            }
        }
    }

    private void drawWarriors() { // todo move war and minimize on breake
        for (Triangle warrior : warriors.values()) {
            drawTriangle(warrior);
        }
    }

    private void drawText(int color, String text, float x, float y) {
        paint.setColor(color);
        paint.setTextSize(100);
        canvas.drawText(text, x, y, paint);
        paint.setColor(Color.BLACK);
    }

    public void addRedBullets(){
        for (Triangle warrior : warriors.values()) {
            addRedBullet(warrior.x, warrior.y);
        }
    }

    private void moveTriangles(ConcurrentHashMap<Integer, Triangle> triangles, float step) {
        for (Integer integer : triangles.keySet()) {
            Triangle triangle = triangles.get(integer);
            drawTriangle(triangle);
            triangle.y += step;
            if (! isInBorder(triangle.x, triangle.y)){
                triangles.remove(integer);
            }
        }
    }

    private void drawTriangle(Triangle triangle) {
        canvas.drawPath(triangle.drawTriangle(), triangle.trianglePainter);
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

    private void checkCollision(){ //TODO find all collision and case by class destruction

    }

    private void checkIntersection(){ //ConcurrentHashMap<Integer, Bullet> greenBullets, ConcurrentHashMap<Integer, Warrio>
        for (Triangle bullet : greenBullets.values()) {
            for (Integer id : warriors.keySet()) {
                Triangle warrior = warriors.get(id);
                if (getDistance(bullet.x, bullet.y, warrior.x, warrior.y) <
                        bullet.getLength() / 2 + warrior.getLength() / 2){
                    deadWarriors.put(deadWarriorId, warriors.get(id));
                    warriors.remove(id);
                    deadWarriorId++;
                }
            }
        }
    }

    private void checkGreenIntersection(){ //ConcurrentHashMap<Integer, Bullet> greenBullets, ConcurrentHashMap<Integer, Warrior>
        for (Integer integer : redBullets.keySet()) {
            Triangle bullet = redBullets.get(integer);
            if (getDistance(bullet.x, bullet.y, greenTriangle.x, greenTriangle.y) <
                bullet.getLength() / 2 + greenTriangle.getLength() / 2){
                    redBullets.remove(integer);
                    greenHealth++;
            }
        }
    }


    public static float getDistance(float ax, float ay, float bx, float by){
        return (float) Math.sqrt(Math.pow((ay - by), 2) + Math.pow(ax - bx, 2));
    }


}