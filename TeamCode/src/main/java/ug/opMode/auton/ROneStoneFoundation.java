package ug.opMode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="RedOneStoneFoundation", group="Stone")
public class ROneStoneFoundation extends CameraStoneAuton {
    @Override
    public void runMe(){
        isRed = true;
        twoStone = false;
        super.runMe();
    }
}
