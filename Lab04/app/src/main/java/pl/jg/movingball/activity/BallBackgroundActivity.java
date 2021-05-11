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

import pl.jg.movingball.helpers.Ball;
import pl.jg.movingball.helpers.Bounds;

public class BallBackgroundActivity extends Activity implements SensorEventListener
{
    BallView ball = null;
    ShapeDrawable mDrawable = new ShapeDrawable();
    public static float sensorValueX;
    public static float sensorValueY;

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
                sensorValueX = sensorEvent.values[1];
                sensorValueY = sensorEvent.values[2];
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
        private boolean firstDraw = true;

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



        public boolean hasSensorValueXChanged(float newSensorX){
            if (Math.abs(newSensorX - lastSensorValueX) > THRESHOLD)
            {
                lastSensorValueX = newSensorX;
                return true;
            }
            return false;
        }

        public boolean pointsUpwards(float newSensorValueX)
        {
            return newSensorValueX > 0;
        }


        protected void onDraw(Canvas canvas)
        {

            System.out.println(String.format("sensor wychylenie X: <{%f}>", BallBackgroundActivity.sensorValueX));
            System.out.println(String.format("sensor wychylenie Y: <{%f}>", BallBackgroundActivity.sensorValueY));

            if(firstDraw == true)
            {
                setDefaultBallValuesTopLeft();
                circle = Ball.AsRectf();
                p = new Paint();
                p.setColor(Color.GREEN);
                firstDraw = false;
                Bounds.setMargin(ballHeight);
                Bounds.setHeight(getScreenHeight());
                Bounds.setWidth(getScreenWidth());
            }

            if(hasSensorValueXChanged(BallBackgroundActivity.sensorValueX))
            {
                if(pointsUpwards(BallBackgroundActivity.sensorValueX))
                {
                    Ball.moveDown(BallBackgroundActivity.sensorValueX);
                    circle = Ball.AsRectf();
                }
            }



            System.out.println(Ball.asString());
            canvas.drawOval(circle, p);
            invalidate();
        }

        private void setDefaultBallValuesTopLeft() {
            Ball.setX1(0);
            Ball.setY1(0);
            Ball.setX2(ballWidth);
            Ball.setY2(ballHeight);
        }
    }

}