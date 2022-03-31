package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.EndgameConstants;
import frc.robot.subsystems.EndgameSubsystem;

public class AscendWithDelay extends SequentialCommandGroup {
    public AscendWithDelay(EndgameSubsystem endgame) {
        addRequirements(endgame);
        addCommands(new DisengageSafetyLock(endgame),
                    new WaitCommand(EndgameConstants.kDelayToUnlockPneumatics),
                    new Ascend(endgame));
    }
}
