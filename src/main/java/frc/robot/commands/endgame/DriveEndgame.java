package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EndgameSubsystem;

public class DriveEndgame extends CommandBase{
    
    private EndgameSubsystem m_endgame;

    public DriveEndgame(EndgameSubsystem endgame) {
        addRequirements(endgame);
        m_endgame = endgame;
    }

     // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_endgame.drive();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return m_endgame.doneDescending() || m_endgame.doneAscending();
    }

}
