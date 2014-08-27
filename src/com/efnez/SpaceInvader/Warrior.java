package com.efnez.SpaceInvader;

import android.graphics.Color;

/**
 * Created by Dany on 27.08.14
 */
public class Warrior extends Triangle <Float>{
    public boolean isAlive = true;
    private static float warriorLength = 100;

    public Warrior(float x, float y) {
        super(Color.RED, warriorLength, x, y);
    }
}
