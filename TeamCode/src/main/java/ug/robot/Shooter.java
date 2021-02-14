package ug.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import ug.util.Param;



public class Shooter extends Mechanism {
    public DcMotor shooter;
    public Servo pusher;
    //boolean encMode = false;
    int upPosEnc;
    private ElapsedTime runtime = new ElapsedTime();
    boolean isPowered;
    int lastMotorPos;
    int zeroMotorPos;
    int topMotorPos;

    double pushIn;
    double pushOut;

    public int currentTarget;
    double lastTimeNotStalled;

    public Shooter(String n, HardwareMap hardwareMap) {
        super(n, hardwareMap);
        shooter = getHwMotor("shooter");
        //shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pusher = getHwServo("pusher");
        hmp.put(mName("Speed"), new Param(1.0));
        hmp.get(mName("Speed")).setStandardServo();
        isPowered = false;

        //Value is 600 for 3.7 Motor, 2200 for Neverest 20
    }

    public void init() {
        stop();
        runtime.reset();
        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //zeroMotorPos = shooter.getCurrentPosition();
        //lastMotorPos = zeroMotorPos;
        shooter.setPower(0);
        //shooter.setTargetPosition(0);
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lastTimeNotStalled = runtime.seconds();
        //currentTarget = zeroMotorPos;
        //topMotorPos = -3600;
        pushIn = .6;
        pushOut = .95;

        retract();
    }

    public void shoot(){
        pusher.setPosition(pushIn);
    }

    public void retract(){
        pusher.setPosition(pushOut);
    }

    public void powerOn() {
        isPowered = true;
        //shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter.setPower(1);
    }

    public void powerOff() {
        isPowered = false;
        //shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        stop();
    }

    public void start(){

    }

    public void stop() {
        //if(!encMode) {
        shooter.setPower(0);
        //}
        isPowered = false;
    }

    public void hold(){
        isPowered = true;
        shooter.setPower(getPVal("Speed")/2);
    }

    /*
    public boolean aboveRobot(int encVal){
        if(-encVal > liftUpVal){
            return true;
        }
        return false;
    }

    public void raiseFast() {
        isPowered = true;
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setPower(-getPVal("Speed")*0.8);
        //encMode = false;
    }

    public void fullIn(){
        isPowered = true;
        lift.setPower(getPVal("Speed"));
        lift.setTargetPosition(upPosEnc);
        //encMode = true;
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void goToDump(){
        isPowered = true;
        lift.setPower(getPVal("Speed"));
        lift.setTargetPosition(upPosEnc + (int)(getPVal("DumpVal")));
        //encMode = true;
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void fullDown(){
        isPowered = true;
        lift.setPower(getPVal("Speed"));
        lift.setTargetPosition(upPosEnc + (int)(getPVal("FullDownVal")));
        //encMode = true;
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void startingUp(){
        upPosEnc = lift.getCurrentPosition();
    }

    public void startingDown(){
        upPosEnc = lift.getCurrentPosition()- (int)getPVal("FullDownVal")-150;
    }

    public void stopIfStalled(){
        if(!isStalledNow())
            lastTimeNotStalled = runtime.seconds();
        if(isStalledNow()&&runtime.seconds()-lastTimeNotStalled > 0.3){
            stop();
        }
    }

    public boolean isStalledNow(){
        if(!isPowered) {
            lastMotorPos = lift.getCurrentPosition();
            return false;
        }
        if(Math.abs(lastMotorPos - lift.getCurrentPosition())<5 ){
            lastMotorPos = lift.getCurrentPosition();
            return true;
        }
        lastMotorPos = lift.getCurrentPosition();
        return false;
    }

    public void autonFullDown(LinearOpMode om){
        int startPos = lift.getCurrentPosition();
        lower();
        double startTime = om.getRuntime();
        while(lift.getCurrentPosition()-startPos < getPVal("FullDownVal") && om.opModeIsActive() && om.getRuntime()-startTime < 5){
            om.telemetry.addData("lift: ", "lifting " + (lift.getCurrentPosition()-startPos));
            om.telemetry.update();
            //stopIfStalled();
        }
        om.telemetry.addData("lift: ", "stopped");
        om.telemetry.update();
        stop();
        if(lift.getCurrentPosition()-startPos < getPVal("FullDownVal")){
            om.stop();
        }
    }

    public void autonFullUp(LinearOpMode om){
        int startPos = lift.getCurrentPosition();
        raiseFast();
        double startTime = om.getRuntime();
        while((lift.getCurrentPosition()-startPos) > -getPVal("FullDownVal") && om.opModeIsActive() && om.getRuntime()-startTime < 5){
            om.telemetry.addData("lift: ", "lifting " + ((lift.getCurrentPosition()-startPos)));
            om.telemetry.update();
            //stopIfStalled();
        }
        om.telemetry.addData("lift: ", "stopped");
        om.telemetry.update();
        stop();
        if((lift.getCurrentPosition()-startPos) > -getPVal("FullDownVal")){
            om.stop();
        }
    }
    */
    public void stopAndReset(){
        shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
