package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.AutoCommand;
import frc.robot.AutoPaths;
import frc.robot.commands.AutoDriveCommand;
import frc.robot.subsystems.*;

public class Straight extends SequentialCommandGroup {
    private final SwerveDriveSubsystem m_swerveDriveSubsystem;

    public Straight(SwerveDriveSubsystem swerveDriveSubsystem, AutoPaths autoPaths) {
        addRequirements(swerveDriveSubsystem);
        m_swerveDriveSubsystem = swerveDriveSubsystem;

        addCommands(
                new AutoDriveCommand(swerveDriveSubsystem, autoPaths.getFastRotation(), true )
        );

    }

}