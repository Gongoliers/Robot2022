package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.EndgameConstants;
import frc.robot.subsystems.EndgameSubsystem;

public class ResetEndgame extends SequentialCommandGroup {
    public ResetEndgame(EndgameSubsystem endgame) {
        double speed = EndgameConstants.kLowerMotorSpeed; 
        addRequirements(endgame);
        addCommands(new DisengageSafetyLock(endgame),
                    new WaitCommand(EndgameConstants.kDelayToUnlockPneumatics),
                    new ResetEndgameMotor(endgame, speed)                    
                    );
    }
}
