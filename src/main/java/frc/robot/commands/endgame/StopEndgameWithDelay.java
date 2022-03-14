package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.EndgameConstants;
import frc.robot.subsystems.EndgameSubsystem;

public class StopEndgameWithDelay extends SequentialCommandGroup {
    public StopEndgameWithDelay(EndgameSubsystem endgame) {
        addRequirements(endgame);
        addCommands(new EngageSafetyLock(endgame),
                    new WaitCommand(EndgameConstants.kDelayToUnlockPneumatics),
                    new StopEndgameMotor(endgame));
    }

    
}
