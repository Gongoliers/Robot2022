package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.EndgameSubsystem;


/** I added a z prefix to both of these files, as this code will most
 * likely not be used, as long as raising / lowering is performed manually
 * endgame should be controlled by .whenPressed, .whileHeld, and .whenReleased
 * talk to drivers to configure this
 */
public class zRetractEndgame extends SequentialCommandGroup {

    public zRetractEndgame(EndgameSubsystem endgame) {
        addCommands(
            new DisengageSafetyLock(endgame),
            new LowerMotor(endgame),
            new EngageSafetyLock(endgame)
         );
    }
}
