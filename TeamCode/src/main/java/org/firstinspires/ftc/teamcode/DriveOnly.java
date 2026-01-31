package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import java.util.Arrays;

@TeleOp(name = "Archytas Drive")
public class DriveOnly extends LinearOpMode{

    //drive variables
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    //intake motor
    private DcMotor intake = null;

    //launch motor
    private DcMotorEx launch2 = null;

    private double pwr =0.9;




    @Override
    public void runOpMode() {

        //hardwareMapping variables
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lfDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lbDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rfDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rbDrive");

        intake = hardwareMap.get(DcMotor.class, "intake");

        launch2 = hardwareMap.get(DcMotorEx.class, "launch2");



        launch2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        telemetry.addData("Launch Power",pwr);
        telemetry.update();
        //on start launch servo is set to pos 0

        //start of game loop
        while(opModeIsActive()){

            //set all motors forward
            leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
            rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
            leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
            rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

            //set drive variables to the sticks
            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            //combine variables for power
            double lfp = (-forward - strafe - turn);
            double rfp = ( forward - strafe - turn);
            double lbp = (-forward + strafe - turn);
            double rbp = ( forward + strafe - turn);

            //compare all powers to 1
            double[] powerArray = {Math.abs(lfp),Math.abs(rfp), Math.abs(lbp), Math.abs(rbp), 1};

            //get the highest value of all the powers and 1
            double max = Arrays.stream(powerArray).max().getAsDouble();

            //divide all powers by the 1 or the highest power to keep power max at 1 or less
            leftFrontDrive.setPower(lfp/max);
            rightFrontDrive.setPower(rfp/max);
            leftBackDrive.setPower(lbp/max);
            rightBackDrive.setPower(rbp/max);

            //a intakes the ball, b pushes balls out
            if(gamepad2.a){
                intake.setPower(-1);
            }
            else if(gamepad2.b){
                intake.setPower(1);
            }
            else{
                intake.setPower(0);
            }

            //left bumper spins launcher, left triggers slows down/reverses
            if(gamepad2.left_bumper){

                launch2.setPower(pwr);
            }
            else if(gamepad2.left_trigger>0.1){
                launch2.setPower(1);
            }
            else if(gamepad2.x){
                pwr = 0.9;
            }
            else if(gamepad2.y){
                pwr =0.8;
            }
            else{

                launch2.setPower(0);
            }

            if(gamepad2.dpadLeftWasPressed()){

                launch2.setPower(-pwr);
                sleep(1000);
                intake.setPower(-1);
                sleep(200);
                intake.setPower(0);
                sleep(500);
                intake.setPower(-1);
                sleep(1000);
                intake.setPower(0);

                launch2.setPower(0);
                sleep(100);
            }
            if(gamepad2.dpadUpWasPressed()){
                pwr=pwr+0.05;
                telemetry.addData("Launch Power",pwr);
                telemetry.update();
            }
            if(gamepad2.dpadDownWasPressed()){
                pwr=pwr-0.05;
                telemetry.addData("Launch Power",pwr);
                telemetry.update();
            }

        }
    }
}
