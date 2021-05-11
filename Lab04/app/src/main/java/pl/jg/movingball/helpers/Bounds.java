package pl.jg.movingball.helpers;

public class Bounds {

    static final double THRESHOLD = .001;

    public static float getHeight() {
        return height;
    }

    public static void setHeight(float height) {
        Bounds.height = height;
    }

    public static float getWidth() {
        return width;
    }

    public static void setWidth(float width) {
        Bounds.width = width;
    }

    public static float getMargin() {
        return margin;
    }

    public static void setMargin(float margin) {
        Bounds.margin = margin;
    }

    public static Touching isTouchingBounds(float x1, float x2, float y1, float y2){
        if (Math.abs(x2 - getWidth()) >= THRESHOLD)
        {
            return Touching.Right;
        }
    }

    private static float height;
    private static float width;
    private static float margin;



}
