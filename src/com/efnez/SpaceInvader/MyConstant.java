package com.efnez.SpaceInvader;

/**
 * Created by Dany on 28.01.2015
 */
public class MyConstant {

    public static int X = MenuActivity.X;

    public static float defaultBulletLength = X / 72;
    public static float defaultTriangleLength = defaultBulletLength * 10;
    public static float defaultTriangleAimLength = defaultTriangleLength * 1.4F;
    public static float defaultWarriorLength = defaultBulletLength * 5;
    public static long fps = 30 * 2;  //Wait in two times longer
    public static long oneSecond = 1000;
    public static long defaultLevelDelay = 2;
    public static int defaultWarriorQuantityOnLevel = 50;
    public static int minWarriorQuantityOnLevel = 4;
    public static int startGreenHealth = 21;
}
