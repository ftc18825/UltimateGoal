package ug.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Double.NaN;

public class Arm extends Mechanism{

    Servo shoulder;
    Servo elbow;
    Servo wrist;
    Servo hand;
    double currentTargetXPos;
    double currentTargetYPos;
    double currentTargetRotation;

    public double shoulderPos;
    public double elbowPos;
    public double wristPos;
    public double handPos;

    double shelbowInPos;
    double shelbowOutPos;
    double shelbowGrabPos;

    double shoulderLength;
    double elbowLength;
    double shoulderAngle;
    double elbowAngle;


    double shoulderZero;
    double shoulder90;
    double elbowZero;
    double elbow90;
    double wristZero;
    double wrist90;

    double updateValue;

    public boolean isOut;
    public boolean isAtCapstone;
    public boolean shelbowIsFullOut;
    //double xyUpdateValue;

    public Arm(String n,HardwareMap hardwareMap){
        super(n, hardwareMap);
        shoulder = getHwServo("shoulder");
        elbow = getHwServo("elbow");
        //wrist = getHwServo("wrist");
        hand = getHwServo("hand");
        /*hmp.put(mName("ShoulderReset"), new Param(0.5));
        hmp.put(mName("ElbowReset"), new Param(0.5));
        hmp.put(mName("WristReset"), new Param(0.5));
        hmp.put(mName("HandReset"), new Param(0.5));
        hmp.get(mName("ShoulderReset")).setStandardServo();
        hmp.get(mName("ElbowReset")).setStandardServo();
        hmp.get(mName("WristReset")).setStandardServo();
        hmp.get(mName("HandReset")).setStandardServo();*/
    }

    public void init(){
        shoulderPos = 0.5;
        elbowPos = 0.5;
        wristPos = 0.5;
        handPos = 0.5;
        updateValue = 0.005/4;
        //xyUpdateValue = 0.05;

        shoulderZero = .7275;
        shoulder90 = .21625;
        elbowZero = .77625;
        elbow90 = .2775;
        wristZero = .61375;
        wrist90 = .96375;

        shoulderLength = 173.0375;
        elbowLength = 173.0375;

        currentTargetXPos = 0;
        currentTargetYPos = 0;
        currentTargetRotation = 0;

        shelbowInPos = 0.80625;
        shelbowOutPos = 0.055;
        shelbowGrabPos = 0.84625;

        //reset();
        release();

        isOut = false;
        isAtCapstone = false;
        shelbowIsFullOut = isOut;
    }

    public void start(){

    }

    public void stop(){

    }

    public void nudgeArm(double x, double y , double rotation){
        rotation += 90;
        double radian = Math.PI/180;
        currentTargetXPos += 5*x*Math.cos(rotation*radian) + 5*y*Math.sin(rotation*radian);
        currentTargetYPos += 5*y*Math.cos(rotation*radian) - 5*x*Math.sin(rotation*radian);

        moveToXYRotation(currentTargetXPos,currentTargetYPos , currentTargetRotation);

    }

    public void shelbowIn(){
        shoulder.setPosition(shelbowInPos);
        shelbowIsFullOut = false;
    }

    public void shelbowGrab(){
        shoulder.setPosition(shelbowGrabPos);
        shelbowIsFullOut = false;
    }

    public void shelbowOut(){
        shoulder.setPosition(shelbowOutPos);
        shelbowIsFullOut = true;
    }

    public void shelbowOutSlow(double milli){
        double endTime = 1500;
        if(milli < endTime) {
            shoulder.setPosition(((shelbowInPos - shelbowOutPos) / (endTime * endTime)) * ((milli - endTime) * (milli - endTime)) + shelbowOutPos);
            shelbowIsFullOut = false;
        }else{
            shoulder.setPosition(shelbowOutPos);
            shelbowIsFullOut = true;
            armIsOut();
        }
    }

    public void armIsOut(){
        isOut = true;
    }

    public void armIsIn(){
        isOut = false;
    }

    public void armIsAtCapstone(){
        isAtCapstone = true;
    }

    public void armIsNotAtCapstone(){
        isAtCapstone = false;
    }

