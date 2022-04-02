package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.EndgameSubsystem;

public class EngageSafetyLock extends InstantCommand {

    private EndgameSubsystem m_endgame;

    public EngageSafetyLock(EndgameSubsystem endgame) {
        addRequirements(endgame);
        m_endgame = endgame;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_endgame.EngageSafetyLock();
    }
}
