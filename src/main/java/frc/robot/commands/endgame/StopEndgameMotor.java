package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.EndgameSubsystem;

public class StopEndgameMotor extends InstantCommand {
    
    private EndgameSubsystem m_endgame;

    public StopEndgameMotor(EndgameSubsystem endgame) {
        addRequirements(endgame);
        m_endgame = endgame;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_endgame.stop();
    }
    
}
