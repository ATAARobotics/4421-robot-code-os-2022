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
    private Gyro gyro;
    private final OI joysticks = new OI();

    private final SwerveDriveSubsystem m_swerveDriveSubsystem;

    // Auto selector on SmartDashboard
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();

    public RobotContainer() {
        // Hardware-based objects
        // NetworkTableInstance inst = NetworkTableInstance.getDefault();
        gyro = new Gyro();
        AutoPaths.CreateAutoPaths();

        m_swerveDriveSubsystem = new SwerveDriveSubsystem(gyro, initialPosition, "rio");

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
        joysticks.toggleFieldOriented.whenPressed(new InstantCommand(() -> {
            m_swerveDriveSubsystem.setFieldOriented(!m_swerveDriveSubsystem.getFieldOriented(), 0);
        }));
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
