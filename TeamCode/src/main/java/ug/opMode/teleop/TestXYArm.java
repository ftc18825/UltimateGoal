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

import ug.robot.Arm;

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

@TeleOp(name = "TestXYArm")
//@Disabled
public class TestXYArm extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    Arm arm;
    double driveSpeed;
    boolean rightStickPressed;

    double xArm;
    double yArm;
    double rotateArm;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        //rob = new Robot(hardwareMap);
        // Tell the driver that initialization is complete.
        arm =new Arm("arm", hardwareMap);
        arm.init();
        telemetry.addData("Status", "Initialized");
        driveSpeed = 1;
        rightStickPressed = false;

        xArm = -225;
        yArm = 160;
        rotateArm = 180;
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
        arm.start();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        /*if(gamepad2.dpad_up){
            arm.shoulderIncrease();
        }else if(gamepad2.dpad_down){
            arm.shoulderDecrease();
        }

        if(gamepad2.y){
            arm.elbowIncrease();
        }else if(gamepad2.a){
            arm.elbowDecrease();
        }

        if(gamepad2.right_bumper){
            arm.wristIncrease();
        }else if(gamepad2.right_trigger>0.5){
            arm.wrsitDecrease();
        }

        if(gamepad2.left_bumper){
            arm.handIncrease();
        }else if(gamepad2.left_trigger>0.5){
            arm.handDecrease();
        }*/

        xArm += gamepad1.left_stick_x;
        yArm -= gamepad1.left_stick_y;
        rotateArm += gamepad1.right_stick_y;

        arm.moveToXYRotation(xArm,yArm,rotateArm);

        if(gamepad1.left_trigger > 0.5) {
            arm.grab();
        }else if (gamepad1.left_bumper){
            arm.release();
        }

        /*if(gamepad1.left_bumper){
            arm.handIncrease();
        }else if(gamepad1.left_trigger>0.5){
            arm.handDecrease();
        }

        if(gamepad1.dpad_up){
            arm.moveJengaFar();
        }

        if(gamepad1.dpad_down){
            arm.moveJengaNear();
        }

        if(gamepad1.dpad_left){
            arm.moveJengaLeft();
        }

        if(gamepad1.dpad_right){
            arm.moveJengaRight();
        }

        if(gamepad1.y){
            arm.reset();
        }

        if(gamepad1.a){
            arm.resetToCapstone();
        }*/

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("ShoulderPos: ", arm.shoulderPos);
        telemetry.addData("ElbowPos: ", arm.elbowPos);
        telemetry.addData("WristPos: ", arm.wristPos);
        telemetry.addData("HandPos: ", arm.handPos);

        telemetry.addData("XArm: ", xArm);
        telemetry.addData("yArm: ", yArm);
        telemetry.addData("RotateArm: ", rotateArm);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        arm.stop();
    }

}
