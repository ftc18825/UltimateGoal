package ug.util;

public class Param {

    private double value;
    boolean hasMax;
    double max;
    boolean hasMin;
    double min;
    double updateStep;

    public Param (double v){
        value = v;
        hasMax = false;
        max = 1;
        hasMin = false;
        min = -1;
        updateStep = 1;
    }

    public void setValue(double input){
        if(hasMax && input >= max) {
            value = max;
        }else if(hasMin && input <= min){
            value = min;
        }else{
            value = input;
        }
    }
    public double getValue(){
        return value;
    }

    public void setStandardServo(){
        setMin(0);
        setMax(1);
        updateStep = 0.05;
    }

    public void setStandardEnc(){
        hasMin = false;
        hasMax = false;
        updateStep = 50;
    }

    public void setMin(double newMin){
        hasMin = true;
        min = newMin;
    }

    public void setMax(double newMax){
        hasMax = true;
        max = newMax;
    }

    public void setUpdateStep(double u){
        updateStep=u;
    }

    public void increaseByStep(){
        setValue(value + updateStep);
    }

    public void decreaseByStep(){
        setValue(value - updateStep);
    }

}
