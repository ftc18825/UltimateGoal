package ug.opMode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="RedTwoStoneAuton", group="Stone")
@Disabled
public class RTwoStoneAuton extends StoneAuton {
    @Override
    public void runOpMode(){
        isRed = true;
        super.runOpMode();
    }

    @Override
    public void runMe(){
        isRed = true;
        twoStone = true;
        super.runMe();
    }
}
