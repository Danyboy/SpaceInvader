package com.efnez.SpaceInvader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
* Created by Dany on 02.05.14.
*/
public abstract class Bullet extends Triangle {
    public long ttl;
    public static float bulletLength = 10; //TODO change with display width

    public Bullet(int color, long ttl, float x, float y) {
        super(color, bulletLength, x, y);
        this.ttl = ttl;
    }

    public long getTtl() {
        return ttl;
    }

}


