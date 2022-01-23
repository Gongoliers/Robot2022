package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

/**
 * Drives forward at a specified speed until stopped/interrupted.
 */
public class Drive extends CommandBase {

    private double speed;

    public Drive(double speed) {

        addRequirement(Robot.drivetrain); // FIXME: Robot.drivetrain is not a Subsystem
        this.speed = speed;

    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        Robot.drivetrain.arcadeDrive(speed, 0); // Drive straight ahead
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end() {
        Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    public void interrupted() {
        end();
    }

}
