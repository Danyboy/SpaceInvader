package com.efnez.SpaceInvader;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;

/**
 * Created by Dany on 08.05.14.
 */
public class GreenTriangleShip extends Triangle {
    public static float triangleLength = 100;

    public GreenTriangleShip(float x, float y){
        super(Color.GREEN, triangleLength, x, y);
    }

    @Override
    public Path drawTriangle() {
        Point a = new Point((int) (x - triangleLength / 2), (int) (y + triangleLength / 2));
        Point b = new Point((int) (x + triangleLength / 2), (int) (y + triangleLength / 2));
        Point c = new Point((int) x, (int) (y - triangleLength / 2));

        return drawTriangle(a, b, c);
    }
}
