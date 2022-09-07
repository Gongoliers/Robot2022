package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.subsystems.endgame.EndgameAction;

public class DescendEndgame extends SequentialCommandGroup {

  public DescendEndgame(EndgameSubsystem subsystem) {
    addCommands(new EndgameCommand(subsystem, EndgameAction.Descend));
  }
}
