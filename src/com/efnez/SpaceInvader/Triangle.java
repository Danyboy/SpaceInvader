package com.efnez.SpaceInvader;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Dany on 02.05.14.
 */
class Triangle <T>{
    public float triangleLength;
    public T x, y;
    public int triangleColor;

    public Triangle(int color, float length, T x, T y) {
        triangleColor = color;
        triangleLength = length;
        this.x = x;
        this.y = y;
    }

    public float getLength() {
        return triangleLength;
    }
    public int getColor() {
        return triangleColor;
    }


}
