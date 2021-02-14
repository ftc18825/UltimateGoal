package ug.opMode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="RedOneStoneFoundation", group="Stone")
@Disabled
public class ROneStoneFoundation extends CameraStoneAuton {
    @Override
    public void runMe(){
        isRed = true;
        twoStone = false;
        super.runMe();
    }
}
