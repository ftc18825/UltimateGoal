package ug.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import ug.robot.sensors.Gyro;
import ug.util.Param;

public class DriveTrain extends Mechanism {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public boolean frontLeftFwd, frontRightFwd, backLeftFwd, backRightFwd;

    int headingOffset;


    public DriveTrain(String n, HardwareMap hardwareMap) {
        //name = n;
        super(n, hardwareMap);
        frontLeft = getHwMotor("frontLeft");
        frontRight = getHwMotor("frontRight");
        backLeft = getHwMotor("backLeft");
        backRight = getHwMotor("backRight");
        hmp.put(mName("TileEnc"), new Param(914));
        hmp.get(mName("TileEnc")).setStandardEnc();
    }

    public void init() {

        frontLeftFwd = false;
        frontRightFwd = true;
        backLeftFwd = false;
        backRightFwd = true;

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if (frontLeftFwd)
            frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        if (frontRightFwd)
            frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        if (backLeftFwd)
            backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        if (backRightFwd)
            backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        headingOffset = 0;

        stop();
    }

    public void holoDrive(double x, double y, double turny) {
        frontLeft.setPower(-y - x - turny);
        frontRight.setPower(-y + x + turny);
        backLeft.setPower(-y + x - turny);
        backRight.setPower(-y - x + turny);
    }

    public void holoGyro(double x, double y, double turny, int heading) {
        heading += headingOffset;
        double headingX = x * Math.cos(((double) heading) / 180.0 * Math.PI) + y * Math.sin(((double) heading) / 180.0 * Math.PI);
        double headingY = y * Math.cos(((double) heading) / 180.0 * Math.PI) - x * Math.sin(((double) heading) / 180.0 * Math.PI);
        holoDrive(headingX, headingY, turny);
    }

    public boolean turnToDegrees(double degrees , Gyro gyro , OpMode om , double speed ){
        double targetDegrees = degrees - 90 - gyro.getHeading();
        double maxDeg = 40;
        boolean done = false;
        while (targetDegrees > 180)
            targetDegrees -= 360;
        while (targetDegrees < -180)
            targetDegrees += 360;

        if(targetDegrees > maxDeg){
            holoDrive(0,0,-speed);
        }else if(targetDegrees < -maxDeg){
            holoDrive(0,0,speed);
        }else if(targetDegrees > 2.5){
            holoDrive(0,0,-speed*(1/5+(targetDegrees-2.5)/(maxDeg-2.5)*4/5));
        }else if(targetDegrees < -2.5){
            holoDrive(0,0,speed*(1/5+(-targetDegrees-2.5)/(maxDeg-2.5)*4/5));
        }else{
            stop();
            done = true;
        }

        om.telemetry.addData("Degrees off: " , degrees-gyro.getHeading());
        om.telemetry.addData("Degrees: " , degrees);
        om.telemetry.addData("Target Degrees: " , targetDegrees);
        //om.telemetry.update();

        return done;
    }

    public void turnDegrees(double degrees, Gyro gyro, LinearOpMode om , double speed) {
        double startTime = om.getRuntime();
        double startingDegrees = -gyro.getHeading();
        double currentDegrees = startingDegrees;
        double turnedDegrees = currentDegrees - startingDegrees;
        if (turnedDegrees > 180)
            turnedDegrees -= 360;
        if (turnedDegrees < -180)
            turnedDegrees += 360;
        if (degrees > 1) {
            holoDrive(0, 0, speed);
            while (om.opModeIsActive() && om.getRuntime() - startTime < 3 && turnedDegrees < degrees) {
                turnedDegrees = -gyro.getHeading() - startingDegrees;
                if (turnedDegrees > 180)
                    turnedDegrees -= 360;
                if (turnedDegrees < -180)
                    turnedDegrees += 360;
                om.telemetry.addData("Starting Degrees: ", startingDegrees);
                om.telemetry.addData("Current Degrees: ", currentDegrees);
                om.telemetry.addData("Turned Degrees: ", turnedDegrees);
                om.telemetry.addData("Gyro Heading: ", gyro.getHeading());
                om.telemetry.update();
            }
        } else if (degrees < -1) {
            holoDrive(0, 0, -speed);
            while (om.opModeIsActive() && om.getRuntime() - startTime < 3 && turnedDegrees > degrees) {
                turnedDegrees = -gyro.getHeading() - startingDegrees;
                if (turnedDegrees > 180)
                    turnedDegrees -= 360;
                if (turnedDegrees < -180)
                    turnedDegrees += 360;
                om.telemetry.addData("Starting Degrees: ", startingDegrees);
                om.telemetry.addData("Current Degrees: ", currentDegrees);
                om.telemetry.addData("Turned Degrees: ", turnedDegrees);
                om.telemetry.addData("Gyro Heading: ", gyro.getHeading());
                om.telemetry.update();
            }
        } else {

        }
        stop();
    }

    public void moveEnc(double speed, double tiles, LinearOpMode om , Gyro gyro) {
        double startTime = om.getRuntime();
        int totalEnc = (int) (tiles * getPVal("TileEnc"));
        int currentEnc = frontLeft.getCurrentPosition();
        int startEnc = currentEnc;
        while (Math.abs(startEnc - currentEnc)  < totalEnc && om.opModeIsActive() && om.getRuntime() - startTime < 3 * tiles) {
            holoGyro(0, speed, 0,(int)gyro.getHeading());
            currentEnc = frontLeft.getCurrentPosition();
            om.telemetry.addData("EncValue ", currentEnc - startEnc);
            om.telemetry.update();
        }
        stop();

    }

    public void strafeEnc(double speed, double tiles, LinearOpMode om,Gyro gyro) {
        double startTime = om.getRuntime();
        int totalEnc = (int) (tiles * getPVal("TileEnc"));
        int currentEnc = frontLeft.getCurrentPosition();
        int startEnc = currentEnc;
        while (Math.abs(startEnc - currentEnc)  < totalEnc && om.opModeIsActive() && om.getRuntime() - startTime < 3 * tiles) {
            holoGyro(speed, 0, 0,(int)gyro.getHeading());
            currentEnc = frontLeft.getCurrentPosition();
            om.telemetry.addData("EncValue ", currentEnc - startEnc);
            om.telemetry.update();
        }
        stop();
    }

    public void start() {

    }

    public void stop() {
        holoDrive(0, 0, 0);
    }

    public void setHeadingOffset(int h) {
        headingOffset = h;
    }


    public void changeFrontLeft(){
        frontLeftFwd = !frontLeftFwd;

        if (frontLeftFwd)
            frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void changeFrontRight(){
        frontRightFwd = !frontRightFwd;

        if (frontRightFwd)
            frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void changeBackLeft(){
        backLeftFwd = !backLeftFwd;

        if (backLeftFwd)
            backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void changeBackRight(){
        backRightFwd = !backRightFwd;

        if (backRightFwd)
            backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            backRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }
}
