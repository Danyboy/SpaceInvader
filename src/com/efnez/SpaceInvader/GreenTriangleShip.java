package com.efnez.SpaceInvader;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;

/**
 * Created by Dany on 08.05.14.
 */
public class GreenTriangleShip extends Triangle {

//    How to static initialise and use super methods?
//
//
//
//    public static float triangleLength = (119 - 1) / 2;
//    public static Bitmap bitmap = BitmapFactory.decodeResource(MySpaceView.resources, R.drawable.green_triangle);

    public GreenTriangleShip(float length,float x, float y){
        super(Color.GREEN, length, x, y);
    }

    @Override
    public Path drawTriangle() {
        Point a = new Point((int) (x - triangleLength / 2), (int) (y + triangleLength / 2));
        Point b = new Point((int) (x + triangleLength / 2), (int) (y + triangleLength / 2));
        Point c = new Point((int) x, (int) (y - triangleLength / 2));

        return drawTriangle(a, b, c);
    }
}
