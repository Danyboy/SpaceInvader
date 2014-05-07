package com.efnez.SpaceInvader;

import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dany on 08.05.14.
 */
public class GameLoop extends Timer {

    GameLoop gameLoop = null;
    TimerTask timerTask;

    private GameLoop(final View view){ //Some like Singleton with callback
        super();
        timerTask = new TimerTask() {
//            run
            @Override
            public void run() {
                    view.invalidate();
            }
        };

        schedule(timerTask, 1000L / 60); //TODO change to fps

    }



    public GameLoop getInstance(View view){
        if (gameLoop == null){
            gameLoop = new GameLoop(view);
        }

        return gameLoop;
    }


}
