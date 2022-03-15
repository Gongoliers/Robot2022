package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.EndgameTimer;

public class OverrideMatchTimer extends InstantCommand{

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        EndgameTimer.resetOverride();
    }

}
