package pl.jg.movingball.helpers;

public class ForceHandler {

    private static ForceHandler _instance;

    public static ForceHandler getInstance() {
        if(_instance == null) {
            _instance = new ForceHandler();
        }
        return _instance;
    }

    public void resetForce(){
        this.force = 2.5f;
    }

    public void decrementForce(){
        this.force -= 0.5;

    }


    public float getForce() {
        return force;
    }

    public void setForce(float force) {
        this.force = force;
    }

    private float force;

}
