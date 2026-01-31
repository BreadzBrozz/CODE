package org.firstinspires.ftc.teamcode.pedroPathing;

import static org.firstinspires.ftc.teamcode.pedroPathing.RightSide.PathState.shoot_collect1;
import static org.firstinspires.ftc.teamcode.pedroPathing.RightSide.PathState.shoot_end;
import static org.firstinspires.ftc.teamcode.pedroPathing.RightSide.PathState.start_shoot;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(name= "pedro's path RIGHT ON BACK")
public class RightSide extends OpMode{

    private DcMotor intake = null;
    private DcMotorEx launch2 = null;

    public int shot = 0;
    public int shots = 0;

    private Follower follower;

    private Timer pathTimer, opModeTimer;
    public enum PathState {
        start_shoot,
        shooting1,
        shooting2,
        shoot_collect1,
        collect1_collect2,
        collect2_shoot,
        shoot_end,

        launchon,
        launchoff,
        intakeon,
        intakeoff,

        launchon2,
        launchoff2,
        intakeon2,
        intakeoff2,

        ending
    }



    private final Pose startPose = new Pose(88.330,7.787, Math.toRadians(90));
    private final Pose shootPose = new Pose(73.65435745937961, 21.536, Math.toRadians(66));

    private final Pose collect1 = new Pose(99.970, 34.457, Math.toRadians(180));

    private final Pose collect2 = new Pose(131.682, 34.429, Math.toRadians(180));

    private final Pose endPose = new Pose(81.132,39.298, Math.toRadians(90));

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
                if (!follower.isBusy()) {
                    follower.followPath(StartShoot, true);
                    setPathState(PathState.launchon);}
                break;
            case shooting1:
                break;
            case shoot_collect1:
                if (!follower.isBusy()) {
                    follower.followPath(ShootCollect1, true);
                    setPathState(PathState.collect1_collect2);}
                break;
            case collect1_collect2:
                if (!follower.isBusy()) {
                    intake.setPower(-1);
                    follower.followPath(Collect1Collect2, true);
                    setPathState(PathState.collect2_shoot);}
                break;
            case collect2_shoot:
                if (!follower.isBusy()) {
                    intake.setPower(0);
                    follower.followPath(Collect2Shoot, true);
                    setPathState(PathState.launchon2);}
                break;
            case shooting2:
                break;
            case shoot_end:
                if (!follower.isBusy()) {
                    follower.followPath(ShootEnd, true);
                    setPathState(PathState.ending);}
                break;
            case ending:
                break;
            case launchon:
                if(!follower.isBusy()){

                    launch2.setPower(0.9);
                    setPathState(PathState.intakeon);
                }
                break;
            case intakeon:
                if(!follower.isBusy() && pathTimer.getElapsedTime() > 3000){
                    intake.setPower(-1);
                    shot++;
                    setPathState(PathState.intakeoff);
                }
                break;
            case intakeoff:
                if(!follower.isBusy() && pathTimer.getElapsedTime() > 500){
                    intake.setPower(0);
                    if(shot == 1){
                        setPathState(PathState.launchon);
                    }
                    else{
                        setPathState(PathState.launchoff);}
                }
                break;
            case launchoff:
                if(!follower.isBusy() && pathTimer.getElapsedTime() > 300){

                    launch2.setPower(0);
                    setPathState(shoot_collect1);
                }
                break;
            case launchon2:
                if(!follower.isBusy()){
                    launch2.setPower(0.9);
                    setPathState(PathState.intakeon2);
                }
                break;
            case intakeon2:
                if(!follower.isBusy() && pathTimer.getElapsedTime() > 3000){
                    intake.setPower(-1);
                    shots++;
                    setPathState(PathState.intakeoff2);
                }
                break;
            case intakeoff2:
                if(!follower.isBusy() && pathTimer.getElapsedTime() > 600){
                    intake.setPower(0);
                    if(shots == 1){
                        setPathState(PathState.launchon2);
                    }
                    else{
                        setPathState(PathState.launchoff2);}
                }
                break;
            case launchoff2:
                if(!follower.isBusy() && pathTimer.getElapsedTime() > 300){

                    launch2.setPower(0);
                    setPathState(shoot_end);
                }
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
        shoot = 0;


        follower = Constants.createFollower(hardwareMap);

        intake = hardwareMap.get(DcMotor.class, "intake");
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