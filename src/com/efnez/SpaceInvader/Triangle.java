package com.efnez.SpaceInvader;

import android.graphics.*;

/**
 * Created by Dany on 02.05.14.
 */
abstract class Triangle {
    public float triangleLength;
    public float x, y;
    public int triangleColor;
    Paint trianglePainter;

    public Triangle(int color, float length, float x, float y) {
        triangleColor = color;
        triangleLength = length;
        this.x = x;
        this.y = y;
        trianglePainter = trianglePainter();
    }

    public float getLength() {
        return triangleLength;
    }
    public int getColor() {
        return triangleColor;
    }


    public void setTriangleLength(float triangleLength) {
        this.triangleLength = triangleLength;
    }

    protected Path drawTriangle(Point a, Point b, Point c){
        Path triangle;

        triangle = new Path();
        triangle.setFillType(Path.FillType.EVEN_ODD);
        triangle.moveTo(b.x, b.y);
        triangle.lineTo(b.x, b.y);
        triangle.lineTo(c.x, c.y);
        triangle.lineTo(a.x, a.y);
        triangle.lineTo(b.x, b.y);
        triangle.close();

//        canvas.drawPath(greenTriangle, trianglePaint);
        return triangle;
    }

    private Paint trianglePainter(){
        trianglePainter = new Paint();

        trianglePainter.setStrokeWidth(4);
        trianglePainter.setColor(triangleColor);
        trianglePainter.setStyle(Paint.Style.FILL_AND_STROKE);
        trianglePainter.setAntiAlias(true);
        return trianglePainter;
    }

    public abstract Path drawTriangle();

    public void setCoordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
