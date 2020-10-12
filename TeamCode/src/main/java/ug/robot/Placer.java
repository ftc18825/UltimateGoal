package ug.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Placer extends Mechanism{
    public Arm arm;
    public Lift lift;
    public int stoneLevel;
    double lastArmTime;

    public Placer (String n, HardwareMap hardwareMap){
        super(n, hardwareMap);
        arm = new Arm("arm",hardwareMap);
        lift = new Lift("lift",hardwareMap);
    }

    public void start(){

    }

    public void stop(){
        lift.stop();

}
    public void init(){
        arm.init();
        lift.init();
        stoneLevel = 0;
        lastArmTime = 0;
    }

    public int getLevel(){
        //88=1 -510=2
        int liftEnc = lift.lift.getCurrentPosition();
        return (int)((liftEnc/(-500))+(1+88/500)+0.75);
    }

    public int getTargetLiftPos(){
        return (int)(stoneLevel+1-(1+88/598))*(-598);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    public void jengaAuto(int liftEnc,double milli){
        if((!arm.isOut) && (!lift.aboveRobot(liftEnc)||!(lift.lift.getCurrentPosition()<getTargetLiftPos())) && lift.lift.getCurrentPosition()>-3400){
            arm.shelbowIn();
            lift.raise();
            lastArmTime = milli;
        }else{
            arm.shelbowOutSlow(milli-lastArmTime);
            arm.armIsOut();
        }
    }

    public void jengaLeftAuto(int liftEnc , double rotation){
        rotation += 90;
        rotation = rotation % 360;
        if((!arm.isOut || arm.isAtCapstone) && !lift.aboveRobot(liftEnc)){
            lift.raise();
        }else{
            /*if((rotation >= 315 && rotation <= 360) || (rotation >= 0 && rotation < 45))
                arm.moveJengaLeft();
            else if(rotation >= 45 && rotation < 135)
                arm.moveJengaFar();
            else if(rotation >= 135 && rotation < 225)
                arm.moveJengaRight();
            else
                arm.moveJengaNear();*/
            arm.shelbowOut();
            arm.armIsOut();
            arm.armIsNotAtCapstone();
        }
    }

    public void jengaRightAuto(int liftEnc , double rotation){
        rotation += 90;
        rotation = rotation % 360;
        if((!arm.isOut || arm.isAtCapstone) && !lift.aboveRobot(liftEnc)){
            lift.raise();
        }else{
            /*if((rotation >= 315 && rotation <= 360) || (rotation >= 0 && rotation < 45))
                arm.moveJengaRight();
            else if(rotation >= 45 && rotation < 135)
                arm.moveJengaNear();
            else if(rotation >= 135 && rotation < 225)
                arm.moveJengaLeft();
            else
                arm.moveJengaFar();*/
            arm.shelbowOut();
            arm.armIsOut();
            arm.armIsNotAtCapstone();
        }
    }

    public void jengaFarAuto(int liftEnc , double rotation){
        rotation += 90;
        rotation = rotation % 360;
        if((!arm.isOut || arm.isAtCapstone) && !lift.aboveRobot(liftEnc)){
            lift.raise();
        }else{
            /*if((rotation >= 315 && rotation <= 360) || (rotation >= 0 && rotation < 45))
                arm.moveJengaFar();
            else if(rotation >= 45 && rotation < 135)
                arm.moveJengaRight();
            else if(rotation >= 135 && rotation < 225)
                arm.moveJengaNear();
            else
                arm.moveJengaLeft();*/
            arm.shelbowOut();
            arm.armIsOut();
            arm.armIsNotAtCapstone();
        }
    }

    public void jengaNearAuto(int liftEnc , double rotation){
        rotation += 90;
        rotation = rotation % 360;
        if((!arm.isOut || arm.isAtCapstone) && !lift.aboveRobot(liftEnc)){
            lift.raise();
        }else{
            /*if((rotation >= 315 && rotation <= 360) || (rotation >= 0 && rotation < 45))
                arm.moveJengaNear();
            else if(rotation >= 45 && rotation < 135)
                arm.moveJengaLeft();
            else if(rotation >= 135 && rotation < 225)
                arm.moveJengaFar();
            else
                arm.moveJengaRight();*/
            arm.shelbowOut();
            arm.armIsOut();
            arm.armIsNotAtCapstone();
        }
    }

    public void resetAuto(int liftEnc){
        if((arm.isOut || arm.isAtCapstone) && !lift.aboveRobot(liftEnc)){
            lift.raise();
        }else{
            arm.reset();
            arm.armIsIn();
            arm.armIsNotAtCapstone();
        }
    }

    public void resetCapstoneAuto(int liftEnc){
        if(!arm.isAtCapstone && !lift.aboveRobot(liftEnc)){
            lift.raise();
        }else{
            arm.resetToCapstone();
            arm.armIsAtCapstone();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
}
