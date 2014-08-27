package com.efnez.SpaceInvader;

import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
 * Created by Dany on 08.05.14.
 */
public class GreenTriangleShip extends Triangle<Float> {

//    How to static initialise and use super methods?
//
//
//
//    public static float triangleLength = (119 - 1) / 2;
//    public static Bitmap bitmap = BitmapFactory.decodeResource(MySpaceView.resources, R.drawable.green_triangle);

    public GreenTriangleShip(float length,float x, float y){
        super(Color.GREEN, length, x, y);
    }

}
