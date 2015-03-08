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
    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";

    private MySpaceView mySpaceView;
    private Handler handler = new Handler();
    long lastGreenFire = System.currentTimeMillis();
    private boolean isPlaying;
    private long now;
    private long lastRedFire;
    private int previousLevel = 1;
    private long defaultDelay = MyConstant.oneSecond / MyConstant.fps;
    private long delay = defaultDelay;
    private boolean isNextLevel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        isPlaying = true;
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //TODO may not work API level

        Intent intent = getIntent();
        mySpaceView = new MySpaceView(this);
        //Restoring saved level
        if (savedInstanceState != null) {
            mySpaceView.mySpaceRender.myLevel = savedInstanceState.getInt(STATE_LEVEL);
        }

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

                isNextLevel = mySpaceView.mySpaceRender.isNextLevel();
                if (isNextLevel){
                    mySpaceView.mySpaceRender.setNextLevel(false);
                    handler.postDelayed(this, MyConstant.oneSecond * MyConstant.defaultLevelDelay);
                }

                mySpaceView.invalidate();
                now = System.currentTimeMillis();
                addBullet();

                isPlaying = ! mySpaceView.mySpaceRender.gameOver;

                if (previousLevel < mySpaceView.mySpaceRender.getMyLevel()){ //TODO not work
                    previousLevel = mySpaceView.mySpaceRender.getMyLevel();
                    delay = MyConstant.oneSecond * MyConstant.defaultLevelDelay;
                } else {
                    delay = defaultDelay;
                }

                if (isPlaying && ! isNextLevel){
//                    System.out.println("pr " + previousLevel + " cur " + mySpaceView.getLevel());
                        handler.postDelayed(this, delay);
                }
            }
        };

        handler.postDelayed(loop, delay);
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
//        onCreate(null);

//        gameLoop();
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this, SpaceActivity.class);
//        startActivity(intent);
//        TODO start PauseActivity
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(STATE_SCORE, mySpaceView.mySpaceRender.getScore());
        savedInstanceState.putInt(STATE_LEVEL, mySpaceView.mySpaceRender.getMyLevel());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}