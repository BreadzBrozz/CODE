package org.firstinspires.ftc.teamcode.pedroPathing;

import static org.firstinspires.ftc.teamcode.pedroPathing.pedrosonlypath.PathState.start_shoot;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(name= "pedro's only path")
public class pedrosonlypath extends OpMode{

    private DcMotor intake = null;
    private DcMotor launch = null;
    private DcMotorEx launch2 = null;

    private Follower follower;

    private Timer pathTimer, opModeTimer;
    public enum PathState {
        start_shoot,
        shooting,
        shoot_collect1,
        collect1_collect2,
        collect2_shoot,
        shoot_end
    }



    private final Pose startPose = new Pose(56.000,8.000, Math.toRadians(90));
    private final Pose shootPose = new Pose(71.245, 23.510, Math.toRadians(55));
    private final Pose collect1 = new Pose(52.510, 34.867, Math.toRadians(-31));
    private final Pose collect2 = new Pose(18.714, 35.204, Math.toRadians(0));
    private final Pose endPose = new Pose(72.000,38.000, Math.toRadians(90));

    private PathChain StartShoot;
    private PathChain ShootCollect1;
    private PathChain Collect1Collect2;
    private PathChain Collect2Shoot;
    private PathChain ShootEnd;

    public int shoot = 0;


    public void buildPaths() {
        StartShoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(),shootPose.getHeading())
                .build();

        ShootCollect1 = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, collect1))
                .setReversed()
                .build();
        Collect1Collect2 = follower.pathBuilder()
                .addPath(new BezierLine(collect1,collect2))
                .setReversed()
                .build();
        Collect2Shoot = follower.pathBuilder()
                .addPath(new BezierLine(collect2,shootPose))
                .setLinearHeadingInterpolation(collect2.getHeading(),shootPose.getHeading())
                .build();
        ShootEnd = follower.pathBuilder()
                .addPath(new BezierLine(shootPose,endPose))
                .build();
    }

    PathState path_State;
    public void statePathUpdate() {
        switch(path_State) {
            case start_shoot:
                follower.followPath(StartShoot, false);
                setPathState(PathState.shoot_collect1);
                break;
            case shooting:
                if(!follower.isBusy()){
                    launch.setPower(0.82);
                    launch2.setPower(-0.82);
                    while(pathTimer.getElapsedTime() < 3000){}
                    pathTimer.resetTimer();

                    intake.setPower(-1);

                    while(pathTimer.getElapsedTime() < 200){}
                    pathTimer.resetTimer();

                    launch.setPower(0.83);
                    launch2.setPower(-0.83);
                    intake.setPower(0);
                    while(pathTimer.getElapsedTime() < 1000){}
                    pathTimer.resetTimer();

                    intake.setPower(-1);
                    while(pathTimer.getElapsedTime() < 1000){}
                    pathTimer.resetTimer();

                    intake.setPower(0);
                    launch.setPower(0);
                    launch2.setPower(0);
                    while(pathTimer.getElapsedTime() < 100){}
                    pathTimer.resetTimer();
                    if(shoot==1){
                        shoot = 2;
                        setPathState(PathState.shoot_end);
                    }
                    else if(shoot==0){
                        shoot = 1;
                        setPathState(PathState.shoot_collect1);
                    }
                    break;
                }
            case shoot_collect1:
                if (!follower.isBusy()) {
                follower.followPath(ShootCollect1, false);
                setPathState(PathState.collect1_collect2);}
                break;
            case collect1_collect2:
                if (!follower.isBusy()) {
                intake.setPower(-1);
                follower.followPath(Collect1Collect2, false);
                intake.setPower(0);
                setPathState(PathState.collect2_shoot);}
                break;
            case collect2_shoot:
                if (!follower.isBusy()) {
                follower.followPath(Collect2Shoot, true);
                setPathState(PathState.shoot_end);}
                break;
            case shoot_end:
                if (!follower.isBusy()) {
                follower.followPath(ShootEnd, true);}
                break;
            default:
                break;
        }

    }

    public void setPathState(PathState newState){
        path_State = newState;
        pathTimer.resetTimer();
    }

    @Override
    public void init(){
        path_State = start_shoot;
        pathTimer = new Timer();
        opModeTimer = new Timer();

        follower = Constants.createFollower(hardwareMap);

        intake = hardwareMap.get(DcMotor.class, "intake");
        launch = hardwareMap.get(DcMotor.class, "launch");
        launch2 = hardwareMap.get(DcMotorEx.class, "launch2");

        buildPaths();
        follower.setStartingPose(startPose);
    }
    @Override
    public void start() {
        opModeTimer.resetTimer();
        setPathState(start_shoot);
    }

    @Override
    public void loop(){
        follower.update();
        statePathUpdate();

    }

}

