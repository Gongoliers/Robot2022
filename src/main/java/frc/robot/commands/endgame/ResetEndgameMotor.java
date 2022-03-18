package frc.robot.commands.endgame;

import com.thegongoliers.input.odometry.EncoderSensor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.Constants.EndgameConstants;

public class ResetEndgameMotor extends CommandBase{
    
    private EndgameSubsystem m_endgame;
    private double m_speed;
    private double initial_encoder_A;
    private double initial_encoder_B;

    /**
     * This method uses the default speed provided in EndgameConstants
     * @param endgame
     */
    public ResetEndgameMotor(EndgameSubsystem endgame) {
        addRequirements(endgame);
        m_endgame = endgame;
        m_speed = EndgameConstants.kLowerMotorSpeed;
    }

    /**
     * This method uses a speed passed into the method LowerMotor
     * @param endgame
     * @param speed
     */
    public ResetEndgameMotor(EndgameSubsystem endgame, double speed) {
        addRequirements(endgame);
        m_endgame = endgame;
        m_speed = speed;
    }

     // Called just before this Command runs the first time
    @Override
    public void initialize() {
        initial_encoder_A = m_endgame.getAEncoder().getDistance();
        initial_encoder_B = m_endgame.getBEncoder().getDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_endgame.setSpeed(m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return ((initial_encoder_A - EndgameConstants.kCappedDistance > m_endgame.getAEncoder().getDistance()) && (initial_encoder_B - EndgameConstants.kCappedDistance > m_endgame.getBEncoder().getDistance()));
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_endgame.stopMotors();
    }
}
