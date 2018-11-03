package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class Robot_Parent extends LinearOpMode {

    public final boolean IS_OCTO = true;

    protected DcMotor leftDrive;
    protected DcMotor rightDrive;
    protected DcMotor middleDrive;

    protected DcMotor backLeftDrive;
    protected DcMotor backRightDrive;
    protected DcMotor frontLeftDrive;
    protected DcMotor frontRightDrive;


    protected PID_Controller goToTurnPID = new PID_Controller(0.025, 0.0, 0.0);

    public double p = 0.0854;
    public double d = 0.00760;
    //Stable Gains: P = 0.02 D = 0.00293
    //Testing gains p = 0.0854 d = 0.00760

    protected PID_Controller holdTurnPID = new PID_Controller(p, 0.0, d);

    private BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        if (IS_OCTO) {
            backLeftDrive = hardwareMap.get(DcMotor.class, "bld");
            backRightDrive = hardwareMap.get(DcMotor.class, "brd");
            frontLeftDrive = hardwareMap.get(DcMotor.class, "fld");
            frontRightDrive = hardwareMap.get(DcMotor.class, "frd");

            backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
            backRightDrive.setDirection(DcMotor.Direction.FORWARD);
            frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
            frontRightDrive.setDirection(DcMotor.Direction.FORWARD);

            backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            leftDrive = hardwareMap.get(DcMotor.class, "ld");
            rightDrive = hardwareMap.get(DcMotor.class, "rd");
            middleDrive = hardwareMap.get(DcMotor.class, "md");

            leftDrive.setDirection(DcMotor.Direction.REVERSE);
            rightDrive.setDirection(DcMotor.Direction.FORWARD);
            middleDrive.setDirection(DcMotor.Direction.FORWARD);
            //middle drive assumes motor faces backwards. Switch if motor faces forwards

            leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            middleDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }


        setupImu();

        Heading.setImu(imu);
        Heading.setFieldOffset(-Heading.getAbsoluteHeading());

        initialize();
        telemetry.addLine("Initialization Complete");
        telemetry.update();
        waitForStart();
        play();
    }

    public abstract void initialize();

    public abstract void play();

    public abstract void setup();

    public abstract void begin();

    // Functions

    protected void setDrive(double forwardPower, double turnPower, double strafePower) {
        if (IS_OCTO) {
            backLeftDrive.setPower(forwardPower + turnPower - strafePower);
            backRightDrive.setPower(forwardPower - turnPower + strafePower);
            frontLeftDrive.setPower(forwardPower + turnPower + strafePower);
            frontRightDrive.setPower(forwardPower - turnPower - strafePower);
        } else {
            leftDrive.setPower(forwardPower + turnPower);
            rightDrive.setPower(forwardPower - turnPower);
            middleDrive.setPower(strafePower);
        }
    }

    private void setupImu() {
        BNO055IMU.Parameters imuParameters = new BNO055IMU.Parameters();
        imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        imuParameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        imuParameters.loggingEnabled = true;
        imuParameters.loggingTag = "IMU";
        imuParameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu.initialize(imuParameters);
    }
}
