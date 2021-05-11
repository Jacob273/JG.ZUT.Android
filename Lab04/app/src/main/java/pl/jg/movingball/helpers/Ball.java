package pl.jg.movingball.helpers;

import android.graphics.RectF;

public class Ball {




    public static float getX1() {
        return x1;
    }

    public static void setX1(float x1) {
        Ball.x1 = x1;
    }

    public static float getY1() {
        return y1;
    }

    public static void setY1(float y1) {
        Ball.y1 = y1;
    }

    public static float getX2() {
        return x2;
    }

    public static void setX2(float x2) {
        Ball.x2 = x2;
    }

    public static float getY2() {
        return y2;
    }

    public static void setY2(float y2) {
        Ball.y2 = y2;
    }

    public static void moveDown(float speed){
        final double dec = 1.5;
        y1 += dec * speed;
        y2 += dec * speed;
    }

    static float x1;
    static float y1;

    static float x2;
    static float y2;

    public static RectF AsRectf()
    {
        return new RectF(x1,y1,x2,y2);
    }

    public static String asString(){
        return String.format("A:(%s, %s)      B:(%s, %s)", x1, y1, x2, y2);
    }

}
