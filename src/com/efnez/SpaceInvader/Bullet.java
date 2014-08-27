package com.efnez.SpaceInvader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
* Created by Dany on 02.05.14.
*/
public class Bullet extends Triangle <Float> {
    public int ttl;
    public static float bulletLength = 10; //TODO change with display width

    public Bullet(int color, float x, float y) {
        super(color, bulletLength, x, y);
    }
}


