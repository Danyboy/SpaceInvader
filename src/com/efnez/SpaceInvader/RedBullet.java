package com.efnez.SpaceInvader;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;

/**
 * Created by Dany on 07.09.14
 */
public class RedBullet extends Bullet {
    public static long ttl = 2500L;

    public RedBullet(float x, float y) {
        super(Color.RED, ttl, x, y);
    }

    @Override
    public Path drawTriangle() {
        Point a = new Point((int) (x - triangleLength / 2), (int) (y - triangleLength / 2));
        Point b = new Point((int) (x + triangleLength / 2), (int) (y - triangleLength / 2));
        Point c = new Point((int) x, (int) (y + triangleLength / 2));

        return drawTriangle(a, b, c);
    }
}
