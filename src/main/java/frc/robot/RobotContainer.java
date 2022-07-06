package frc.robot;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

//swerve commands and subsystems
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.SwerveDriveSubsystem;

// import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class RobotContainer {

    // The initial position of the robot relative to the field. This is measured
    // from the left-hand corner of the field closest to the driver, from the
    // driver's perspective
    public Translation2d initialPosition = new Translation2d(0, 0);

    // Create hardware objects
    private Pigeon pigeon;
    private final OI joysticks = new OI();

    private final SwerveDriveSubsystem m_swerveDriveSubsystem;

    private boolean visionEnabled = false;
    /*
     * private boolean visionTargeting = false;
     * private ProfiledPIDController visionPID = new ProfiledPIDController(0.9, 0,
     * 0.001, new TrapezoidProfile.Constraints(RobotMap.MAXIMUM_ROTATIONAL_SPEED /
     * 4, RobotMap.MAXIMUM_ROTATIONAL_ACCELERATION / 2));
     * private double visionTarget = -999;
     * private int targetedTicks = 0;
     */

    private double aimRotationSpeed = 0.25 * 0.7;
    private double visionRotationVelocity;

    // Auto selector on SmartDashboard
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();

    public RobotContainer() {
        // Hardware-based objects
        // NetworkTableInstance inst = NetworkTableInstance.getDefault();
        pigeon = new Pigeon();
        AutoPaths.CreateAutoPaths();

        m_swerveDriveSubsystem = new SwerveDriveSubsystem(pigeon, initialPosition, "rio");

        m_swerveDriveSubsystem.setBrakes(false);

        m_swerveDriveSubsystem.setDefaultCommand(
                new DriveCommand(m_swerveDriveSubsystem, joysticks::getXVelocity, joysticks::getYVelocity,
                        joysticks::getRotationVelocity, joysticks::getSpeed, () -> 0.8 * joysticks.getSpeed()));

        autoChooser.addOption("DO NOTHING", new WaitCommand(0));
        SmartDashboard.putData(autoChooser);
        LiveWindow.disableAllTelemetry();

        configureBindings();
    }

    private void configureBindings() {
        joysticks.aimLeft.whenHeld(new DriveCommand(m_swerveDriveSubsystem, joysticks::getXVelocity,
                joysticks::getYVelocity, () -> -aimRotationSpeed, joysticks::getSpeed));
        joysticks.aimRight.whenHeld(
                new DriveCommand(m_swerveDriveSubsystem, joysticks::getXVelocity,
                        joysticks::getYVelocity, () -> aimRotationSpeed, joysticks::getSpeed));

        new Trigger(() -> visionEnabled).whileActiveOnce(new DriveCommand(m_swerveDriveSubsystem,
                joysticks::getXVelocity, joysticks::getYVelocity, () -> visionRotationVelocity));

    }

    public OI getOI() {
        return joysticks;
    }

    public SwerveDriveSubsystem getSwerveDriveSubsystem() {
        return m_swerveDriveSubsystem;
    }

    public SendableChooser<Command> getAutonomousChooser() {
        return autoChooser;
    }
}
