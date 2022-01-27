package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

/*
 * Turns the robot at a particular speed until stopped/interrupted.
 * Turns right if the speed is positive or left if the speed is negative.
 */
public class Turn extends CommandBase {

    private DrivetrainSubsystem m_drivetrain;
    private double m_speed;

    public Turn(DrivetrainSubsystem drivetrain, double speed) {

        addRequirements(drivetrain);
        m_drivetrain = drivetrain;
        m_speed = speed;

    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_drivetrain.arcadeDrive(0, m_speed); // FIXME: Add arcadeDrive to subsystem
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_drivetrain.stop();
    }

}
