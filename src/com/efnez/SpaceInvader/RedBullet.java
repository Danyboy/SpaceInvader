package com.efnez.SpaceInvader;

import android.graphics.Color;

/**
 * Created by Dany on 07.09.14
 */
public class RedBullet extends Bullet {
    public static long ttl = 2500L;

    public RedBullet(float x, float y) {
        super(Color.RED, ttl, x, y);
    }
}
