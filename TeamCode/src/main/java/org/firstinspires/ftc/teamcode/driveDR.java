package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "shoot drive at red")


public class driveDR extends LinearOpMode {
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;


    private DcMotor intake = null;
    private DcMotor launch = null;
    private DcMotorEx launch2 = null;




    @Override
    public void runOpMode() {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lfDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lbDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rfDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rbDrive");

        intake = hardwareMap.get(DcMotor.class, "intake");
        launch = hardwareMap.get(DcMotor.class, "launch");
        launch2 = hardwareMap.get(DcMotorEx.class, "launch2");


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


        waitForStart();


        while (opModeIsActive()) {


            //drive backwards and pause
            launch.setPower(0.82);
            launch2.setPower(-0.82);
            sleep(2500);
            intake.setPower(-1);
            sleep(200);
            launch.setPower(0.83);
            launch2.setPower(-0.83);
            intake.setPower(0);
            sleep(1000);
            intake.setPower(-1);
            sleep(1000);
            intake.setPower(0);
            launch.setPower(0);
            launch2.setPower(0);
            sleep(100);



            Drive(1200, 1200, 1200, 1200, 0.6);
            sleep(30000);
        }
    }




    public void Drive ( int FrontLTarget, int FrontRTarget, int BackLTarget, int BackRTarget,
                        double Speed){
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
