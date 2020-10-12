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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import ug.robot.Robot;


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

@TeleOp(name="Servo and Motor Test", group="Test")
//@Disabled
public class SetServosAndMotors extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    public Robot hyrule;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        hyrule = new Robot(hardwareMap);
        hyrule.init(false);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        hyrule.start();
        int counter = 0;
        int total = 0;
        int selected = 0;
        boolean totalCounted = false;
        boolean yPressed = false;
        boolean aPressed = false;
        boolean xPressed = false;
        boolean bPressed = false;
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            counter = 0;
            for (int i=0; i<hyrule.mechanisms.size(); i++ ){
                for(String s : hyrule.mechanisms.get(i).hms.keySet()){
                    if(counter == selected) {
                        telemetry.addData(">>> " + s, hyrule.mechanisms.get(i).hms.get(s).getPosition());
                        if(gamepad1.x && !xPressed){
                            hyrule.mechanisms.get(i).hms.get(s).setPosition(Range.clip(hyrule.mechanisms.get(i).hms.get(s).getPosition() - 0.05,0,1));
                        }
                        if(gamepad1.b && !bPressed){
                            hyrule.mechanisms.get(i).hms.get(s).setPosition(Range.clip(hyrule.mechanisms.get(i).hms.get(s).getPosition() + 0.05,0,1));
                        }
                    }else
                        telemetry.addData( s , hyrule.mechanisms.get(i).hms.get(s).getPosition() );
                    if(!totalCounted)
                        total++;
                    counter++;
                }
            }
            for (int i=0; i<hyrule.mechanisms.size(); i++ ){
                for(String s : hyrule.mechanisms.get(i).hmm.keySet()){
                    if(counter == selected) {
                        DcMotor m = hyrule.mechanisms.get(i).hmm.get(s);
                        telemetry.addData(">>> " + s, m.getCurrentPosition());
                        if(gamepad1.x && !xPressed){
                            m.setPower(1);
                            m.setTargetPosition(m.getCurrentPosition() - 200);
                            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        }
                        if(gamepad1.b && !bPressed){
                            m.setPower(1);
                            m.setTargetPosition(m.getCurrentPosition() + 200);
                            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        }
                    }else
                        telemetry.addData( s , hyrule.mechanisms.get(i).hmm.get(s).getCurrentPosition());
                    if(!totalCounted)
                        total++;
                    counter++;
                }
            }
            totalCounted = true;
            if(gamepad1.a && !aPressed){
                selected++;
                selected = selected % total;
            }
            if(gamepad1.y && !yPressed){
                selected--;
                selected = (selected+total) % total;
            }
            aPressed = gamepad1.a;
            yPressed = gamepad1.y;
            xPressed = gamepad1.x;
            bPressed = gamepad1.b;


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
            sleep(100);
        }
        hyrule.stop();
    }
}
