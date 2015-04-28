package com.efnez.SpaceInvader;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.net.nsd.NsdManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dany on 02.05.14
 */
public class MySpaceRender {

    private Canvas canvas;
    private MySpaceView view;
    private Paint paint;

    private final ConcurrentHashMap<Integer, Triangle> warriors;
    private final ConcurrentHashMap<Integer, Triangle> redBullets;
    private ConcurrentHashMap<Integer, Triangle> greenBullets;
    private ConcurrentHashMap<Integer, Triangle> deadWarriors;
    public Triangle greenTriangle;

    private int greenBulletQuantity = 0;
    private int warriorId = 0;
    private int deadWarriorId = 0;
    private int redBulletQuantity;
    private int greenHit = 0;

    private int backgroundY = 0;
    private int backgroundImageSize = 1440; //TODO remove
    private Bitmap background1;
    private Bitmap background2;

    public int myLevel = 1;
    public boolean gameOver;

    private boolean nextLevel = false;

    NsdHelper mNsdHelper;
    private Handler mUpdateHandler;
    PlayerConnection mConnection;
    private GreenTriangleShip playerTriangle;

    public MySpaceRender(MySpaceView view, Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        background1 = BitmapFactory.decodeResource(MySpaceView.resources, R.drawable.stars1, options);
        backgroundImageSize = options.outHeight;
        background2 = BitmapFactory.decodeResource(MySpaceView.resources, R.drawable.stars2);

        this.view = view;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        greenTriangle = new GreenTriangleShip(MenuActivity.X / 2, MenuActivity.Y / 2); //TODO rewrite with intent

        deadWarriors = new ConcurrentHashMap<Integer, Triangle>();
        greenBullets = new ConcurrentHashMap<Integer, Triangle>();
        redBullets = new ConcurrentHashMap<Integer, Triangle>();
        warriors = new ConcurrentHashMap<Integer, Triangle>();

        playerTriangle = new GreenTriangleShip(MenuActivity.X, MenuActivity.Y); //TODO rewrite with intent
//        mConnection = new PlayerConnection(mUpdateHandler);
//        mNsdHelper = new NsdHelper(context);
//        mNsdHelper.initializeNsd();
//        clickAdvertise(view);
//        updatePositionHandler();
    }

