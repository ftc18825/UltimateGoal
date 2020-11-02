package ug.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class WobbleGrabber extends Mechanism{

    Servo wobbleGrabber;

    double wobbleUp;
    double wobbleDown;

    public double wobblePos;

    public int editing;
    double updateVal;

    public WobbleGrabber(String n, HardwareMap hardwareMap){
        super(n,hardwareMap);

        wobbleGrabber = getHwServo("wobbleGrabber");

    }

    public void start(){

    }

    public void stop(){

    }

    public void init(){
        wobbleUp = 0;
        wobbleDown = 0;
        wobblePos = 0;

        editing = 0;
        updateVal = 0.002;

        wobbleGrabber.setPosition(wobbleUp);
    }

    public void allDown(){
        wobbleGrabber.setPosition(wobbleDown);
    }

    public void allUp(){
        wobbleGrabber.setPosition(wobbleUp);
    }

    public void testDown(){
        if(getSelected().equals("wobble")){
            wobblePos -= updateVal;
            wobbleGrabber.setPosition(wobblePos);
        }
    }

    public void testUp(){
        if(getSelected().equals("wobble")){
            wobblePos += updateVal;
            wobbleGrabber.setPosition(wobblePos);
        }
    }

    public String getSelected(){
        if(editing%5==0){
            return "wobble";
        }
        return "wobble";
    }

}
