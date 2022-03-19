package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.Constants.EndgameConstants;

public class manualControlDown extends CommandBase{
    
    private EndgameSubsystem m_endgame;
    private double m_speedA;
    private double m_speedB;

    /**
     * This method uses the default speed provided in EndgameConstants
     * @param endgame
     */
    public manualControlDown(EndgameSubsystem endgame) {
        addRequirements(endgame);
        m_endgame = endgame;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_endgame.manualControl(-.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_endgame.stopMotors();
    }
}