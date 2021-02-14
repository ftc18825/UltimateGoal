package ug.opMode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="RedShootWobbleAuton", group="ShootWobble")
//@Disabled
public class RShootWobbleAuton extends ShootWobbleAuton {
    @Override
    public void runOpMode(){
        isRed = true;
        super.runOpMode();
    }

    @Override
    public void runMe(){
        isRed = true;
        super.runMe();
    }
}
