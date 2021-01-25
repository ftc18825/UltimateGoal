package ug.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake extends Mechanism {


    DcMotor intake;

    public Intake(String n,HardwareMap hardwareMap){
        super(n, hardwareMap);
        intake = getHwMotor("intake");
    }


    public void init(){

    }

    public void start(){

    }

    public void stop(){
        stopPower();
    }

    public void in(){
        intake.setPower(1);
    }
    public void out(){
        intake.setPower(-1);
    }
    public void stopPower(){
        intake.setPower(0);

    }
}
