package com.efnez.SpaceInvader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

/**
* Created by Dany on 02.05.14.
*/
public class Bullet extends Triangle <Float> {
    public Bitmap bullet = BitmapFactory.decodeResource(MyBitmapView.resources, R.drawable.green_triangle_23x12);
    public int ttl;

    public Bullet(float x, float y) {
        super(x, y);
    }
}

class Triangle <T>{
    public T x, y;

    public Triangle(T x, T y) {
        this.x = x;
        this.y = y;
    }

}


