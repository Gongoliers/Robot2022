package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.Constants.EndgameConstants;

public class RaiseMotor extends CommandBase {
    
    private EndgameSubsystem m_endgame;
    public RaiseMotor(EndgameSubsystem endgame) {
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
        m_endgame.setSpeed(EndgameConstants.kRaiseMotorSpeedA, EndgameConstants.kRaiseMotorSpeedB);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return (m_endgame.AMotorDone(EndgameConstants.kRaiseMotorSpeedA) && m_endgame.BMotorDone(EndgameConstants.kRaiseMotorSpeedB));
    }
    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_endgame.stopMotors();
    }
}
