package ug.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class CapstoneDumper extends Mechanism{

    public Servo capstoneDumper;

    double upPos;
    double dumpPos;

    public CapstoneDumper(String n, HardwareMap hardwareMap){
        super(n,hardwareMap);

        capstoneDumper = getHwServo("capstoneDumper");

    }

    public void start(){

    }

    public void stop(){

    }

    public void init(){
        dumpPos = .218;
        upPos = .73;
    }

    public void up(){
        capstoneDumper.setPosition(upPos);
    }

    public void dump(){
        capstoneDumper.setPosition(dumpPos);
    }

}
