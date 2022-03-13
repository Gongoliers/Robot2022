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
        m_endgame.getMotors().set(EndgameConstants.kRaiseMotorSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        // AverageEncoderSensor m_encoder = m_endgame.getEncoders();
        // /** TODO: Does this BaseEncoderSensor update every time this function is caled?
        //  *      If not, change .getEncoders to .getEncoderDistance, have it return a variable
        //  *      and the variable will be updated in the Subsystem's perioidic
        //  */
        // if (m_encoder.getDistance() >= EndgameConstants.kCappedDistance) {
        //     return true;
        // } else {
        //     return false;
        // }
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_endgame.getMotors().stopMotor();
    }
}
