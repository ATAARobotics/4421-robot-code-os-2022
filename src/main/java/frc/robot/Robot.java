package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class Robot extends TimedRobot {

    // Timer for keeping track of when to disable brakes after being disabled so
    // that the robot stops safely - DO NOT USE COMMANDS-DOES NOT WORK WHEN DISABLED
    private Timer brakesOffTimer = new Timer();

    private RobotContainer robotContainer = null;

    private Command m_autonomousCommand = null;;

    public Robot() {
        robotContainer = new RobotContainer();
        if (!Constants.COMP_MODE) {
            DriverStation.silenceJoystickConnectionWarning(true);
        } else {
            DriverStation.silenceJoystickConnectionWarning(false);
        }
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        if (Constants.REPORTING_DIAGNOSTICS) {
            SmartDashboard.putNumber("Battery Voltage", RobotController.getBatteryVoltage());
            SmartDashboard.putNumber("Drive Controller Temp",
                    robotContainer.getSwerveDriveSubsystem().getDriveTemperature());
            SmartDashboard.putNumber("Rotation Controller Temp",
                    robotContainer.getSwerveDriveSubsystem().getRotationTemperature());
            SmartDashboard.putNumber("Robot Heading", robotContainer.getSwerveDriveSubsystem().getHeading());
        }
    }

    @Override
    public void disabledInit() {
        // Cancel all commands
        CommandScheduler.getInstance().cancelAll();
        // Write remaining blackbox data to file
        Blackbox.getInstance().finishLog();
        // Start brake timer
        brakesOffTimer.reset();
        brakesOffTimer.start();
    }

    @Override
    public void disabledPeriodic() {
        if (brakesOffTimer.get() > 2.5) {
            brakesOffTimer.stop();
            brakesOffTimer.reset();
            robotContainer.getSwerveDriveSubsystem().setBrakes(false);
        }
    }

    @Override
    public void autonomousInit() {
        m_autonomousCommand = robotContainer.getAutonomousChooser().getSelected();
        robotContainer.AutoInit(0);
        m_autonomousCommand.schedule();
    }

    @Override
    public void autonomousPeriodic() {
        Blackbox.getInstance().periodic();
    }

    @Override
    public void teleopInit() {
        if(!Constants.FIELD_ORIENTED){
            robotContainer.getSwerveDriveSubsystem().setFieldOriented(false, 0);
        }
        Blackbox.getInstance().startLog();

        Blackbox.getInstance().addLog("Gyro Reading", robotContainer.getSwerveDriveSubsystem()::getHeading);
        Blackbox.getInstance().addLog("Field Oriented", robotContainer.getSwerveDriveSubsystem()::getFieldOriented);
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
            m_autonomousCommand = null;
        }
        robotContainer.getSwerveDriveSubsystem().setBrakes(true);

        if (!Constants.FIELD_ORIENTED) {
            robotContainer.getSwerveDriveSubsystem().setFieldOriented(false, 0);
        }
    }

    @Override
    public void teleopPeriodic() {
        Blackbox.getInstance().periodic();
        robotContainer.getOI().checkInputs();

        if (Constants.REPORTING_DIAGNOSTICS) {
            SmartDashboard.putNumber("Joy X", robotContainer.getOI().getXVelocity());
            SmartDashboard.putNumber("Joy Y", robotContainer.getOI().getYVelocity());
            SmartDashboard.putNumber("Rotation", robotContainer.getOI().getRotationVelocity());
            SmartDashboard.putNumber("Slider", robotContainer.getOI().getSpeed());
        }
    }

    @Override
    public void testInit() {
        Blackbox.getInstance().startLog();
        CommandScheduler.getInstance().cancelAll();
    }
}
