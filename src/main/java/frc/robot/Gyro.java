package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import com.ctre.phoenix.sensors.Pigeon2.AxisDirection;

public class Gyro {
    private AHRS navX;
    private double offset;
    private Pigeon2 pigeon;
    private double currentYaw = 0.0;

    /**
     * Resets the navX reading to be straight ahead
     */
    public Gyro() {

        if (Constants.USING_PIGEON) {
            pigeon = new Pigeon2(Constants.PIGEON_ID, "rio");

            // Mount direction settings - (forward, up) as according to the pigeon's casing
            pigeon.configMountPose(AxisDirection.PositiveX, AxisDirection.PositiveZ);

            // CALIBRATION OF PIGEON (attempt to complete all steps quickly):
            // 1. Drive the robot flush with a flat surface
            // 2. Restart the robot code or redeploy
            // 3. Spin the robot 10 rotations, at full speed, clockwise
            // 4. Drive the robot back to the surface
            // 5. Mark down the heading of the robot
            // 6. Repeat steps 3-4, but spin counterclockwise instead of clockwise
            // 7. Mark down the heading of the robot
            // If the heading from step 7 is not within +-45 degrees, there might be a
            // hardware problem - try mounting the pigeon in a different orientation.
            // Otherwise, to calculate the error:
            // error = ((heading from step 5 - (heading from step 7 / 2)) / 10) * (180 / PI)
            // Repeat the whole process a couple times, tweaking the value, until you end up
            // with a value from step 5 that is very close to half the value from step 7.
            // PUT YOUR ERROR VALUE IN HERE:
            pigeon.configYAxisGyroError(3.5);

            pigeon.setYaw(0);
        } else {
            try {
                // Initializes the navX object on the roboRIO's MXP port and resets it
                navX = new AHRS(SPI.Port.kMXP);
                navX.reset();
            } catch (RuntimeException ex) {
                DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
            }
            offset = 0;
            navX.calibrate();
        }
    }

    public void reset() {
        navX.reset();
    }

    public void setOffset(double rotationOffset) {
        offset = rotationOffset;
    }

    /**
     * Gets the value from the navX, measured in radians from -Pi to Pi
     */
    public double getAngle() {
        if (navX != null) {
            double angle = navX.getYaw();

            // Convert to radians
            angle = Math.toRadians(angle) + offset;

            // Offset by Pi to find values in the wrong half of the circle
            angle += Math.PI;

            // Wrap angle at 2*Pi
            angle %= 2.0 * Math.PI;

            // Ensure the value is not negative
            if (angle < 0) {
                angle += 2.0 * Math.PI;
            }

            // Undo the offset
            angle -= Math.PI;

            return angle;
        } else {
            return currentYaw;
        }

    }

    public void update() {

        if (pigeon != null) {
            double yaw = -pigeon.getYaw();

            yaw *= Math.PI / 180.0;

            yaw %= Math.PI * 2;

            yaw += Math.PI * 3;

            yaw %= Math.PI * 2;

            yaw -= Math.PI;

            currentYaw = yaw;
        }
    }

    public void setYaw(double yaw) {
        if (pigeon != null) {
            pigeon.setYaw(yaw);
            currentYaw = yaw;
        }
    }

}
