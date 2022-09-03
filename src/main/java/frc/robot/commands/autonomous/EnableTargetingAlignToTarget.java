package frc.robot.commands.autonomous;

import com.thegongoliers.commands.AlignTargetCommand;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

public class EnableTargetingAlignToTarget extends SequentialCommandGroup {
    public EnableTargetingAlignToTarget(Drivetrain drivetrain, Vision vision) {
        addCommands(
            new EnableTargetingMode(vision),
            new AlignTargetCommand(drivetrain, drivetrain.getModularDrivetrain(), 0, 0)
        );
    }
}
