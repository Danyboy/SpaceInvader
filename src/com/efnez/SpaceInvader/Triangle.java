package com.efnez.SpaceInvader;

import android.graphics.Bitmap;

/**
 * Created by Dany on 02.05.14.
 */
class Triangle <T>{
    public static float triangleCenter;
    public static Bitmap bitmap;
    public T x, y;

    public Triangle(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public static float getTriangleCenter() {
        return triangleCenter;
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }
}
