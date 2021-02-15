package ug.opMode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import ug.util.Param;
import ug.util.ParamManager;

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


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name = "Skys", group = "Autonomous")
@Disabled
public class UGAuton extends BasicAuton {

    // Declare OpMode members.
    //private ElapsedTime runtime = new ElapsedTime();
    boolean editing;
    boolean startingAtCrater;
    double degrees;
    ParamManager paramManager;
    public String paramFileName;

    public UGAuton() {
        super();
        startingAtCrater = true;
        degrees = 0;
        paramFileName = "UGAutonParams";

        hmp.put("TilesToPark" , new Param(3));
        setParamUpdateStep("TilesToPark" , 0.1);
        hmp.put("TilesToShoot" , new Param(1.5));
        setParamUpdateStep("TilesToShoot" , 0.1);
        hmp.put("TilesToA" , new Param(1));
        setParamUpdateStep("TilesToA" , 0.1);
        hmp.put("TilesToB" , new Param(2));
        setParamUpdateStep("TilesToB" , 0.1);
        hmp.put("TilesToC" , new Param(3));
        setParamUpdateStep("TilesToC" , 0.1);
        hmp.put("DegreesToWobble" , new Param(90));
        setParamUpdateStep("DegreesToWobble" , 2.5);
        hmp.put("TilesToWallRed" , new Param(1));
        setParamUpdateStep("TilesToWallRed" , 0.05);
        hmp.put("TilesToWallBlue" , new Param(1));
        setParamUpdateStep("TilesToWallBlue" , 0.05);
        hmp.put("StrafeShootRed" , new Param(.5));
        setParamUpdateStep("TilesToWallBlue" , .1);
        hmp.put("StrafeShootBackRed" , new Param(0));
        setParamUpdateStep("TilesToWallBlue" , .1);

    }

    public void forwardTiles(double tiles,double speed) {
        rob.driveTrain.moveEnc(speed, tiles, this,rob.gyro);
    }

    public void backTiles(double tiles,double speed){
        rob.driveTrain.moveEnc(-speed, tiles, this,rob.gyro);
    }

    public void rightTiles(double tiles,double speed) {
        rob.driveTrain.strafeEnc(speed, tiles, this,rob.gyro);
    }

    public void leftTiles(double tiles,double speed) {
        rob.driveTrain.strafeEnc(-speed, tiles, this,rob.gyro);
    }



}
