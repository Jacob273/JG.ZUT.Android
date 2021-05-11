package pl.jg.movingball.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import pl.jg.movingball.helpers.Collider;
import pl.jg.movingball.helpers.ForceHandler;
import pl.jg.movingball.helpers.MovableBall;
import pl.jg.movingball.helpers.Bounds;
import pl.jg.movingball.helpers.MoveMode;

public class BallBackgroundActivity extends Activity implements SensorEventListener
{
    BallView ball = null;
    ShapeDrawable mDrawable = new ShapeDrawable();
    public static float sensorValueY;
    public static float sensorValueX;

    private SensorManager sensorManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ball = new BallView(this);
        setContentView(ball);
    }

    // This method will update the UI on new sensor events
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                sensorValueX = sensorEvent.values[0];
                sensorValueY = sensorEvent.values[1];
            }
        }
    }

    // I've chosen to not implement this method
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // Register this class as a listener for the accelerometer sensor
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        // ...and the orientation sensor
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop()
    {
        // Unregister the listener
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    public class BallView extends View
    {
        final int ballWidth = 250;
        final int ballHeight = 250;
        RectF circle;
        Paint p;

        float lastSensorValueX;
        float lastSensorValueY;
        final double THRESHOLD = .001;
        private boolean firstTimeDrawn = true;

        public int getScreenHeight(){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.heightPixels;
        }

        public int getScreenWidth(){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }

        public BallView(Context context)
        {
            super(context);
            mDrawable = new ShapeDrawable(new OvalShape());
            mDrawable.getPaint().setColor(0xff74AC23);
            mDrawable.setBounds(5, 5, getScreenWidth() - ballHeight, getScreenHeight() - ballHeight);
        }

        public boolean hasSensorValueYChanged(float newSensorY){
            if (Math.abs(newSensorY - lastSensorValueY) > THRESHOLD)
            {
                lastSensorValueY = newSensorY;
                return true;
            }
            return false;
        }

        public boolean hasSensorValueXChanged(float newSensorX){
            if (Math.abs(newSensorX - lastSensorValueX) > THRESHOLD)
            {
                lastSensorValueX = newSensorX;
                return true;
            }
            return false;
        }

        public boolean pointingUpwards(float newSensorValueX)
        {
            return newSensorValueX > 0;
        }

        public boolean pointingDownwards(float newSensorValueX)
        {
            return newSensorValueX < 0;
        }

        public boolean pointingRight(float newSensorValueY)
        {
            return newSensorValueY < 0;
        }

        public boolean pointingLeft(float newSensorValueY)
        {
            return newSensorValueY > 0;
        }

        protected void onDraw(Canvas canvas)
        {
            //System.out.println(String.format("sensor wychylenie X: <{%f}>", BallBackgroundActivity.sensorValueX));
            //System.out.println(String.format("sensor wychylenie Y: <{%f}>", BallBackgroundActivity.sensorValueY));
            if(firstTimeDrawn == true)
            {
                setDefaultBallValuesTopLeft();
                circle = MovableBall.getInstance().AsRectf();
                Collider.getInstance().setBall(MovableBall.getInstance());
                p = new Paint();
                p.setColor(Color.GREEN);
                Bounds.getInstance().setMargin(ballHeight);
                Bounds.getInstance().setScreenHeight(getScreenHeight());
                Bounds.getInstance().setScreenWidth(getScreenWidth());
                firstTimeDrawn = false;
            }

            Integer currentTouchingState = Bounds.getInstance().checkTouchingState(MovableBall.getInstance());
            Collider.getInstance().setTouchingState(currentTouchingState);
            MoveMode moveMode = Collider.getInstance().getMode();

            if(moveMode == MoveMode.FreeFall) {
                if (hasSensorValueYChanged(BallBackgroundActivity.sensorValueY)) {
                    if (pointingUpwards(BallBackgroundActivity.sensorValueY) && !((currentTouchingState & Bounds.BOTTOM) == Bounds.BOTTOM)) {
                        //System.out.println("Pointing upwards");
                        MovableBall.getInstance().moveDown(BallBackgroundActivity.sensorValueY);
                    }
                    if (pointingDownwards(BallBackgroundActivity.sensorValueY) && !((currentTouchingState & Bounds.TOP) == Bounds.TOP)) {
                        //System.out.println("Pointing downwards");
                        MovableBall.getInstance().moveUp(BallBackgroundActivity.sensorValueY);
                    }

                }

                if (hasSensorValueXChanged(BallBackgroundActivity.sensorValueX)) {
                    if (pointingRight(BallBackgroundActivity.sensorValueX) && !((currentTouchingState & Bounds.RIGHT) == Bounds.RIGHT)) {
                        //System.out.println("Pointing to right");
                        MovableBall.getInstance().moveRight(BallBackgroundActivity.sensorValueX);
                    }
                    if (pointingLeft(BallBackgroundActivity.sensorValueX) && !((currentTouchingState & Bounds.LEFT) == Bounds.LEFT)) {
                        //System.out.println("Pointing to left");
                        MovableBall.getInstance().moveLeft(BallBackgroundActivity.sensorValueX);
                    }
                }
            }
            else{
                ForceHandler.getInstance().resetForce();
                float force = ForceHandler.getInstance().getForce();

                if(moveMode == MoveMode.MustTop)
                {
                    MovableBall.getInstance().moveUp(force);
                }
                else if(moveMode == MoveMode.MustBottom)
                {
                    MovableBall.getInstance().moveDown(force);
                }
                else if((moveMode == MoveMode.MustRight))
                {
                    MovableBall.getInstance().moveRight(force);
                }
                else if((moveMode == MoveMode.MustLeft))
                {
                    MovableBall.getInstance().moveLeft(force);
                }
            }

            circle = MovableBall.getInstance().AsRectf();

            //System.out.println(MovableBall.getInstance().asString());
            canvas.drawOval(circle, p);
            invalidate();
        }

        private void setDefaultBallValuesTopLeft() {
            MovableBall.getInstance().setX1(getScreenWidth() / 2);
            MovableBall.getInstance().setY1(getScreenHeight() / 2);
            MovableBall.getInstance().setX2(ballWidth + getScreenWidth() / 2);
            MovableBall.getInstance().setY2(ballHeight + getScreenHeight() / 2);
        }
    }

}