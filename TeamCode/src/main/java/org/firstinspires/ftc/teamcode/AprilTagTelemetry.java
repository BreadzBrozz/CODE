package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;

@TeleOp(name = "april tag test with drive")
public class AprilTagTelemetry extends LinearOpMode{
    // initalize variables
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;


    private DcMotor intake = null;
    private DcMotor launch = null;


    public void runOpMode() throws InterruptedException{
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lfDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lbDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rfDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rbDrive");

        intake = hardwareMap.get(DcMotor.class, "intake");
        launch = hardwareMap.get(DcMotor.class, "launch");


        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);


        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        //all that happens on init
        AprilTagProcessor tgprss = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .build();

        VisionPortal visp = new VisionPortal.Builder()
                .addProcessor(tgprss)
                .setCamera(hardwareMap.get(WebcamName.class, "camera"))
                .setCameraResolution(new Size(640, 480))
                .build();


        waitForStart();

        //main loop
        while(opModeIsActive() && !isStopRequested()){
            //looks for april tags
            if(!tgprss.getDetections().isEmpty()) {
                //goes through the list of april tags seen
                for(int i =0; i < tgprss.getDetections().size(); i++){
                    AprilTagDetection tag = tgprss.getDetections().get(i);
                    if(tag.id == 21){
                        tag21();
                    }
                    if(tag.id == 22){
                        tag22();
                    }
                    if(tag.id == 23){
                        tag23();

                    }
                }
            }
        }

    }

    public void tag23 (){
        Drive(3491,3491,3491,3491,0.6);
        sleep(100);
        Drive(900,-900,900,-900,0.6);
        intake.setPower(1);
        sleep(300);
        Drive(-500,-500,-500,-500,0.6);
        intake.setPower(0);
        Drive(500,500,500,500,0.6);
        sleep(100);
        Drive(-900,900,-900,900,0.6);
        sleep(100);
        Drive(-3391,-3391,-3391,-3391,0.6);
        sleep(100);
        Drive(-100,100,-100,100,0.6);


        launch.setPower(0.82);
        sleep(2500);
        intake.setPower(-1);
        sleep(200);
        launch.setPower(0.83);
        intake.setPower(0);
        sleep(1000);
        intake.setPower(-1);
        sleep(1000);
        intake.setPower(0);
        launch.setPower(0);
        sleep(100);
        Drive(1200, 1200, 1200, 1200, 0.6);

        sleep(300000);

    }
    public void tag22 (){
        Drive(2074,2074,2074,2074,0.6);
        sleep(100);
        Drive(900,-900,900,-900,0.6);
        intake.setPower(1);
        sleep(300);
        Drive(-500,-500,-500,-500,0.6);
        intake.setPower(0);
        Drive(500,500,500,500,0.6);
        sleep(100);
        Drive(-900,900,-900,900,0.6);
        sleep(100);
        Drive(-1974,-1974,-1974,-1974,0.6);
        sleep(100);
        Drive(-100,100,-100,100,0.6);


        launch.setPower(0.82);
        sleep(2500);
        intake.setPower(-1);
        sleep(200);
        launch.setPower(0.83);
        intake.setPower(0);
        sleep(1000);
        intake.setPower(-1);
        sleep(1000);
        intake.setPower(0);
        launch.setPower(0);
        sleep(100);
        Drive(1200, 1200, 1200, 1200, 0.6);

        sleep(300000);
    }
    public void tag21 (){
        Drive(1200,1200,1200,1200,0.6);
        sleep(100);
        Drive(850,-850,850,-850,0.6);
        intake.setPower(-1);
        sleep(300);
        Drive(300,300,300,300,0.6);
        sleep(300);
        Drive(-1200,-1200,-1200,-1200,0.99);
        sleep(300);
        intake.setPower(0);
        Drive(1100,1100,1100,1100,0.6);
        sleep(100);
        Drive(-850,850,-850,850,0.6);
        sleep(100);
        Drive(-1050,-1050,-1050,-1050,0.6);
        sleep(100);
        Drive(-100,100,-100,100,0.6);


        launch.setPower(0.82);
        sleep(2500);
        intake.setPower(-1);
        sleep(500);
        launch.setPower(0.83);
        intake.setPower(0);
        sleep(1000);
        intake.setPower(-1);
        sleep(1000);
        intake.setPower(0);
        launch.setPower(0);
        sleep(100);
        Drive(1200, 1200, 1200, 1200, 0.6);

        //ensure to add a sleep at the end b/c the code is in a while loop and we dont want auto executing over and over
        sleep(300000);
    }
    public void Drive ( int FrontLTarget, int FrontRTarget, int BackLTarget, int BackRTarget, double Speed){
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        leftFrontDrive.setTargetPosition(FrontLTarget);
        rightFrontDrive.setTargetPosition(FrontRTarget);
        leftBackDrive.setTargetPosition(BackLTarget);
        rightBackDrive.setTargetPosition(BackRTarget);


        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        leftFrontDrive.setPower(Speed);
        rightFrontDrive.setPower(Speed);
        leftBackDrive.setPower(Speed);
        rightBackDrive.setPower(Speed);


        while (opModeIsActive() && (rightBackDrive.isBusy() || leftBackDrive.isBusy() || rightFrontDrive.isBusy() || leftFrontDrive.isBusy())) {
            idle();
        }


        rightBackDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftFrontDrive.setPower(0);
    }
}
