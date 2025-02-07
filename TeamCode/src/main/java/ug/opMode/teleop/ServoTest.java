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

@TeleOp(name = "TestServos")
//@Disabled
public class ServoTest extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    Robot rob;
    double driveSpeed;
    boolean rightStickPressed;
    boolean yPressed;
    boolean aPressed;
    boolean bPressed;
    boolean xPressed;
    
    double value;
    double updateVal;
    int editVal;
    
    String [] editing;

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
        rightStickPressed = false;
        yPressed = false;
        aPressed = false;
        bPressed = false;
        xPressed = false;
        
        editing = new String [] {"wobbleGrabber" , "pusher" , "wobbleRotator"};
        value = 0;
        editVal = 0;
        updateVal = 0.002;
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

        if(gamepad1.x && !xPressed){
            editVal ++;
        }
        xPressed = gamepad1.x;

        if(gamepad1.b&&!bPressed){
            editVal --;
        }
        bPressed = gamepad1.b;

        if(gamepad1.y){
            value += updateVal;
        }

        if(gamepad1.a){
            value -= updateVal;
        }


        if(editVal % editing.length == 0){
            rob.wobbleGrabber.wobbleGrabber.setPosition(value);
        }else if(editVal % editing.length == 1){
            rob.shooter.pusher.setPosition(value);
        }else if(editVal % editing.length == 2){
            rob.wobbleGrabber.wobbleRotator.setPosition(value);
        }


            // Show the elapsed game time and wheel power.
        telemetry.addData("Heading: ", rob.gyro.getHeading());
        telemetry.addData("Status", "Run Time: " + runtime.toString());

        telemetry.addData("Editing: " , editing[editVal%editing.length]);
        telemetry.addData("WobbleGrabberPos: " , rob.wobbleGrabber.wobbleGrabber.getPosition());
        telemetry.addData("PusherPos: " , rob.shooter.pusher.getPosition());
        telemetry.addData("WobbleRotatorPos: " , rob.wobbleGrabber.wobbleRotator.getPosition());

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        rob.stop();
    }

}
