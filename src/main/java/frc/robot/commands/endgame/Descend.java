package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EndgameSubsystem;

public class Descend extends CommandBase {
    
    private EndgameSubsystem m_endgame;

    public Descend(EndgameSubsystem endgame) {
        addRequirements(endgame);
        m_endgame = endgame;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_endgame.descend();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_endgame.done();
    }

}
