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
import frc.robot.commands.auto.*;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.math.controller.ProfiledPIDController;
// import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class RobotContainer {

    // The initial position of the robot relative to the field. This is measured
    // from the left-hand corner of the field closest to the driver, from the
    // driver's perspective
    public Translation2d initialPosition = new Translation2d(0, 0);

    // Create hardware objects
    private Pigeon pigeon;
    private final OI joysticks = new OI();

    private final SwerveDriveSubsystem m_swerveDriveSubsystem;
    private final ArmSubsystem m_armSubsystem;
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final ClawSubsystem m_clawSubsystem;
    private final AutoPaths m_autoPaths;

    // Auto Stuff
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();
    public static ProfiledPIDController rotationController = new ProfiledPIDController(0.9, 0, 0.001, new TrapezoidProfile.Constraints(Constants.MAXIMUM_ROTATIONAL_SPEED_AUTO, Constants.MAXIMUM_ROTATIONAL_ACCELERATION));

    public RobotContainer() {
        // Hardware-based objects
        // NetworkTableInstance inst = NetworkTableInstance.getDefault();
        pigeon = new Pigeon();

        m_swerveDriveSubsystem = new SwerveDriveSubsystem(pigeon, initialPosition, "rio");
        m_armSubsystem = new ArmSubsystem();
        m_elevatorSubsystem = new ElevatorSubsystem();
        m_clawSubsystem = new ClawSubsystem();
        m_autoPaths = new AutoPaths();

        m_swerveDriveSubsystem.setBrakes(false);

        m_swerveDriveSubsystem.setDefaultCommand(
                new DriveCommand(m_swerveDriveSubsystem, joysticks::getXVelocity, joysticks::getYVelocity,
                        joysticks::getRotationVelocity, joysticks::getSpeed, () -> 0.8 * joysticks.getSpeed()));
        autoChooser.addOption("test", new Straight(m_swerveDriveSubsystem, m_autoPaths));
        autoChooser.addOption("DO NOTHING", new WaitCommand(0));
        SmartDashboard.putData(autoChooser);
        LiveWindow.disableAllTelemetry();

        configureBindings();
    }
    public void AutoInit(double rotation){
      rotationController.enableContinuousInput(-Math.PI, Math.PI);

      rotationController.reset(new TrapezoidProfile.State(rotation, 0.0));

  }
    private void configureBindings() {
        joysticks.toggleFieldOriented.whenPressed(new InstantCommand(() -> {
            m_swerveDriveSubsystem.setFieldOriented(!m_swerveDriveSubsystem.getFieldOriented(), 0);
        }));
        joysticks.ArmOverride.whenActive(
          new DriveCommand(m_swerveDriveSubsystem, ()-> 0,()-> -0.2,()-> 0)
        ).whenInactive(new DriveCommand(m_swerveDriveSubsystem, joysticks::getXVelocity, joysticks::getYVelocity,
                          joysticks::getRotationVelocity, joysticks::getSpeed, () -> 0.8 * joysticks.getSpeed()));
        joysticks.ArmUp.whenActive(
            new RunCommand(m_armSubsystem::armUp, m_armSubsystem)
          ).whenInactive(
            new InstantCommand(m_armSubsystem::stop, m_armSubsystem)
          );
      
        joysticks.ArmDown.whenActive(
            new RunCommand(m_armSubsystem::armDown, m_armSubsystem)
          ).whenInactive(
            new InstantCommand(m_armSubsystem::stop, m_armSubsystem)
          );


        joysticks.ElevatorUp.whenActive(
            new RunCommand(m_elevatorSubsystem::elevatorUp, m_elevatorSubsystem)
          ).whenInactive(
            new InstantCommand(m_elevatorSubsystem::stop, m_elevatorSubsystem)
          );
      
        joysticks.ElevatorDown.whenActive(
            new RunCommand(m_elevatorSubsystem::elevatorDown, m_elevatorSubsystem)
          ).whenInactive(
            new InstantCommand(m_elevatorSubsystem::stop, m_elevatorSubsystem)
          );

        joysticks.ElevatorOverride.whenActive(
            new RunCommand(m_elevatorSubsystem::elevatorOverride, m_elevatorSubsystem)
          ).whenInactive(
            new InstantCommand(m_elevatorSubsystem::stop, m_elevatorSubsystem)
          );

        joysticks.ElevatorReset.whenActive(
            new RunCommand(m_elevatorSubsystem::elevatorReset, m_elevatorSubsystem)
          ).whenInactive(
            new InstantCommand(m_elevatorSubsystem::stop, m_elevatorSubsystem)
          );
          joysticks.ElevatorHalfwayPoint.whenActive(
            new RunCommand(() -> m_elevatorSubsystem.setElevatorPostion(0.5), m_elevatorSubsystem)
          );
        
        joysticks.toggleClaw
          .toggleWhenPressed(new StartEndCommand(m_clawSubsystem::clawReverse, m_clawSubsystem::clawForward, m_clawSubsystem));

        joysticks.extendClaw
          .toggleWhenPressed(new InstantCommand(m_clawSubsystem::clawExtend, m_clawSubsystem));
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
