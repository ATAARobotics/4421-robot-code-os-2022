package frc.robot;

import edu.wpi.first.wpilibj.Preferences;

/**
 * A centralized file that keeps track of constants of the robot, such as device
 * IDs, device ports and robot dimensions
 * 
 * This is not the same as the RobotMaps from previous years, the only thing in
 * this class is constants, each hardware class defines its own motors and
 * whatnot
 */
public class Constants {
    // Disables some safeties and enables logging of warnings we expect and know
    // about during development
    public static final boolean COMP_MODE = false;
    public static final boolean USING_PIGEON = true;

    // Enforces a maximum safe speed of the motors. This may cause steering issues.
    public static final double MAX_SAFE_SPEED_OVERRIDE = COMP_MODE ? 1.0 : 0.8;

    // Measurements are in meters
    public static final double WHEELBASE = 0.67945;
    public static final double TRACK_WIDTH = 0.4953;

    // Maximum linear speed is in meters/second
    public static final double MAXIMUM_SPEED = 1.25;
    // USED ONLY IN AUTO - Maximum acceleration is in meters/second/second
    public static final double MAXIMUM_ACCELERATION = 2.0;

    // Maximum rotational speed is in radians/second
    public static final double MAXIMUM_ROTATIONAL_SPEED = Math.PI;
    // USED ONLY IN AUTO - Maximum rotational acceleration is in
    // radians/second/second
    public static final double MAXIMUM_ROTATIONAL_ACCELERATION = Math.PI;

    // Swerve offset
    public static final double[] ANGLE_OFFSET = new double[] {
            0.3528, -3.0526, -2.4252, -2.5863
    };

    /*
     * CAN ID and CAN Bus
     * CAN Bus options supported: "rio", "rio"
     * ***IF CANIVORE FAILS CHANGE SWERVE_BUS_ACTIVE TO false***
     */

    // CAN FD Device IDs
    public static final int[] DRIVE_MOTORS_ID = { 1, 2, 3, 4 };
    public static final int[] ROTATION_MOTORS_ID = { 5, 6, 7, 8 };
    public static final int[] ROTATION_ENCODERS_ID = { 9, 10, 11, 12 };
    public static final int PIGEON_ID = 20;

    /*
     * CAN Bus (Legacy) NOT CURRENTLY SUPPORTED
     * public static final String SPARK_MOTOR_BUS = "rio";
     */

    // PWM Ports

    // Sensor Ports

    // Solenoid Ports
    public static final int[] CLAW_PNEUMATIC = { 6 , 7 }; // Change Later when claw is built
    // Sensor config

    // Drive encoder ticks per meter
    public static final double[] TICKS_PER_METER = new double[] {
            43423.315, 45649.9148, 45547.4436, 45648.5925
    };

    public static final int PrimaryArmMotorID = 21;
    // 9000 is not the ID
    public static final int SecondaryArmMotorID = 24;

    public static final int ArmMotorID = 21;
    public static final int ArmMotorID2 = 24;
    public static final int ElevatorMotor = 22;

    public static final int ArmCANCoderID = 23;
    // Maximum arm rotation (in degrees)
    public static final double ArmMaxAngle = 200;
    // Minimum arm rotation (in degrees)
    public static final double ArmMinAngle = 5;

    public static final double ElevatorTop = 233;

    // DRIVER CONFIG
    // Dead zones of each joystick - Measured from 0 to 1. This should always be at
    // least 0.1.
    public static final double JOY_DEAD_ZONE = 0.3;
    // Whether teleop should start in field oriented mode
    public static final boolean FIELD_ORIENTED = true;
    // The sensitivity value for the joysticks - the values are exponentiated to
    // this value, so higher numbers result in a lower sensitivity, 1 results in
    // normal sensitivity, and decimals increase sensitivity
    public static final double JOYSTICK_SENSITIVITY = 2;
    public static final double TURNING_SENSITIVITY = 5;
    // LOGGING
    // Set this to true if you want to log diagnostics to SmartDashboard
    public static final boolean REPORTING_DIAGNOSTICS = true;
    // Set this to true if you want to visualize the robot's movement during auto -
    // talk to Jacob if you have no idea what this does
    public static final boolean AUTO_PATH_LOGGING_ENABLED = false;
}
