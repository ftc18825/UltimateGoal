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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.HashMap;

import ug.robot.Robot;
import ug.util.Param;
import ug.util.ParamManager;


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

@Autonomous(name="Basic", group="Autonomous")
//@Disabled
public class BasicAuton extends LinearOpMode {

    // Declare OpMode members.
    public ElapsedTime runtime = new ElapsedTime();
    public HashMap hmp;
    boolean editing;
    Robot rob;
    ParamManager paramManager;
    public String paramFileName;

    boolean isRed;
    int color;
    boolean isWobble;

    public BasicAuton(){
        super();
        paramFileName = "BasicAutonParams";
        hmp = new HashMap<String,Param>();
        hmp.put("DelayTime", new Param(0));
        setParamUpdateStep("DelayTime", .5);

        isRed = true;
    }

    public void runMe(){
    }

    @Override
    public void runOpMode() {
        rob = new Robot(hardwareMap);
        paramManager = new ParamManager();

        editing = false;
        telemetry.addData("Status", "Initializing");
        telemetry.update();
        rob.init(true);
        int firstSize = hmp.size();
        paramManager.loadFromFile(hardwareMap.appContext, paramFileName,hmp);
        int secondSize = hmp.size();

        // Wait for the game to start (driver presses PLAY)
        int loopCount=0;
        color = -1;
        while(!opModeIsActive()&&!isStopRequested()){
            if(gamepad1.dpad_right && gamepad1.a){
                editing = true;
            }

            color = rob.vision.ringLocation(this,isRed);
            telemetry.addData("ColorCode = ",color);
            loopCount++;
            telemetry.addData("Loop # ",loopCount);

            //if(rob.vision.getPosition(this)){
            //    telemetry.addData("X,Y,Angle: ", rob.vision.posX + " " + rob.vision.posY + " " + rob.vision.posAngle);
            //}else{
            //    telemetry.addData("No target seen", "");
            //}

            telemetry.addData("Status", "Initialized");
            telemetry.addData("firstSize ", firstSize);
            telemetry.addData("secondSize ", secondSize);
            telemetry.addData("Gyro Heading: ", rob.gyro.getHeading());

            if(editing){
                paramManager.respondToGamePadAndTelemetry(gamepad1, hmp,this);
            }else{
                rob.vision.updateVariables(this , gamepad1);
            }
            telemetry.update();
            sleep(10);
        }

        if(editing){
            paramManager.saveToFile(hardwareMap.appContext,paramFileName,hmp);
        }else if(opModeIsActive()){
            runtime.reset();
            rob.start();
            // run until the end of the match (driver presses STOP)
            sleep((long)(getPVal("DelayTime")*1000));
            runMe();
        }
        rob.stop();
    }

    public void setParamUpdateStep(String s, double d) {
        Param p = (Param) hmp.get(s);
        p.setUpdateStep(d);
    }
    public double getPVal(String s) {
        Param p = (Param) hmp.get(s);
        return p.getValue();
    }
}
