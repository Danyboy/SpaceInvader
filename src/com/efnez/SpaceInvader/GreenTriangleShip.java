package com.efnez.SpaceInvader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Dany on 08.05.14.
 */
public class GreenTriangleShip extends Triangle<Float> {

//    How to static initialise and use super methods?
//
//
//
//    public static float triangleCenter = (119 - 1) / 2;
//    public static Bitmap bitmap = BitmapFactory.decodeResource(MySpaceView.resources, R.drawable.green_triangle);

    public GreenTriangleShip(float x, float y){
        super(x, y);
        triangleCenter = (119 - 1) / 2;
        //        TODO get res without view
        bitmap = BitmapFactory.decodeResource(MySpaceView.resources, R.drawable.green_triangle);
    }

}
