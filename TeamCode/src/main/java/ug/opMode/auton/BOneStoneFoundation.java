package ug.opMode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="BlueOneStoneFoundation", group="Stone")
@Disabled
public class BOneStoneFoundation extends CameraStoneAuton {
    @Override
    public void runOpMode(){
        isRed = false;
        super.runOpMode();
    }

    @Override
    public void runMe(){
        isRed = false;
        twoStone = false;
        super.runMe();
    }
}
