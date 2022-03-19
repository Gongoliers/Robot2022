package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.Constants.EndgameConstants;

public class LowerMotor extends CommandBase{
    
    private EndgameSubsystem m_endgame;
    private double m_speedA;
    private double m_speedB;

    /**
     * This method uses the default speed provided in EndgameConstants
     * @param endgame
     */
    public LowerMotor(EndgameSubsystem endgame) {
        addRequirements(endgame);
        m_endgame = endgame;
        m_speedA = EndgameConstants.kLowerMotorSpeedA;
        m_speedB = EndgameConstants.kLowerMotorSpeedB;
    }

    /**
     * This method uses a speed passed into the method LowerMotor
     * @param endgame
     * @param speed
     */
    public LowerMotor(EndgameSubsystem endgame, double speedA, double speedB) {
        addRequirements(endgame);
        m_endgame = endgame;
        m_speedA = speedA;
        m_speedB = speedB;
    }

     // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        System.out.println("Execute");
        m_endgame.setSpeed(m_speedA, m_speedB);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        System.out.println("FINISHED");
        return (m_endgame.AMotorDone() && m_endgame.BMotorDone());
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_endgame.stopMotors();
    }
}
