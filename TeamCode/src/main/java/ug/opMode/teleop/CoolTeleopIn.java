package ug.opMode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Cool TeleOp In")
//@Disabled
public class CoolTeleopIn extends CoolTeleopOut {
    @Override
    public void init(){
        super.init();
        isOut = false;
    }
}
