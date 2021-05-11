package pl.jg.movingball.helpers;

import java.util.Timer;
import java.util.TimerTask;

public class Collider {

    private static Collider _instance;

    public MoveMode getMode() {
        return mode;
    }

    private MoveMode mode;

    private Collider(){

    }

    public static Collider getInstance() {
        if(_instance == null) {
            _instance = new Collider();
        }
        return _instance;
    }

    public MovableBall getBall() {
        return ball;
    }

    public void setBall(MovableBall ball) {
        this.ball = ball;
    }

    private MovableBall ball;

    public void setTouchingState(int newTouchingState) {
            calculateMoveMode(newTouchingState);
    }

    private boolean canChangeBallstate = true;

    public boolean isCanChangeBallstate() {
        return canChangeBallstate;
    }

    public void setCanChangeBallstate(boolean canChangeBallstate) {
        this.canChangeBallstate = canChangeBallstate;
    }

    private void setMode(MoveMode newMode) {
        if(canChangeBallstate) {
            this.mode = newMode;
            if (newMode != MoveMode.FreeFall) {
                canChangeBallstate = false;
                Timer timer = new Timer();
                TimerTask task = new CantChangeStateTimer(Collider.getInstance());
                timer.schedule(task, 200);
            }
            else{
                canChangeBallstate = true;
            }
        }
    }

    private void calculateMoveMode(int newTouchingState) {

        if(((newTouchingState & Bounds.BOTTOM) == Bounds.BOTTOM))
        {
            setMode(MoveMode.MustTop);
            return;
        }

        if(((newTouchingState & Bounds.TOP) == Bounds.TOP))
        {
            setMode(MoveMode.MustBottom);
            return;
        }

        if(((newTouchingState & Bounds.LEFT) == Bounds.LEFT))
        {
            setMode(MoveMode.MustRight);
            return;
        }

        if(((newTouchingState & Bounds.RIGHT) == Bounds.RIGHT))
        {
            setMode(MoveMode.MustLeft);
            return;
        }

        setMode(MoveMode.FreeFall);
    }

    public int touchingState;





}
