package com.efnez.SpaceInvader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
* Created by Dany on 02.05.14.
*/
public class Bullet extends Triangle <Float> {
    public int ttl;

    public Bullet(float x, float y) {
        super(x, y);
        triangleCenter = (23 - 1) / 2;
        bitmap = BitmapFactory.decodeResource(MySpaceView.resources, R.drawable.green_triangle_23x12);
    }
}


