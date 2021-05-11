package pl.jg.movingball.helpers;

public class Bounds {

    public static final int NOONE = 0;  // 0000
    public static final int LEFT = 1;  // 0001
    public static final int RIGHT   = 2;  // 0010
    public static final int TOP = 4;  // 0100
    public static final int BOTTOM = 8;  // 1000

    private float screenHeight;
    private float screenWidth;
    private float margin;
    private static Bounds _instance;

    private Bounds() {
    }

    public static Bounds getInstance() {
        if (_instance == null) {
            _instance = new Bounds();
        }
        return _instance;
    }

    static final double THRESHOLD = .001;

    public float getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
    }

    public float getMargin() {
        return margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }


    public int checkTouchingState(MovableBall ball) {

        //float x1, float x2, float y1, float y2
        float x1 = ball.getX1();
        float x2 = ball.getX2();
        float y1 = ball.getY1();
        float y2 = ball.getY2();

        Integer _result = Bounds.NOONE;

        if(Float.compare(y2, getScreenHeight()) >= 0)
        {
            _result |= Bounds.BOTTOM;
        }

        if(Float.compare(x2, getScreenWidth()) >= 0)
        {
            _result |= Bounds.RIGHT;
        }

        if(Float.compare(y1, 0) <= 0)
        {
            _result |= Bounds.TOP;
        }

        if(Float.compare(x1, 0) <= 0)
        {
            _result |= Bounds.LEFT;
        }

        return _result;
    }
}