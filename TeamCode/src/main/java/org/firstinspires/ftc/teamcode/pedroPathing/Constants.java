package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(12.24)
            .forwardZeroPowerAcceleration(-42.5)
            .lateralZeroPowerAcceleration(-78.5)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.1, 0.000001, 0.0000001, 0.000001))
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.15, 0, 0.01, 0.015 ))
            .headingPIDFCoefficients(new PIDFCoefficients(0.9, 0.00001, 0.000001, 0.001))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(0.9,0.001,0.08,0.01))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.1,0.0001,0.000005,0.8,0.03))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.01 ,0,0.000005,0.01, 0.3))
            .centripetalScaling(0.005)
            .useSecondaryTranslationalPIDF(true)
            .useSecondaryHeadingPIDF(true)
            .useSecondaryDrivePIDF(true)
            ;
    public static PinpointConstants localizerConstants = new PinpointConstants()

            .forwardPodY(0.5)
            .strafePodX(0.5)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);


    public static MecanumConstants driveConstants = new MecanumConstants()

            .maxPower(1)
            .rightFrontMotorName("rfDrive")
            .rightRearMotorName("rbDrive")
            .leftRearMotorName("lbDrive")
            .leftFrontMotorName("lfDrive")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(60.7)
            .yVelocity(43.5)
    ;

    public static PathConstraints pathConstraints = new PathConstraints(0.8, 100, 1, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
    }
}
