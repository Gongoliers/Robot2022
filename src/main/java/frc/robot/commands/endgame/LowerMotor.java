package frc.robot.commands.endgame;

import com.thegongoliers.input.odometry.BaseEncoderSensor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.Constants.EndgameConstants;

public class LowerMotor extends CommandBase{
    
    private EndgameSubsystem m_endgame;
    public LowerMotor(EndgameSubsystem endgame) {
        addRequirements(endgame);
    }

     // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_endgame.getMotors().set(EndgameConstants.kLowerMotorSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        BaseEncoderSensor m_encoder = m_endgame.getEncoders();
        /** TODO: Does this BaseEncoderSensor update every time this function is called?
         *      If not, change .getEncoders to .getEncoderDistance, have it return a variable
         *      and the variable will be updated in the Subsystem's perioidic
         */
        if (m_encoder.getDistance() <= -EndgameConstants.kCappedDistance) {
            return true;
        } else {
            return false;
        }
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }
}
