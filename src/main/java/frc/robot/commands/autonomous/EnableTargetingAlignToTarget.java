package frc.robot.commands.autonomous;

import com.thegongoliers.commands.AlignTargetCommand;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class EnableTargetingAlignToTarget extends SequentialCommandGroup {
    public EnableTargetingAlignToTarget(DrivetrainSubsystem drivetrain, VisionSubsystem vision) {
        addCommands(
            new EnableTargetingMode(vision),
            new AlignTargetCommand(drivetrain, drivetrain.getModularDrivetrain(), 0, 0)
        );
    }
}
