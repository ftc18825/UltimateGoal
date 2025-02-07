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

@Autonomous(name="StoneAuton", group="Stone")
@Disabled
public class ShootWobbleAuton extends ShootAuton {
    @Override
    public void runMe(){
        isWobble = true;
        super.runMe();

        if(color == 0){
            forwardTiles(getPVal("TilesToA") , .25);
        }else if(color == 1){
            forwardTiles(getPVal("TilesToB") , .25);
        }else{
            forwardTiles(getPVal("TilesToC") , .25);
        }

        sleep(250);

        /*

        double startTime = runtime.milliseconds();
        while(opModeIsActive() && runtime.milliseconds()-startTime < 1500){
            rob.driveTrain.turnToDegrees(getPVal("DegreesToWobble"),rob.gyro,this,0.25);
        }
        sleep(250);

         */

        if(color == 1){
            if(isRed){
                leftTiles(getPVal("TilesToWallRed") , 0.25);
                rob.wobbleGrabber.down();
                sleep(500);
                rob.wobbleGrabber.release();
                sleep(250);
                leftTiles(0.25 , 0.25);
                rob.wobbleGrabber.up();
                sleep(250);
                //rightTiles(getPVal("TilesToWallRed") , 0.25);
            }else{
                rightTiles(getPVal("TilesToWallBlue") , 0.25);
                rob.wobbleGrabber.down();
                sleep(500);
                rob.wobbleGrabber.release();
                sleep(250);
                leftTiles(0.25 , 0.25);
                rob.wobbleGrabber.up();
                sleep(250);
                //leftTiles(getPVal("TilesToWallBlue") , 0.25);
            }
        }else{
            rob.wobbleGrabber.down();
            sleep(500);
            rob.wobbleGrabber.release();
            sleep(250);
            leftTiles(0.25 , 0.25);
            rob.wobbleGrabber.up();
            sleep(250);
        }
        sleep(250);

        /*

        startTime = runtime.milliseconds();
        while(opModeIsActive() && runtime.milliseconds()-startTime < 1500){
            rob.driveTrain.turnToDegrees(0,rob.gyro,this,0.25);
        }
        sleep(250);

         */

        if(color == 0){
            backTiles(getPVal("TilesToA") + getPVal("TilesToShoot") - getPVal("TilesToPark") , 0.25);
        }else if(color == 1){
            backTiles(getPVal("TilesToB") + getPVal("TilesToShoot") - getPVal("TilesToPark") , 0.25);
        }else{
            backTiles(getPVal("TilesToC") + getPVal("TilesToShoot") - getPVal("TilesToPark") , 0.25);
        }

    }
}
