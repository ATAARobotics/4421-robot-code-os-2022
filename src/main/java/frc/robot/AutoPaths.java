package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoPaths {
    /*
     * Path variable declarations, should be formatted as:
     * 
     * private Trajectory pathName;
     */

    private static Trajectory test1;
    private static Trajectory test2;

    public static void CreateAutoPaths() {

        test1 = TrajectoryBuilder(
                Math.PI / 2,
                Arrays.asList(
                        new Translation2d(meterConversion(6), meterConversion(5.5)),
                        new Translation2d(meterConversion(7), meterConversion(5.5))),
                Math.PI / 2);

        test2 = TrajectoryBuilder(
                Math.PI / 2,
                Arrays.asList(
                        new Translation2d(meterConversion(7), meterConversion(5.5)),
                        new Translation2d(meterConversion(7), meterConversion(6.5))),
                Math.PI);
    }

    public static Trajectory getTest1() {
        return test1;
    }

    public static Trajectory getTest2() {
        return test2;
    }

    private static Trajectory TrajectoryBuilder(double rotationOffset, List<Translation2d> waypoints,
            double targetAngle) {
        // Configure the path to not exceed the maximum speed or acceleration specified
        // in RobotMap
        TrajectoryConfig trajectoryConfig = new TrajectoryConfig(Constants.MAXIMUM_SPEED,
                Constants.MAXIMUM_ACCELERATION);

        // Store the waypoints for data logging purposes
        if (Constants.AUTO_PATH_LOGGING_ENABLED) {
            waypoints = new ArrayList<Translation2d>(waypoints);
        }

        waypoints = new ArrayList<Translation2d>(waypoints);

        // Get the first and last two points in this path. Some of these may be the
        // same.
        Translation2d firstPoint = waypoints.get(0);
        Translation2d secondPoint = waypoints.get(1);
        Translation2d secondLastPoint = waypoints.get(waypoints.size() - 2);
        Translation2d lastPoint = waypoints.get(waypoints.size() - 1);

        /**
         * Get the angle that the robot should aim for and end with based on the angle
         * to the second and last waypoint.
         * The purpose of this is to prevent the robot from adding a slight bulge to the
         * trajectory, as it thinks that
         * the robot needs to move to be able to turn. Although this would be correct in
         * a differential drive, it is
         * unneccessary with a swerve - so this shaves off a little bit of time by going
         * straight to the waypoints.
         */
        double firstRotation = Math.atan2(secondPoint.getY() - firstPoint.getY(),
                secondPoint.getX() - firstPoint.getX());
        double lastRotation = Math.atan2(lastPoint.getY() - secondLastPoint.getY(),
                lastPoint.getX() - secondLastPoint.getX());

        // Remove the first and last waypoints from the list, as we are going to
        // manually specify their rotation
        if (Constants.REPORTING_DIAGNOSTICS) {
            SmartDashboard.putString("Waypoints", waypoints.toString());
        }
        waypoints.remove(0);
        waypoints.remove(waypoints.size() - 1);

        // Create the trajectory based on the waypoints and computed angles
        return TrajectoryGenerator.generateTrajectory(new Pose2d(firstPoint, new Rotation2d(firstRotation)), waypoints,
                new Pose2d(lastPoint, new Rotation2d(lastRotation)), trajectoryConfig);
    }

    // Convert meters to Jacob units
    private static double meterConversion(double meters) {
        return (0.5 * meters) + (0.1811 * Math.signum(meters));
    }

}
