package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.subsystems.endgame.EndgameAction;

public class AscendEndgame extends SequentialCommandGroup {

    public AscendEndgame(EndgameSubsystem subsystem) {
        addCommands(
                new EndgameCommand(subsystem, EndgameAction.Unlock).withTimeout(0.4),
                new EndgameCommand(subsystem, EndgameAction.Ascend)
        );
    }

}
