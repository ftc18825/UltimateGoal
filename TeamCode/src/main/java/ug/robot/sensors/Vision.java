/* Copyright (c) 2018 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ug.robot.sensors;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import ug.robot.Mechanism;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

/**
 * This 2018-2019 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the gold and silver minerals.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */

public class Vision extends Mechanism {
    /*private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";*/

    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "Aa/4fun/////AAAAGZlp5jwSLEkohJaE3ovEnaRnBuuoQdhLq+CXuTdYBJdd1+lAe4tZkx9Z8G8gh4McTgzWNw/CeqzcVHWHF4UorHI+29iOM6r0QZA+cWJJocd4x0OPRWBhT/4vUSIikcdTxxIpL94itCOcDfoX3ikedfjdC3QyC3tIq7OaCYwOsurGM2HpiEawIAk+Rm0kl3G1gassnuJ7dnd66kasWLzoXmO3lfQKkrFbfzAY/G59HAE+/RKNcCZdMTtKLhCwBq5qFet3WdB+mpebp/ET1Gqq4uVhUahESXzcrkX4TTlfr7WuJ7FsZd1dbVuki4fAEY9k17dfuey2+qncBxsNl2SJVpDVR9CNIRuL6Z0Tftlhyj3F";

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = FRONT;
    private static final boolean PHONE_IS_PORTRAIT = true  ;

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField  = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;

    public double XStartPercent = 0.15;
    public double XEndPercent = 0.35;
    public double YStartPercent = 0;
    public double YEndPercent = .5;
    public int editVal = 0;
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    public String [] editing = new String [] {"XStart" , "XEnd" , "YStart" , "YEnd"};

    //VuforiaTrackables targetsSkyStone = null;
    List<VuforiaTrackable> allTrackables = null;

    public float posX , posY , posAngle;
    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */

