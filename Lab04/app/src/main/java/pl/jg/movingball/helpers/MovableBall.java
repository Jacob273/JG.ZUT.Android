package pl.jg.movingball.helpers;

import android.graphics.RectF;

public class MovableBall {

    private static MovableBall _instance;

    private MovableBall() {
    }

    public static MovableBall getInstance() {
        if(_instance == null) {
            _instance = new MovableBall();
        }
        return _instance;
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public void moveDown(float speed){
        final double absSpeed = Math.abs(speed);
        y1 += change * absSpeed;
        y2 += change * absSpeed;
    }

    final double change = 5.5;

    public void moveUp(float speed){
        final double absSpeed = Math.abs(speed);
        y1 -= change * absSpeed;
        y2 -= change * absSpeed;
    }

    public void moveLeft(float speed){
        final double absSpeed = Math.abs(speed);
        x1 -= change * absSpeed;
        x2 -= change * absSpeed;
    }

    public void moveRight(float speed){
        final double absSpeed = Math.abs(speed);
        x1 += change * absSpeed;
        x2 += change * absSpeed;
    }

    private float x1;
    private float y1;

    private float x2;
    private float y2;

    public RectF AsRectf()
    {
        return new RectF(x1,y1,x2,y2);
    }

    public String asString(){
        return String.format("A:(%s, %s)      B:(%s, %s)", x1, y1, x2, y2);
    }

}
