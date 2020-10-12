package ug.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import ug.util.Param;



public class Lift extends Mechanism {
    public DcMotor lift;
    //boolean encMode = false;
    int upPosEnc;
    private ElapsedTime runtime = new ElapsedTime();
    boolean isPowered;
    int lastMotorPos;
    int zeroMotorPos;
    int topMotorPos;
    int liftUpVal;
    public int currentTarget;
    double lastTimeNotStalled;

    public Lift(String n, HardwareMap hardwareMap) {
        super(n, hardwareMap);
        lift = getHwMotor("lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hmp.put(mName("Speed"), new Param(1.0));
        hmp.get(mName("Speed")).setStandardServo();
        hmp.put(mName("FullInVal"), new Param(0));
        hmp.put(mName("DumpVal"), new Param(850));
        hmp.put(mName("FullDownVal"), new Param(2250));
        isPowered = false;
        hmp.get(mName("FullInVal")).setStandardEnc();
        hmp.get(mName("DumpVal")).setStandardEnc();
        hmp.get(mName("FullDownVal")).setStandardEnc();

        liftUpVal = 2200;
        //Value is 600 for 3.7 Motor, 2200 for Neverest 20
    }

    public void init() {
        stop();
        runtime.reset();
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        zeroMotorPos = lift.getCurrentPosition();
        lastMotorPos = zeroMotorPos;
        lift.setPower(0);
        lift.setTargetPosition(zeroMotorPos);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lastTimeNotStalled = runtime.seconds();
        currentTarget = zeroMotorPos;
        topMotorPos = -3600;
    }

    public boolean aboveRobot(int encVal){
        if(-encVal > liftUpVal){
            return true;
        }
        return false;
    }

    public void lower() {
        isPowered = true;
        //lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setPower(getPVal("Speed")*1);
        //currentTarget -= 110;
        currentTarget = lift.getCurrentPosition()+500;
        if(currentTarget > zeroMotorPos+100){
            currentTarget=zeroMotorPos+100;
        }
        lift.setTargetPosition(currentTarget);
        //encMode = false;
    }

    public void raise() {
        isPowered = true;
        //lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setPower(getPVal("Speed")*1);
        currentTarget = lift.getCurrentPosition()-500;
        //currentTarget += 110;
        if(currentTarget < topMotorPos){
            currentTarget=topMotorPos;
        }
        lift.setTargetPosition(currentTarget);
        //encMode = false;
    }

    public void hold(){
        isPowered = true;
        lift.setPower(getPVal("Speed")/2);
        //currentTarget = lift.getCurrentPosition();
        //lift.setTargetPosition(currentTarget);
    }

    public void raiseFast() {
        isPowered = true;
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setPower(-getPVal("Speed")*0.8);
        //encMode = false;
    }

    public void stop() {
        //if(!encMode) {
        lift.setPower(0);
        //}
        isPowered = false;
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

    public void stopAndReset(){
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
