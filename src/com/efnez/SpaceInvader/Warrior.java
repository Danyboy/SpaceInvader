package com.efnez.SpaceInvader;

import android.graphics.Color;

/**
 * Created by Dany on 27.08.14
 */
public class Warrior extends Triangle <Float>{
    public boolean isAlive = true;

    public Warrior(float length, float x, float y) {
        super(Color.RED, length, x, y);
    }
}
