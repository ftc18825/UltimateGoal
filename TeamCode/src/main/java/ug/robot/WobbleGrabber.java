package ug.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class WobbleGrabber extends Mechanism{

    public Servo wobbleGrabber;
    public Servo wobbleRotator;

    double wobbleGrabPos;
    double wobbleReleasePos;

    double rotateDownPos;
    double rotateOutPos;
    double rotateUpPos;

    public double wobblePos;

    public int editing;
    double updateVal;

    public WobbleGrabber(String n, HardwareMap hardwareMap){
        super(n,hardwareMap);

        wobbleGrabber = getHwServo("wobbleGrabber");
        wobbleRotator = getHwServo("wobbleRotator");
    }

    public void start(){

    }

    public void stop(){

    }

    public void init(){
        wobbleGrabPos = 0.367;
        wobbleReleasePos = 0.666;

        rotateDownPos = 0.777;
        rotateOutPos = 0.49;
        rotateUpPos = 0;

        wobblePos = 0;

        editing = 0;
        updateVal = 0.002;

        up();
        grab();
    }

    public void grab(){
        wobbleGrabber.setPosition(wobbleGrabPos);
    }

    public void release(){
        wobbleGrabber.setPosition(wobbleReleasePos);
    }

    public void up(){
        wobbleRotator.setPosition(rotateUpPos);
    }

    public void down(){
        wobbleRotator.setPosition(rotateDownPos);
    }

    public void out(){
        wobbleRotator.setPosition(rotateOutPos);
    }

    public void allDown(){
        wobbleGrabber.setPosition(wobbleReleasePos);
    }

    public void allUp(){
        wobbleGrabber.setPosition(wobbleGrabPos);
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