    private void updatePositionHandler() {
        mUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Float x = msg.getData().getFloat("x");
                Float y = msg.getData().getFloat("y");
                updatePlayerPosition(x, y);
            }
        };
    }

    private void updatePlayerPosition(Float x, Float y) {
        playerTriangle.x = x;
        playerTriangle.y = y;
    }

    public void clickAdvertise(View v) {
        // Register service
        if(mConnection.getLocalPort() > -1) {
            mNsdHelper.registerService(mConnection.getLocalPort());
        } else {
//            Log.d(TAG, "ServerSocket isn't bound.");
        }
    }



    public void repaint(Canvas canvas) {
        this.canvas = canvas;
//        redrawBackground();

        greenTriangle.x = view.getPosition().first; //      TODO rewrite with interface View.getX or with interface getPair
        greenTriangle.y = view.getPosition().second;

        //TODO rewrite with getLevelConstant()
        drawTriangle(greenTriangle); //ship

        if (playerTriangle != null){ //TODO added list of players
            drawTriangle(playerTriangle); //ship
        }

        drawWarriors();
        moveTriangles(greenBullets, getGreenBulletSpeedByLevel());
        moveTriangles(redBullets, getRedBulletSpeedByLevel());
        moveTriangles(warriors, 1);
        checkIntersection();
        checkGreenIntersection();

        minimizeTriangle(deadWarriors, 1);
        moveTriangles(deadWarriors, 0);

        addWarriors();

        drawWarriorHealth();
        drawGreenHealth();
        drawCurrentScore();

        checkGameOver();
        checkLevelComplete();

//        mConnection.sendPosition(view.getPosition()); //

    }

    private float getRedBulletSpeedByLevel() {
        return 2 + myLevel;
    }

    private float getGreenBulletSpeedByLevel() {
        return -5;
    }

    private void redrawBackground() {
        int step = 3;
        if (backgroundY < 3 * backgroundImageSize - step){
            drawBackground(backgroundY);
            backgroundY += step;
        } else {
            backgroundY = 0;
            drawBackground(backgroundY);
        }
    }

    private void drawBitmap(Bitmap bitmap, float y){
        canvas.drawBitmap(bitmap, -400, y, paint);
    }

    private void drawBackground(float y) {
        if (y < backgroundImageSize){
            if (y < MenuActivity.Y){
            drawBitmap(background1, y);}
            drawBitmap(background2, y - backgroundImageSize);
        } else if (backgroundImageSize <= y && y < 2 * backgroundImageSize){
            if (y < backgroundImageSize + MenuActivity.Y){
            drawBitmap(background2, y - backgroundImageSize);}
            drawBitmap(background1, y - 2 * backgroundImageSize);
        } else if (2 * backgroundImageSize <= y && y < 3 * backgroundImageSize){
            if (y < 2 * backgroundImageSize + MenuActivity.Y){
            drawBitmap(background1, y - 2 * backgroundImageSize);}
            drawBitmap(background2, y - 3 * backgroundImageSize);
        }
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

    private void drawWarriors() {
        for (Triangle warrior : warriors.values()) {
            drawTriangle(warrior);
        }
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
                triangles.remove(integer); // TODO check that it work
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
        if (warriors.isEmpty() || warriors.size() < getWarriorsByLevel()){
            for (int i = 0; i < getWarriorsByLevel(); i++) {
                addWarrior();
            }
        }
    }

    private void checkCollision(){ //TODO find all collision and case destruction by class

    }

    private void checkIntersection(){ //ConcurrentHashMap<Integer, Bullet> greenBullets, ConcurrentHashMap<Integer, Warrio>
        for (Triangle bullet : greenBullets.values()) {
            for (Integer id : warriors.keySet()) {
                Triangle warrior = warriors.get(id);
                if (getDistance(bullet.x, bullet.y, warrior.x, warrior.y) <
                        bullet.getLength() / 2 + warrior.getLength() / 2){
                    deadWarriors.put(deadWarriorId, warriors.get(id));
                    warriors.remove(id); //TODO rewrite with iterator remove
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
                    greenHit++;
            }
        }
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

    private void drawText(int color, String text, float x, float y) {
        paint.setColor(color);
        paint.setTextSize(MyConstant.defaultTriangleLength);
        canvas.drawText(text, x, y, paint);
        paint.setColor(Color.BLACK);
    }

    private void drawLine(int color, float x, float y) {
        paint.setColor(color);
        paint.setStrokeWidth(MyConstant.defaultBulletLength);
        canvas.drawLine(0, y, x, y, paint);
        paint.setColor(Color.BLACK);
    }

    public float getRandomFloat(float start, float end){
        return (float) Math.random() * (start - end) + end;
    }

    public static float getDistance(float ax, float ay, float bx, float by){
        return (float) Math.sqrt(Math.pow((ay - by), 2) + Math.pow(ax - bx, 2));
    }

    public double getGreenHealth() {
        return (double) (getGreenHealthByLevel() - greenHit);
    }

    public double getRedHealth() {
        return (double) (getRedHealthByLevel() - deadWarriorId);
    }

    private float getNormalizeHealth(double currentHealth, int levelHealth){
        return (float) (currentHealth / (double)levelHealth);
    }

    private int getRedHealthByLevel() {
        return myLevel * MyConstant.defaultWarriorQuantityOnLevel; //TODO remove this shit
    }

    private int getWarriorsByLevel() {
        return MyConstant.minWarriorQuantityOnLevel + myLevel;
    }

    public int getMyLevel() {
        return myLevel;
    }

    private int getGreenHealthByLevel() {
        int health = MyConstant.startGreenHealth - myLevel;
        return health > 1 ? health : 1; //TODO remove this shit
    }

    public void setNextLevel(boolean nextLevel) {
        this.nextLevel = nextLevel;
    }

    public boolean isNextLevel() {
        return nextLevel;
    }

    int myOneStepHack = 0;
    private void checkLevelComplete(){
        if (myOneStepHack == 1){
            myOneStepHack = 0;
            drawNextLevelMessage();
        }
        if (getRedHealth() <= 0 ){
            myOneStepHack = 1;
            deadWarriorId = 0; //TODO check it that nothing broke
            greenHit = 0;
            drawNextLevelMessage();
            myLevel++;
            nextLevel = true;
        }
    }

    private void checkGameOver(){
        if (getGreenHealth() <= 0) {
            drawText(MyConstant.myRed, "Game over!", MyConstant.defaultWarriorLength, view.getHeight() / 2);
            drawScore();
            gameOver = true;
        }
    }

    public int getScore() {
        return (((myLevel - 1) * (myLevel) / 2) * MyConstant.defaultWarriorQuantityOnLevel + deadWarriorId);
    }

    private void drawNextLevelMessage() {
        drawText(MyConstant.myGreen, "Level " + (myLevel - 1) + " complete!", MyConstant.defaultWarriorLength, view.getHeight() / 2);
        drawScore();
    }

    private void drawScore() {
        drawText(MyConstant.myGreen, "You score " + getScore() + "",
                MyConstant.defaultWarriorLength,
                view.getHeight() / 2 + 2 * MyConstant.defaultWarriorLength);
    }

    private void drawCurrentScore() {
        drawText(MyConstant.myRed, getScore() + "", MyConstant.defaultBulletLength,
                2 * MyConstant.defaultWarriorLength);
    }

    private void drawGreenHealth() {
        drawLine(MyConstant.myGreen, (view.getWidth() * getNormalizeHealth(getGreenHealth(), getGreenHealthByLevel())),
                view.getHeight() - MyConstant.defaultBulletLength);
    }

    private void drawWarriorHealth() {
        drawLine(MyConstant.myRed, (view.getWidth() * getNormalizeHealth(getRedHealth(), getRedHealthByLevel())),
                MyConstant.defaultBulletLength);
    }
}