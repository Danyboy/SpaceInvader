package com.efnez.SpaceInvader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created with IntelliJ IDEA.
 * User: Dany
 */
public class SpaceActivity extends Activity {
    public static int X;
    public static int Y;
    private MySpaceView mySpaceView;
    private Handler handler = new Handler();
    long fps = MyConstant.fps; //Wait in two times longer
    long lastGreenFire = System.currentTimeMillis();
    private boolean isPlaying;
    private long now;
    private long lastRedFire;
    private int previousLevel = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        isPlaying = true;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //TODO may not work API level

        Intent intent = getIntent();
        mySpaceView = new MySpaceView(this);
        setContentView(mySpaceView);
        addEvent();
        gameLoop();
    }

    private void addEvent() {
        //TODO all time repaint convert into events (redBullet event etc)
    }


    private void gameLoop(){
        Runnable loop = new Runnable() {
            @Override
            public void run() {
                mySpaceView.invalidate();
                now = System.currentTimeMillis();
                addBullet();

                isPlaying = ! mySpaceView.getGameOver();

                if (isPlaying){
//                    System.out.println("pr " + previousLevel + " cur " + mySpaceView.getLevel());
                    if (previousLevel < mySpaceView.getLevel()){ //TODO not work
                        previousLevel = mySpaceView.getLevel();
                        handler.postDelayed(this, MyConstant.defaultDelay * MyConstant.defaultLevelDelay);
                    } else {
                        handler.postDelayed(this, MyConstant.defaultDelay / fps);
                    }
                }
            }
        };

        handler.postDelayed(loop, MyConstant.defaultDelay / fps);
    }

    private void addBullet() {
        if (now - lastGreenFire > GreenBullet.ttl){
            mySpaceView.mySpaceRender.addGreenBullet();
            lastGreenFire = now;
        }
        if (now - lastRedFire > RedBullet.ttl){
            mySpaceView.mySpaceRender.addRedBullets();
            lastRedFire = now;
        }
    }

    @Override
    protected void onPause() {
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        super.onPause();
        isPlaying = false;
    }

    @Override
    protected void onResume() {
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        super.onResume();
        isPlaying = true;
        gameLoop();
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this, SpaceActivity.class);
//        startActivity(intent);
    }
}