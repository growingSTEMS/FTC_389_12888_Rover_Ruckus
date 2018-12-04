package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Arcade Drive", group = "")
public class Arcade_Drive extends Teleop_Parent {
    private final double P2_MULT = 0.5;

    @Override
    public void begin() {

    }

    //Begins teleop
    @Override
    public void run() {
        double forwardPower = gamepad1.left_stick_y + gamepad2.left_stick_x*P2_MULT;
        double turnPower = gamepad1.right_stick_x + gamepad2.right_stick_x*P2_MULT;
        double strafePower = -gamepad1.left_stick_x + gamepad2.left_stick_y*P2_MULT;

        if (Math.abs(strafePower) > Math.abs(forwardPower))
            forwardPower = 0.0;
        else
            strafePower = 0.0;

        //Slows the drive by a certain factor if true
        if (driveSlowFactor)
        {
            forwardPower *= SLOW_DRIVE_SCALE_FACTOR;
            strafePower *= SLOW_DRIVE_SCALE_FACTOR;
        }
        setDrive(forwardPower, strafePower, turnPower);

        //Lifts the arm to certain positions and maps them to certain joystick positions
        if(gamepad1.right_bumper){
            long startTime = System.currentTimeMillis();
            while(System.currentTimeMillis() < startTime + 750){
                setArmRotator(LIFT_POWER_UP * (0.0013 * (System.currentTimeMillis() - startTime)));
            }
           setArmRotator(LIFT_POWER_UP);
        }
        else if(gamepad1.right_trigger > 0.1f){
            long startTime = System.currentTimeMillis();
            while(System.currentTimeMillis() < startTime + 750){
                setArmRotator(LIFT_POWER_DOWN * (0.0013 * (System.currentTimeMillis() - startTime)));
            }
            setArmRotator(LIFT_POWER_DOWN);
        }
        else{
            holdArmPosition();
        }

        //Extends the arm to certain positions, and maps them to certain joystick positions
        if(gamepad1.left_bumper){
            setArmExtender(LIFT_POWER_UP);
        }
        else if(gamepad1.left_trigger > 0.1f){
            setArmExtender(LIFT_POWER_DOWN);
        }
        else{
            setArmExtender(0.0);
        }

        //Set lander to certain positions, and maps them to certain joystick positions
        if(gamepad1.y){
            setArmLander(LIFT_POWER_UP);
        }
        else if(gamepad1.a){
            setArmLander(LIFT_POWER_DOWN);
        }
        else{
            setArmLander(LIFT_POWER_IDLE);
        }

        //Enables or disables a slower drive
        if(gamepad1.dpad_up) {
            driveSlowFactor = true;
        }
        else if(gamepad1.dpad_down) {
            driveSlowFactor = false;
        }

        if(gamepad1.x){
            setIntakeMotor(INTAKE_POWER_END);
        } else if(gamepad1.b) {
            setIntakeMotor(-INTAKE_POWER_END);
        }else{
            setIntakeMotor(0.0);
        }

        if(gamepad1.dpad_left){
            setIntakeShifter(INTAKE_SHIFTER_POWER);
        }
        else{
            setIntakeShifter(0.0);
        }
    }
}
