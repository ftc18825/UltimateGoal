/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ug.opMode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import ug.util.Param;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="CameraStoneAuton", group="Camera")
@Disabled
public class CameraStoneAuton extends UGAuton {
    boolean twoStone = false;
    double startTime = 0;

    double blueQuarryX = -30;
    double blueQuarryY = 44;
    double blueQuarryAngle = -180;

    double blueFoundationX = 50;
    double blueFoundationY = 32.6;

    public CameraStoneAuton(){
        super();
        paramFileName = "CameraStoneParams";
        //hmp = new HashMap<String, Param>();
        /*
        hmp.put("FastSpeed",new Param(0.7));
        setParamUpdateStep("FastSpeed",0.05);
        hmp.put("SlowSpeed",new Param(0.3));
        setParamUpdateStep("SlowSpeed",0.05);
        hmp.put("TurnSpeed",new Param(1));
        setParamUpdateStep("TurnSpeed",0.05);
        hmp.put("ScaleTiles",new Param(1.2));
        setParamUpdateStep("ScaleTiles",0.01);
        hmp.put("Distance1", new Param(0.5));
        setParamUpdateStep("Distance1", .1);
        hmp.put("Distance2aRed", new Param(-0.2));
        setParamUpdateStep("Distance2aRed", .05);
        hmp.put("Distance2bRed", new Param(0.2));
        setParamUpdateStep("Distance2bRed", .05);
        hmp.put("Distance2cRed", new Param(-0.5));
        setParamUpdateStep("Distance2cRed", .05);
        hmp.put("Distance2aBlue", new Param(-0.2));
        setParamUpdateStep("Distance2aBlue", .05);
        hmp.put("Distance2bBlue", new Param(0.2));
        setParamUpdateStep("Distance2bBlue", .05);
        hmp.put("Distance2cBlue", new Param(-0.5));
        setParamUpdateStep("Distance2cBlue", .05);
        hmp.put("Distance3Red", new Param(1.2));
        setParamUpdateStep("Distance3Red", .1);
        hmp.put("Distance3Blue", new Param(1.0));
        setParamUpdateStep("Distance3Blue", .1);
        hmp.put("Distance4", new Param(0.6));
        setParamUpdateStep("Distance4", .1);
        hmp.put("Distance5", new Param(1.1));
        setParamUpdateStep("Distance5", .1);
        hmp.put("Distance6Cam", new Param(3.0));
        setParamUpdateStep("Distance6Cam", .1);
        hmp.put("Distance6NoC", new Param(3.5));
        setParamUpdateStep("Distance6NoC", .1);
        hmp.put("NoTargetX", new Param(1));
        setParamUpdateStep("NoTargetX", .1);
        hmp.put("NoTargetY", new Param(1));
        setParamUpdateStep("NoTargetY", .1);
        hmp.put("RedOffset", new Param(.75));
        setParamUpdateStep("RedOffset", .05);
        hmp.put("BackAfterFoundation", new Param(1.5));
        setParamUpdateStep("BackAfterFoundation", .05);
        hmp.put("ParkTiles", new Param(1.5));
        setParamUpdateStep("ParkTiles", .05);
*/
    }