    /**
     * {@link #} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    //private TFObjectDetector tfod;

    public boolean using;

    public Vision(String n,HardwareMap hardwareMap){
        super(n, hardwareMap);
        using = false;
    }

    public void init(){
        if(using) {
            initVuforia();

        }


    }

    public void start(){
        /*if (tfod != null) {
            //tfod.activate();
        }*/
    }

    public void stop(){
        /*if (tfod != null) {
            //tfod.shutdown();
        }*/

        if(using){
            //targetsSkyStone.deactivate();
        }
    }

    /*@Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }


        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {

            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                      telemetry.addData("# Object Detected", updatedRecognitions.size());
                      if (updatedRecognitions.size() == 3) {
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;
                        for (Recognition recognition : updatedRecognitions) {
                          if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                          } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                          } else {
                            silverMineral2X = (int) recognition.getLeft();
                          }
                        }
                        if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                          if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Left");
                          } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            telemetry.addData("Gold Mineral Position", "Right");
                          } else {
                            telemetry.addData("Gold Mineral Position", "Center");
                          }
                        }
                      }
                      telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }*/



    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = CAMERA_CHOICE;
        parameters.useExtendedTracking=false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        vuforia.setFrameQueueCapacity(4);

        /*targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        //VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        //blueRearBridge.setName("Blue Rear Bridge");
        //VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        //redRearBridge.setName("Red Rear Bridge");
        //VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        //redFrontBridge.setName("Red Front Bridge");
        //VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        //blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");*/

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables = new ArrayList<VuforiaTrackable>();
        //allTrackables.addAll(targetsSkyStone);

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms

        /*
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));


        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));*/

        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        /*
        final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line
        */
        final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 11.5f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 6.5f * mmPerInch;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        // WARNING:
        // In this sample, we do not wait for PLAY to be pressed.  Target Tracking is started immediately when INIT is pressed.
        // This sequence is used to enable the new remote DS Camera Preview feature to be used with this sample.
        // CONSEQUENTLY do not put any driving commands in this loop.
        // To restore the normal opmode structure, just un-comment the following line:

        // waitForStart();

        // Note: To use the remote camera preview:
        // AFTER you hit Init on the Driver Station, use the "options menu" to select "Camera Stream"
        // Tap the preview window to receive a fresh image.


        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.

        //targetsSkyStone.activate();
    }

    public boolean getPosition(OpMode om){
        targetVisible = false;
        for (VuforiaTrackable trackable : allTrackables) {
            if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                om.telemetry.addData("Visible Target", trackable.getName());
                targetVisible = true;

                // getUpdatedRobotLocation() will return null if no new information is available since
                // the last time that call was made, or if the trackable is not currently visible.
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
                break;
            }
        }

        // Provide feedback as to where the robot is located (if we know).
        if (targetVisible) {
            // express position (translation) of robot in inches.
            VectorF translation = lastLocation.getTranslation();
            om.telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

            // express the rotation of the robot in degrees.
            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            om.telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);

            posAngle = rotation.thirdAngle;
            posX = translation.get(0) / mmPerInch;
            posY = translation.get(1) / mmPerInch;
        }
        else {
            om.telemetry.addData("Visible Target", "none");
        }
        //om.telemetry.update();
        return targetVisible;
    }



    static public int red(int pixel) {
        return (pixel >> 16) & 0xff;
    }

    static public int green(int pixel) {
        return (pixel >> 8) & 0xff;
    }

    static public int blue(int pixel) {
        return pixel & 0xff;
    }

    public static double saturation(double red , double green , double blue , double totalMax){
        double min = red;
        double max = red;

        if(green < min){
            min = green;
        }else{
            max = green;
        }

        if(blue < min){
            min = blue;
        }
        if(blue > max){
            max = blue;
        }

        double lumin = (max + min)/(2*totalMax);

        if(lumin == 1){
            return 0;
        }else{
            return ((max - min)/totalMax)/(1-Math.abs(2*lumin - 1));
        }

    }

    public void updateVariables(LinearOpMode om , Gamepad g){
        if(g.dpad_up && !upPressed){
            if(editVal%4 == 0){
                XStartPercent += 0.01;
            }else if(editVal%4 == 1){
                XEndPercent += 0.01;
            }else if(editVal%4 == 2){
                YStartPercent += 0.01;
            }else{
                YEndPercent += 0.01;
            }
        }
        upPressed = g.dpad_up;

        if(g.dpad_down && !downPressed){
            if(editVal%4 == 0){
                XStartPercent -= 0.01;
            }else if(editVal%4 == 1){
                XEndPercent -= 0.01;
            }else if(editVal%4 == 2){
                YStartPercent -= 0.01;
            }else{
                YEndPercent -= 0.01;
            }
        }
        downPressed = g.dpad_down;

        if(g.dpad_left && !leftPressed){
            editVal -= 1;
        }
        leftPressed = g.dpad_left;

        if(g.dpad_right && !rightPressed){
            editVal += 1;
        }
        rightPressed = g.dpad_right;

        om.telemetry.addData("Editing: " , editing[editVal%editing.length]);
        om.telemetry.addData("XStart" , XStartPercent);
        om.telemetry.addData("XEnd" , XEndPercent);
        om.telemetry.addData("YStart" , YStartPercent);
        om.telemetry.addData("YEnd" , YEndPercent);
    }

    public int ringLocation(LinearOpMode om, boolean isRed){
        int color = -1;
        if(!om.opModeIsActive()) {
            Image image;


            VuforiaLocalizer.CloseableFrame frame = null;
            try {
                frame = vuforia.getFrameQueue().take();

                for (int i = 0; i < frame.getNumImages(); i++) {
                    Image img = frame.getImage(i);

                    if (img.getFormat() == PIXEL_FORMAT.RGB565) {
                        image = frame.getImage(i);

                        Bitmap rgbImage = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
                        rgbImage.copyPixelsFromBuffer(image.getPixels());

                        int XStart = (int) ((double) rgbImage.getWidth() * XStartPercent);
                        int XEnd = (int) ((double) rgbImage.getWidth() * XEndPercent);

                        int YStart;
                        int YEnd;

                        if (!isRed) {
                            YStart = (int) ((double) rgbImage.getHeight() * YStartPercent);
                            YEnd = (int) ((double) rgbImage.getHeight() * YEndPercent);
                        } else {
                            YStart = (int) ((double) rgbImage.getHeight() * YStartPercent);
                            YEnd = (int) ((double) rgbImage.getHeight() * YEndPercent);
                        }

                        int rightRedValue = 0;
                        int rightBlueValue = 0;
                        int rightGreenValue = 0;
                        int leftRedValue = 0;
                        int leftBlueValue = 0;
                        int leftGreenValue = 0;

                        //for (int x = XStart; x < XEnd; x+= (XEnd-XStart)/8) {
                        for (int r = 0; r < 8; r++) {
                            //for (int y = yStart; y < yEnd; y+=(yEnd-yStart)/8) {
                            String colorString = "";
                            for (int c = 0; c < 8; c++) {
                                int pixel = rgbImage.getPixel((XEnd - XStart) / 8 * r + XStart, (YEnd - YStart) / 8 * c + YStart);
                                leftGreenValue += green(pixel);
                                leftBlueValue += blue(pixel);
                                leftRedValue += red(pixel);
                                //color[r][c] = red(pixel);
                                colorString += green(pixel) + " ";
                            }
                            om.telemetry.addData("" , colorString);
                        }

                        /*

                        double min = leftRedValue;
                        double max = leftRedValue;

                        if(leftGreenValue < min){
                            min = leftGreenValue;
                        }else{
                            max = leftGreenValue;
                        }

                        if(leftBlueValue < min){
                            min = leftBlueValue;
                        }
                        if(leftBlueValue > max){
                            max = leftBlueValue;
                        }

                        double lumin = (max + min)/(2*8*8*255);

                         */

                        double sat = saturation(leftRedValue , leftGreenValue , leftBlueValue , 8*8*255);
                        if(sat > 0.15){
                            color = 2;
                        }else if(sat > 0.03){
                            color = 1;
                        }else{
                            color = 0;
                        }


                        //om.telemetry.addData("Total Green:", leftGreenValue);
                        om.telemetry.addData("Saturation: " , sat);
                        //om.telemetry.addData("min: " , min);
                        //om.telemetry.addData("max: " , max);
                        //om.telemetry.addData("Lumin: " , lumin);
                        //om.telemetry.addData("Top: " , (max - min)/(8*8*255));
                        //om.telemetry.addData("Bottom: " , (1-Math.abs(2*lumin - 1)));
                        //om.telemetry.addData("Total Right Red", rightRedValue);

                       /* if (leftRedValue > rightRedValue) {
                            if (leftRedValue / (rightRedValue + 1) > 2) {
                                color = 1;
                            } else {
                                color = 2;
                            }
                        } else {
                            if (rightRedValue / (leftRedValue + 1) > 2) {
                                color = 0;
                            } else {
                                color = 2;
                            }
                        }*/

                    /*if(rightBlueValue > leftBlueValue){
                        color = 1;
                    }else{
                        color = 2;
                    }*/


                    /*if(leftRedValue > leftGreenValue && leftRedValue > leftBlueValue){
                        color = 0;
                    }else if(leftBlueValue>leftGreenValue){
                        color = 2;
                    }else{
                        color = 1;
                    }*/

                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (frame != null) frame.close();
            }
        }
        return color;
    }

    public boolean blueIsOnLeft(Bitmap rgbImage) {
        int leftXStart = (int) ((double) rgbImage.getWidth() * 0.05);
        int rightXStart = (int) ((double) rgbImage.getWidth() * 0.75);
        int leftXEnd = (int) ((double) rgbImage.getWidth() * 0.25);
        int rightXEnd = (int) ((double) rgbImage.getWidth() * 0.95);

        int yStart = (int) ((double) rgbImage.getHeight() * 0.4);
        int yEnd = (int) ((double) rgbImage.getHeight() * 0.6);

        int rightRedValue = 0;
        int rightBlueValue = 0;
        int leftRedValue = 0;
        int leftBlueValue = 0;

        for (int x = leftXStart; x < leftXEnd; x++) {
            for (int y = yStart; y < yEnd; y++) {
                int pixel = rgbImage.getPixel(x, y);
                leftRedValue += red(pixel);
                leftBlueValue += blue(pixel);
            }
        }

        for (int x = rightXStart; x < rightXEnd; x++) {
            for (int y = yStart; y < yEnd; y++) {
                int pixel = rgbImage.getPixel(x, y);
                rightRedValue += red(pixel);
                rightBlueValue += blue(pixel);
            }
        }

        if (leftRedValue - leftBlueValue > rightRedValue - rightBlueValue)
            return true;
        else
            return false;
    }

}
