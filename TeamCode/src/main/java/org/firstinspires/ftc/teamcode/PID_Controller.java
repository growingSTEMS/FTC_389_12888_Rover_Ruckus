package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by user on 7/10/2018.
 */
public class PID_Controller{
    //Sets up empty PID controller values and other variables

    ElapsedTime runtime;

    private double setpoint = 0.0;
    private double error = 0.0;
    private double lastError = 0.0;
    private double time = 0.0;
    private double lastTime = 0.0;
    private double pValue = 0.0;
    private double PGAIN;
    private double iValue = 0.0;
    private double IGAIN;
    private double dValue = 0.0;
    private double DGAIN;

    //Class constructor which sets inputs as their respective gains
    public PID_Controller(double PGAIN, double IGAIN, double DGAIN){
        this.runtime = new ElapsedTime();
        resetPID();
        this.PGAIN = PGAIN;
        this.IGAIN = IGAIN;
        this.DGAIN = DGAIN;
    }
    //Creates input as setpoint to be used in PID
    public void setSetpoint(double newSetpoint) {
        this.setpoint = newSetpoint;
    }
    public double getSetpoint(){return setpoint;}

    //Sets up p, i, and d values based on their respective formulas, and sets up errors, setpoints,
    // and times, and processes them, returns the sum of p, i, and d values
    public double update(double input){
        lastError = error;
        lastTime = time;
        error = setpoint - input;
        time = runtime.seconds();
        pValue = PGAIN * error;
        iValue += IGAIN * (lastError + error) * (0.5) * (time - lastTime);
        dValue = DGAIN * (error - lastError) / (time - lastTime);
        return getPID();
    }
    //Clears integral value
    public void resetPID() {
        resetPID(0.0);
    }

    //Clears time values and Integral values
    public void resetPID(double startingIValue) {
        runtime.reset();
        iValue = startingIValue;
    }

    //Shows current PID values to the console
    public void displayCurrentPID(Telemetry telemetry) {
        telemetry.addData("P: ",pValue);
        telemetry.addData("I: ",iValue);
        telemetry.addData("D: ",dValue);
        telemetry.addData("PID: ",getPID());
    }
    //Gets the sum of all PID values
    private double getPID() {
        return pValue + iValue + dValue;
    }
}