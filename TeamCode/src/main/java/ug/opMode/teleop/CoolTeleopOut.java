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

package ug.opMode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import ug.robot.Robot;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "Cool TeleOp Out")
//@Disabled
public class CoolTeleopOut extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    Robot rob;
    double driveSpeed;
    boolean leftStick1Pressed;
    boolean yPressed;
    boolean aPressed;
    boolean bPressed;
    boolean xPressed;
    boolean dpadUpPressed;
    boolean dpadDownPressed;
    boolean left1Pressed;
    boolean beginning;
    double lastBackUpTime;
    double lastCapstoneTime;
    double lastLiftTime;
    double lastArmTime;

    boolean isOut;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        //rob = new Robot(hardwareMap);
        // Tell the driver that initialization is complete.
        rob = new Robot(hardwareMap);

        rob.init(false);
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Rob Mechs", rob.mechanisms.size());
        driveSpeed = 1;
        leftStick1Pressed = false;
        yPressed = false;
        aPressed = false;
        bPressed = false;
        xPressed = false;
        dpadUpPressed = false;
        dpadDownPressed = false;
        beginning = true;
        lastBackUpTime = 0;
        lastCapstoneTime = 0;
        lastLiftTime = 0;
        lastArmTime = 0;
        isOut = true;
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
        rob.start();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        rob.driveTrain.holoGyro(gamepad1.left_stick_x * driveSpeed, -gamepad1.left_stick_y * driveSpeed, gamepad1.right_stick_x * driveSpeed*2/3,(int)rob.gyro.getHeading());
        //rob.driveTrain.holoDrive(gamepad1.left_stick_x * driveSpeed, -gamepad1.left_stick_y * driveSpeed, gamepad1.right_stick_x * driveSpeed);

        if (gamepad1.left_stick_button && !leftStick1Pressed) {
            if (driveSpeed > 0.9)
                driveSpeed = 0.5;
            else if (driveSpeed < 0.6)
                driveSpeed = 1.0;
        }
        leftStick1Pressed = gamepad1.left_stick_button;

        if (gamepad1.right_trigger > 0.5) {
            rob.intake.in();
        } else if (gamepad1.right_bumper) {
            rob.intake.out();
        } else {
            rob.intake.stopPower();
        }

        if(gamepad1.y){
            rob.foundationGrabber.fangsUp();
            rob.foundationGrabber.fgUp();
        }else if(gamepad1.a){
            rob.foundationGrabber.fangsDown();
        }else if(gamepad1.x){
            rob.foundationGrabber.fangsNubs();
            rob.foundationGrabber.fgDown();
        }else if(gamepad1.b){
            rob.foundationGrabber.fgDown();
        }

        if (gamepad1.dpad_up) {
            rob.gyro.resetHeadingForward();
        }
        if (gamepad1.dpad_left) {
            rob.gyro.resetHeadingLeft();
        }
        if(gamepad1.dpad_right) {
            rob.gyro.resetHeadingRight();
        }
        if(gamepad1.dpad_down){
            rob.gyro.resetHeadingBackward();
        }

        if(gamepad1.left_bumper || gamepad2.left_bumper){
            rob.intake.pushOut();
        }else{
            rob.intake.pushIn();
        }



        // Show the elapsed game time and wheel power.
        telemetry.addData("Heading: ", rob.gyro.getHeading());
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        //telemetry.addData("Drive Motors", rob.driveTrain.frontLeftFwd + " " + rob.driveTrain.backLeftFwd + " " + rob.driveTrain.frontRightFwd + " " + rob.driveTrain.backRightFwd);
        beginning = false;
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        rob.stop();
    }

}
