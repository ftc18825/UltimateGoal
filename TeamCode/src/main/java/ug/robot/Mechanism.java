package ug.robot;

import ug.util.Param;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.HashMap;

public class Mechanism {

    public String name = null;
    public HardwareMap  hardwareMap;
    public HashMap<String, Param> hmp;
    public HashMap<String,Servo> hms;
    public HashMap<String,DcMotor> hmm;

    public Mechanism (String n, HardwareMap h){
        name = n;
        hardwareMap = h;
        hmp = new HashMap<String, ug.util.Param>();
        hms = new HashMap<String,Servo>();
        hmm = new HashMap<String,DcMotor>();
    }

    public Mechanism(){

    }

    public void init(){

    }

    public void start(){

    }

    public void stop(){

    }

    public String mName(String s){
        return name + s;
    }

    public Servo getHwServo(String n){
        Servo s= hardwareMap.get(Servo.class, n);
        hms.put(mName(n),s);
        return s;
    }

    public DcMotor getHwMotor(String n){
        DcMotor s= hardwareMap.get(DcMotor.class, n);
        hmm.put(mName(n),s);
        return s;
    }

    public double getPVal(String s){
        return hmp.get(mName(s)).getValue();
    }
}
