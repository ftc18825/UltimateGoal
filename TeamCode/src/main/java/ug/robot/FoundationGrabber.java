package ug.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class FoundationGrabber extends Mechanism{

    Servo left;
    Servo right;
    Servo middle;

    Servo leftFang;
    Servo rightFang;

    double leftDown;
    double rightDown;
    double middleDown;
    double leftUp;
    double rightUp;
    double middleUp;
    double leftOut;
    double rightOut;
    double leftFangUp;
    double rightFangUp;
    double leftFangDown;
    double rightFangDown;
    double leftFangNubHeight;
    double rightFangNubHeight;

    double middleStone;
    double middleIn;
    double leftFangIn;
    double rightFangIn;

    public double leftPos;
    public double rightPos;
    public double middlePos;
    public double leftFangPos;
    public double rightFangPos;

    public int editing;
    double updateVal;

    public FoundationGrabber (String n, HardwareMap hardwareMap){
        super(n,hardwareMap);

        left = getHwServo("leftGrabber");
        right = getHwServo("rightGrabber");
        middle = getHwServo("middleGrabber");
        leftFang = getHwServo("leftFang");
        rightFang = getHwServo("rightFang");

    }

    public void start(){

    }

    public void stop(){

    }

    public void init(){
        leftDown = .58;
        rightDown = .464;
        middleDown = .7;
        leftUp = .318;
        rightUp = .69;
        middleUp = .158;
        leftOut = 1;
        rightOut = 0;
        leftFangUp = .154;
        rightFangUp = 0.666;
        leftFangDown = 0.74;
        rightFangDown = 0.05;
        leftFangNubHeight = .685;
        rightFangNubHeight = 0.105;

        middleIn = .068;
        leftFangIn = .154;
        rightFangIn = .72;
        middleStone = .646;

        leftPos = 0.5;
        rightPos = 0.5;
        middlePos = 0.5;
        leftFangPos = 0.5;
        rightFangPos = 0.5;

        editing = 0;
        updateVal = 0.002;

        left.setPosition(leftUp);
        right.setPosition(rightUp);
        middle.setPosition(middleIn);
        leftFang.setPosition(leftFangIn);
        rightFang.setPosition(rightFangIn );
    }

    public void allOut(){
        left.setPosition(leftOut);
        right.setPosition(rightOut);
    }

    public void allDown(){
        left.setPosition(leftDown);
        right.setPosition(rightDown);
        middle.setPosition(middleDown);
        leftFang.setPosition(leftFangDown);
        rightFang.setPosition(rightFangDown);
    }

    public void allUp(){
        left.setPosition(leftUp);
        right.setPosition(rightUp);
        middle.setPosition(middleUp);
        leftFang.setPosition(leftFangUp);
        rightFang.setPosition(rightFangUp);
    }

    public void fgDown(){
        left.setPosition(leftDown);
        right.setPosition(rightDown);
    }

    public void fgUp(){
        left.setPosition(leftUp);
        right.setPosition(rightUp);
    }

    public void fangsDown(){
        leftFang.setPosition(leftFangDown);
        rightFang.setPosition(rightFangDown);
    }

    public void fangsUp(){
        leftFang.setPosition(leftFangUp);
        rightFang.setPosition(rightFangUp);
    }
    public void fangsNubs(){
        leftFang.setPosition(leftFangNubHeight);
        rightFang.setPosition(rightFangNubHeight);
    }

    public void stonePrepare(){
        left.setPosition(leftDown);
        right.setPosition(rightDown);
        middle.setPosition(middleUp);
    }

    public void middleToStone(){
        middle.setPosition(middleStone);
    }

    public void middleUp(){
        middle.setPosition(middleUp);
    }

    public void testDown(){
        if(getSelected().equals("left")){
            leftPos -= updateVal;
            left.setPosition(leftPos);
        }else if(getSelected().equals("right")){
            rightPos -= updateVal;
            right.setPosition(rightPos);
        }else if(getSelected().equals("middle")){
            middlePos -= updateVal;
            middle.setPosition(middlePos);
        }else if(getSelected().equals("leftFang")){
            leftFangPos -= updateVal;
            leftFang.setPosition(leftFangPos);
        }else{
            rightFangPos -= updateVal;
            rightFang.setPosition(rightFangPos);
        }
    }

    public void testUp(){
        if(getSelected().equals("left")){
            leftPos += updateVal;
            left.setPosition(leftPos);
        }else if(getSelected().equals("right")){
            rightPos += updateVal;
            right.setPosition(rightPos);
        }else if(getSelected().equals("middle")){
            middlePos += updateVal;
            middle.setPosition(middlePos);
        }else if(getSelected().equals("leftFang")){
            leftFangPos += updateVal;
            leftFang.setPosition(leftFangPos);
        }else{
            rightFangPos += updateVal;
            rightFang.setPosition(rightFangPos);
        }
    }

    public String getSelected(){
        if(editing%5==0){
            return "left";
        }else if(editing%5==1){
            return "right";
        }else if(editing%5==2){
            return "middle";
        }else if(editing%5==3){
            return "leftFang";
        }else{
            return "rightFang";
        }
    }

}
