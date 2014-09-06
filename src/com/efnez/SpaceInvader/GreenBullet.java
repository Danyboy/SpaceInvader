package com.efnez.SpaceInvader;

import android.graphics.Color;

/**
 * Created by Dany on 07.09.14
 */
public class GreenBullet extends Bullet {
    public static long ttl = 250L;

    public GreenBullet(float x, float y) {
        super(Color.GREEN, ttl, x, y);
    }
}
