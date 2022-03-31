package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.EndgameConstants;
import frc.robot.subsystems.EndgameSubsystem;

public class Ascend extends SequentialCommandGroup {
    public Ascend(EndgameSubsystem endgame) {
        addRequirements(endgame);
        addCommands(new SetDirectionAscend(endgame),
					new DriveEndgame(endgame));
    }
}
