package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.EndgameSubsystem;

public class RetractEndgame extends SequentialCommandGroup {

    public RetractEndgame(EndgameSubsystem endgame) {
        addCommands(
            new DisengageSafetyLock(endgame),
            new LowerMotor(endgame),
            new EngageSafetyLock(endgame)
         );
    }
}
