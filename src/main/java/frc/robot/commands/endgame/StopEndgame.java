package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.subsystems.endgame.EndgameAction;

public class StopEndgame extends SequentialCommandGroup {

    public StopEndgame(EndgameSubsystem subsystem) {
        addCommands(
                new EndgameCommand(subsystem, EndgameAction.Stop).withTimeout(0.2)
        );
    }

}
