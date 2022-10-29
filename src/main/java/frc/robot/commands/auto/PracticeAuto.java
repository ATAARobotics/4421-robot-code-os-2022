package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.SwerveDriveSubsystem;
import frc.robot.AutoPaths;
import frc.robot.commands.AutoDriveCommand;

public class PracticeAuto extends SequentialCommandGroup {
    private final SwerveDriveSubsystem m_swerveDriveSubsystem;

    public PracticeAuto(SwerveDriveSubsystem swerveDriveSubsystem) {
        addRequirements(swerveDriveSubsystem);
        m_swerveDriveSubsystem = swerveDriveSubsystem;
        addCommands(
                new AutoDriveCommand(m_swerveDriveSubsystem, AutoPaths.getTest1()),
                new AutoDriveCommand(m_swerveDriveSubsystem, AutoPaths.getTest2()));
    }
}
