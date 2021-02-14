package ug.opMode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="BlueShootWobbleAuton", group="ShootWobble")
//@Disabled
public class BShootWobbleAuton extends ShootWobbleAuton {
    @Override
    public void runOpMode(){
        isRed = false;
        super.runOpMode();
    }

    @Override
    public void runMe(){
        isRed = false;
        super.runMe();
    }
}