    @Override
    public void runMe(){
        /*
        //Moving to Stone
        rob.placer.arm.shelbowIn();
        rob.intake.pushOut();
        forwardTiles(getPVal("Distance1"),getPVal("FastSpeed"));
        //rob.intake.pushIn();
        int targetDegrees = 0;
        if(isRed)
            targetDegrees = 180;
        startTime = runtime.milliseconds();
        while(opModeIsActive() && runtime.milliseconds()-startTime < 1500){
            rob.driveTrain.turnToDegrees(targetDegrees,rob.gyro,this,getPVal("TurnSpeed"));
        }
        rob.driveTrain.stop();
        //rob.intake.pushOut();
        double d;
        if(color == 0){

            if(isRed) {
                d = getPVal("Distance2aRed");
                d = -d;
            }else{
                d = getPVal("Distance2aBlue");
            }
            if(d > 0){
                rightTiles(d,getPVal("SlowSpeed"));
            }else{
                leftTiles(-d,getPVal("SlowSpeed"));
            }
        }else if(color == 1){
            if(isRed) {
                d = getPVal("Distance2bRed");
                d = -d;
            }else{
                d = getPVal("Distance2bBlue");
            }
            if(d > 0){
                rightTiles(d,getPVal("SlowSpeed"));
            }else{
                leftTiles(-d,getPVal("SlowSpeed"));
            }
        }else{
            if(isRed) {
                d = getPVal("Distance2cRed");
                d = -d;
            }else{
                d = getPVal("Distance2cBlue");
            }
            if(d > 0){
                rightTiles(d,getPVal("SlowSpeed"));
            }else{
                leftTiles(-d,getPVal("SlowSpeed"));
            }
        }



        sleep(250);
        if(isRed)
            forwardTiles(getPVal("Distance3Red"),getPVal("SlowSpeed"));
        else
            forwardTiles(getPVal("Distance3Blue"),getPVal("SlowSpeed"));
        sleep(250);

        //Grabbing Stone
        rob.intake.in();
        if(!isRed) {
            rightTiles(getPVal("Distance4"), getPVal("SlowSpeed"));
        }else{
            leftTiles(getPVal("Distance4"), getPVal("SlowSpeed"));
        }

        rob.intake.pushIn();

        //Moving to Foundation
        sleep(250);
        backTiles(getPVal("Distance5"),getPVal("SlowSpeed"));
        rob.intake.stopPower();
        sleep(250);
        rob.placer.arm.grab();
        startTime = runtime.milliseconds();
        while(opModeIsActive() && runtime.milliseconds()-startTime < 500){
            rob.driveTrain.turnToDegrees(targetDegrees,rob.gyro,this,getPVal("TurnSpeed"));
        }
        rob.driveTrain.stop();
        */
        /*while(runtime.milliseconds()<20000){
            telemetry.addData("Seeing Camera: ",rob.vision.getPosition(this));
            telemetry.update();
        }*/

        sleep(500);
        /*
        if(false){//rob.vision.getPosition(this)){
            double xDistance = (blueQuarryX-rob.vision.posX) / 24.0;
            double yDistance;
            if(isRed) {
                yDistance = (-blueQuarryY - rob.vision.posY) / 24.0;
            }else{
                yDistance = (blueQuarryY - rob.vision.posY) / 24.0;
            }
            double angleDrive = (blueQuarryAngle-rob.vision.posAngle);
        */
            /*targetDegrees = 0;
            if(isRed)
                targetDegrees = 180;
            startTime = runtime.milliseconds();
            while(opModeIsActive() && runtime.milliseconds()-startTime < 1500){
                rob.driveTrain.turnToDegrees(targetDegrees,rob.gyro,this,getPVal("TurnSpeed"));
            }*/
            /*
            if(!isRed){
                xDistance*=-1;
                yDistance*=-1;
            }

            xDistance*=getPVal("ScaleTiles");
            yDistance*=getPVal("ScaleTiles");
            if(isRed){
                xDistance+=getPVal("Distance6Cam")-getPVal("RedOffset");
            }else {
                xDistance -= getPVal("Distance6Cam");
            }

            if(yDistance>0){
                forwardTiles(yDistance,getPVal("SlowSpeed"));
            }else{
                backTiles(-yDistance,getPVal("SlowSpeed"));
            }

            if(xDistance>0){
                rightTiles(xDistance,getPVal("FastSpeed"));
            }else{
                leftTiles(-xDistance,getPVal("FastSpeed"));
            }


        }else{
            sleep(500);
            if(isRed) {
                if (color == 0) {
                    rightTiles(getPVal("Distance6NoC") + getPVal("Distance2aRed")-getPVal("RedOffset"), getPVal("FastSpeed"));
                } else if (color == 1) {
                    rightTiles(getPVal("Distance6NoC") + getPVal("Distance2bRed")-getPVal("RedOffset"), getPVal("FastSpeed"));
                } else {
                    rightTiles(getPVal("Distance6NoC") + getPVal("Distance2cRed")-getPVal("RedOffset"), getPVal("FastSpeed"));
                }
            }else{
                if (color == 0) {
                    leftTiles(getPVal("Distance6NoC") + getPVal("Distance2aBlue"), getPVal("FastSpeed"));
                } else if (color == 1) {
                    leftTiles(getPVal("Distance6NoC") + getPVal("Distance2bBlue"), getPVal("FastSpeed"));
                } else {
                    leftTiles(getPVal("Distance6NoC") + getPVal("Distance2cBlue"), getPVal("FastSpeed"));
                }
            }
        }

        sleep(250);
        startTime = runtime.milliseconds();
        while(opModeIsActive() && runtime.milliseconds()-startTime < 1500){
            rob.driveTrain.turnToDegrees(270,rob.gyro,this,getPVal("TurnSpeed"));
        }
        rob.driveTrain.stop();
        */
        /*while(opModeIsActive()){
            telemetry.addData("Seeing Camera: ",rob.vision.getPosition(this));
            telemetry.addData("PosX: ",rob.vision.posX);
            telemetry.addData("PosY: ",rob.vision.posY);
            telemetry.update();
        }*/

        /*
        sleep(500);

        if(rob.vision.getPosition(this)){
            double xDistance = (blueFoundationX-rob.vision.posX) / 24.0;
            double yDistance;
            if(isRed) {
                yDistance = (-blueFoundationY - rob.vision.posY) / 24.0;
            }else{
                yDistance = (blueFoundationY - rob.vision.posY) / 24.0;
            }
            */
            /*targetDegrees = 0;
            if(isRed)
                targetDegrees = 180;
            startTime = runtime.milliseconds();
            while(opModeIsActive() && runtime.milliseconds()-startTime < 1500){
                rob.driveTrain.turnToDegrees(targetDegrees,rob.gyro,this,getPVal("TurnSpeed"));
            }*/
            /*
            if(!isRed){
                xDistance*=-1;
                yDistance*=-1;
            }

            xDistance*=getPVal("ScaleTiles");
            yDistance*=getPVal("ScaleTiles");

            if(xDistance>0){
                rightTiles(xDistance,getPVal("SlowSpeed"));
            }else{
                leftTiles(-xDistance,getPVal("SlowSpeed"));
            }

            if(yDistance>0){
                forwardTiles(yDistance,getPVal("SlowSpeed"));
            }else{
                backTiles(-yDistance,getPVal("SlowSpeed"));
            }

        }else{
            if(isRed) {
                rightTiles(getPVal("NoTargetX")+getPVal("RedOffset"),getPVal("SlowSpeed"));
            }else{
                leftTiles(getPVal("NoTargetX"),getPVal("SlowSpeed"));
            }
            forwardTiles(getPVal("NoTargetY"),getPVal("SlowSpeed"));
        }

        rob.wobbleGrabber.fangsNubs();
        rob.wobbleGrabber.fgDown();

        rob.driveTrain.stop();

        //sleep(500);
        //forwardTiles(.75,0.3);

        //Grabbing and moving Foundation
        //rob.foundationGrabber.fangsDown();
        //rob.foundationGrabber.fgDown();
        sleep(250);
        if(isRed) {
            leftTiles(0.35, 0.2);
        }else{
            rightTiles(0.35, 0.2);
        }

        rob.wobbleGrabber.fangsDown();

        backTiles(getPVal("BackAfterFoundation"),getPVal("FastSpeed"));
        if(isRed) {
            targetDegrees = 180;
        }else{
            targetDegrees = 0;
        }
        startTime = runtime.milliseconds();
        while(opModeIsActive() && runtime.milliseconds()-startTime < 2000){
            rob.driveTrain.turnToDegrees(targetDegrees,rob.gyro,this,getPVal("TurnSpeed"));
        }
        rob.driveTrain.stop();

        sleep(100);
        if(isRed) {
            rightTiles(1, 0.6);
        }else{
            leftTiles(1, 0.6);
        }

        //Placing Stone]
        rob.placer.arm.shelbowIn();
        sleep(100);
        while(!rob.placer.arm.shelbowIsFullOut){
            rob.placer.jengaAuto(rob.placer.lift.lift.getCurrentPosition(),runtime.milliseconds());
        }
        while(rob.placer.getLevel()>1){
            rob.placer.lift.lower();
        }
        rob.placer.lift.hold();
        rob.placer.arm.release();
        rob.wobbleGrabber.fangsUp();
        rob.wobbleGrabber.allOut();
        rob.placer.lift.raise();
        sleep(100);
        rob.placer.lift.hold();
        rightTiles(0.1,0.3);
        rob.placer.lift.lower();
        sleep(300);
        rob.placer.lift.hold();

        if(isRed){
            leftTiles(0.2,getPVal("FastSpeed"));
        }else{
            rightTiles(0.2,getPVal("FastSpeed"));
        }

        rob.placer.lift.lower();
        sleep(1000);
        rob.placer.lift.hold();

        forwardTiles(0.25,getPVal("FastSpeed"));

        if(isRed){
            leftTiles(getPVal("ParkTiles")-0.2,getPVal("FastSpeed"));
        }else{
            rightTiles(getPVal("ParkTiles")-0.2,getPVal("FastSpeed"));
        }


        rob.stop();
           */
        /*

        //Moving to Skybridge
        sleep(500);
        backTiles(2,0.7);
        sleep(500);
        forwardTiles(1,0.5);
        sleep(500);
        rightTiles(1.5,0.7);

*/

        /*rob.foundationGrabber.stonePrepare();
        backTiles(1.5,0.25);
        //backTiles(.5,0.25);
        rob.foundationGrabber.middleToStone();
        sleep(250);
        forwardTiles(0.5,0.5);
        sleep(250);
        if(isRed) {
            rob.driveTrain.turnDegrees(80,rob.gyro,this,0.15);  // negative is left
        }else{
            rob.driveTrain.turnDegrees(-80,rob.gyro,this,0.15);
        }
        if(!twoStone){
            while(runtime.seconds()<25){
                sleep(10);
            }
        }else {
            sleep(250);
        }
        backTiles(2,0.5);
        sleep(250);
        rob.foundationGrabber.middleUp();
        sleep(250);
        if(twoStone) {
            forwardTiles(2.86, 0.5);
            sleep(250);
            if (isRed) {
                rob.driveTrain.turnDegrees(-80, rob.gyro, this, 0.15);  // negative is left
            } else {
                rob.driveTrain.turnDegrees(80, rob.gyro, this, 0.15);
            }
            sleep(250);
            backTiles(.5, 0.5);
            sleep(250);
            rob.foundationGrabber.middleToStone();
            sleep(250);
            forwardTiles(.75, 0.5);
            sleep(250);
            if (isRed) {
                rob.driveTrain.turnDegrees(80, rob.gyro, this, 0.15);  // negative is left
            } else {
                rob.driveTrain.turnDegrees(-80, rob.gyro, this, 0.15);
            }
            sleep(250);
            backTiles(2.86, 0.75);
            sleep(250);
            rob.foundationGrabber.middleUp();
            sleep(250);
        }
        forwardTiles(1,0.5);
        sleep(250);
        rob.foundationGrabber.allDown();
        */
    }
}
