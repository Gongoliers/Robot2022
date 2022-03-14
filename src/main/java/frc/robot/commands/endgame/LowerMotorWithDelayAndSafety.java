package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.EndgameConstants;
import frc.robot.subsystems.EndgameSubsystem;

public class LowerMotorWithDelayAndSafety extends SequentialCommandGroup {
    public LowerMotorWithDelayAndSafety(EndgameSubsystem endgame) {
        addRequirements(endgame);
        addCommands(new DisengageSafetyLock(endgame),
                    new WaitCommand(EndgameConstants.kDelayToUnlockPneumatics),
                    new LowerMotor(endgame));
    }

    
}
