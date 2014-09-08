package com.efnez.SpaceInvader;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;

/**
 * Created by Dany on 27.08.14
 */
public class Warrior extends Triangle {
    public boolean isAlive = true;
    private static float warriorLength = 50;

    public Warrior(float x, float y) {
        super(Color.RED, warriorLength, x, y);
    }

    @Override
    public Path drawTriangle() {
        Point a = new Point((int) (x - triangleLength / 2), (int) (y - triangleLength / 2));
        Point b = new Point((int) (x + triangleLength / 2), (int) (y - triangleLength / 2));
        Point c = new Point((int) x, (int) (y + triangleLength / 2));

        return drawTriangle(a, b, c);
    }
}
