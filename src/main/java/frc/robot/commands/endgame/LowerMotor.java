package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.Constants.EndgameConstants;

public class LowerMotor extends CommandBase{
    
    private EndgameSubsystem m_endgame;
    private double m_speed;

    /**
     * This method uses the default speed provided in EndgameConstants
     * @param endgame
     */
    public LowerMotor(EndgameSubsystem endgame) {
        addRequirements(endgame);
        m_endgame = endgame;
        m_speed = EndgameConstants.kLowerMotorSpeed;
    }

    /**
     * This method uses a speed passed into the method LowerMotor
     * @param endgame
     * @param speed
     */
    public LowerMotor(EndgameSubsystem endgame, double speed) {
        addRequirements(endgame);
        m_endgame = endgame;
        m_speed = speed;
    }

     // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_endgame.setSpeed(m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        System.out.println("FINISHED");
        return (m_endgame.AMotorDone(EndgameConstants.kLowerMotorSpeed) && m_endgame.BMotorDone(EndgameConstants.kLowerMotorSpeed));
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_endgame.stopMotors();
    }
}
