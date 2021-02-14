package ug.opMode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="RedShootAuton", group="Shoot")
//@Disabled
public class RShootAuton extends ShootAuton {

    @Override
    public void runOpMode(){
        isRed = true;
        super.runOpMode();
    }

    @Override
    public void runMe(){
        isRed = true;
        isWobble = false;
        super.runMe();
    }
}