    public void reset(){
        //moveToXYRotation(-244,110,180);
        shelbowIn();
    }

    public void resetToCapstone(){
        moveToXYRotation(27,230,180);
    }

    public void moveJengaFar(){
        moveToXYRotation(325,62.65,92.6);
    }

    public void moveJengaNear(){
        moveToXYRotation(232,62,92.6);
    }

    public void moveJengaLeft(){
        moveToXYRotation(193.35,165.26,-5.4);
    }

    public void moveJengaRight(){
        moveToXYRotation(209,67,-8);
    }

    public void shoulderIncrease(){
        shoulderPos += updateValue;
        if(shoulderPos>1){
            shoulderPos = 1;
        }
        shoulder.setPosition(shoulderPos);
    }

    public void shoulderDecrease(){
        shoulderPos -= updateValue;
        if(shoulderPos<0){
            shoulderPos = 0;
        }
        shoulder.setPosition(shoulderPos);
    }

    public void elbowIncrease(){
        elbowPos += updateValue;
        if(elbowPos>1){
            elbowPos = 1;
        }
        elbow.setPosition(elbowPos);
    }

    public void elbowDecrease(){
        elbowPos -= updateValue;
        if(elbowPos<0){
            elbowPos = 0;
        }
        elbow.setPosition(elbowPos);
    }

    public void wristIncrease(){
        wristPos += updateValue;
        if(wristPos>1){
            wristPos = 1;
        }
        wrist.setPosition(wristPos);
    }

    public void wrsitDecrease(){
        wristPos -= updateValue;
        if(wristPos<0){
            wristPos = 0;
        }
        wrist.setPosition(wristPos);
    }

    public void handIncrease(){
        handPos += updateValue;
        if(handPos>1){
            handPos = 1;
        }
        hand.setPosition(handPos);
    }

    public void handDecrease(){
        handPos -= updateValue;
        if(handPos<0){
            handPos = 0;
        }
        hand.setPosition(handPos);
    }

    public void grab(){
        //shelbowGrab();
        hand.setPosition(.2225);
    }

    public void releaseHigh(){
        hand.setPosition(.8);
    }

    public void release(){
        hand.setPosition(.4325);
    }


    public void shoulderMoveToAngle(double degrees){
        shoulderPos = (shoulder90-shoulderZero)/90*degrees+shoulderZero;
        if(shoulderPos < 0.)
            shoulderPos = 0.;
        if(shoulderPos >1)
            shoulderPos = 1;
        if(shoulderPos != NaN )
            shoulder.setPosition(shoulderPos);
    }

    public void elbowMoveToAngle(double degrees){
        elbowPos = (elbow90-elbowZero)/90*degrees+elbowZero;
        if(elbowPos < 0)
            elbowPos = 0;
        if(elbowPos > 1)
            elbowPos = 1;
        elbow.setPosition(elbowPos);
    }

    public void wristMoveToAngle(double degrees){
        wristPos = (wrist90-wristZero)/90*degrees+wristZero;
        if(wristPos < 0)
            wristPos = 0;
        if(wristPos > 1)
            wristPos = 1;
        wrist.setPosition(wristPos);
    }


    public void moveToXYRotation(double xMil, double yMil, double rotateDegrees){
        double value = (xMil*xMil+yMil*yMil-shoulderLength*shoulderLength-elbowLength*elbowLength)/(2*shoulderLength*elbowLength);
        if (value > 1)
            value = 1;
        else if (value < -1)
            value = -1;
        elbowAngle = (Math.acos(value))*360/(2*Math.PI);
        elbowMoveToAngle(elbowAngle);
        double elbowRadians = elbowAngle*(Math.PI*2)/360;
        shoulderAngle = (Math.atan2(yMil,xMil)-Math.atan2(elbowLength*Math.sin(elbowRadians),shoulderLength+elbowLength*Math.cos(elbowRadians)))*360/(2*Math.PI);
        shoulderMoveToAngle(shoulderAngle);
        wristMoveToAngle(rotateDegrees-shoulderAngle-elbowAngle);

        currentTargetXPos = xMil;
        currentTargetYPos = yMil;
        currentTargetRotation = rotateDegrees;
    }


}
