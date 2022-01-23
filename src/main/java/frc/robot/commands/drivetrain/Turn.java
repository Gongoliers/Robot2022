package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

/**
 * Turns the robot at a particular speed until stopped/interrupted.
 * Turns right if the speed is positive or left if the speed is negative.
 */
public class Turn extends CommandBase {

    public double speed;

    public Turn(double speed) {

        addRequirement(Robot.drivetrain); // FIXME: Robot.drivetrain is not a subsystem
        this.speed = speed;

    }

    // Called just before this Command runs the first time
    @Override
    public void initalize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        Robot.drivetrain.arcadeDrive(0, speed);
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
    // subsystems is scheduled to tun
    @Override
    public void interrupted() {
        end();
    }

}
