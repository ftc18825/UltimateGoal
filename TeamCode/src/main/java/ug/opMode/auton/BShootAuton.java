package ug.opMode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="BlueIntakeAuton", group="Stone")
@Disabled
public class BShootAuton extends ShootAuton {

    @Override
    public void runOpMode(){
        isRed = false;
        super.runOpMode();
    }

    @Override
    public void runMe(){
        isRed = false;
        isWobble = false;
        super.runMe();
    }
}
