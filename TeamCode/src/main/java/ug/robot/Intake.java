package ug.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends Mechanism {


    DcMotor leftIntake;
    DcMotor rightIntake;
    public Servo leftPusher;
    public Servo rightPusher;

    double leftIn;
    double leftOut;
    double rightIn;
    double rightOut;

    public Intake(String n,HardwareMap hardwareMap){
        super(n, hardwareMap);
        leftIntake = getHwMotor("leftIntake");
        rightIntake = getHwMotor("rightIntake");
        leftPusher = getHwServo("leftPusher");
        rightPusher = getHwServo("rightPusher");
    }


    public void init(){
        leftIn = 0.84;
        leftOut = .394;
        rightIn = 0;
        rightOut = 0.75;

        pushIn();
    }

    public void start(){

    }

    public void stop(){

    }

    public void pushOut(){
        leftPusher.setPosition(leftOut);
        rightPusher.setPosition(rightOut);
    }

    public void pushIn(){
        leftPusher.setPosition(leftIn);
        rightPusher.setPosition(rightIn);
    }

    public void in(){
        leftIntake.setPower(-1);
        rightIntake.setPower(1);
    }
    public void out(){
        leftIntake.setPower(0.5);
        rightIntake.setPower(-0.5);
    }
    public void stopPower(){
        rightIntake.setPower(0);
        leftIntake.setPower(0);

    }
}
