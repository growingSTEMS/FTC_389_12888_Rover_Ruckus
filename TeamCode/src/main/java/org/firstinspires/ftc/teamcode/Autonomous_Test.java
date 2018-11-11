package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Autonomous_Test")
public class Autonomous_Test extends Autonomous_Parent {

    private final long sleepTime = 2000;

    @Override
    public void setup() {
    }

    @Override
    public void begin() {
        // TODO: find why all turns after first turn fail
        driveDistance(36);
        sleep(sleepTime);

        turn (90);
        sleep(sleepTime);

        driveDistance(36);
        sleep(sleepTime);

        turn(-90);
        sleep(sleepTime);

        driveDistance(-36);
        sleep(sleepTime);

        turn(-90);
        sleep(sleepTime);

        driveDistance(36);
        sleep(sleepTime);

        turn(90);
        sleep(sleepTime);
    }
}