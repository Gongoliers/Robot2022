package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.EndgameSubsystem;

public class RaiseEndgame extends SequentialCommandGroup {

    public RaiseEndgame(EndgameSubsystem endgame) {
        addCommands(
            new DisengageSafetyLock(endgame),
            new RaiseMotor(endgame),
            new EngageSafetyLock(endgame)
         );
    }
}
