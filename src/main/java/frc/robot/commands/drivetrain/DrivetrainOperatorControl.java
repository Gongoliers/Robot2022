package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

/**
 * Allows the driver to control movement using a joystick / controller.
 */
public class DrivetrainOperatorControl extends CommandBase {

    public DrivetrainOperatorControl() {

        addRequirement(Robot.drivetrain); // FIXME: Robot.drivetrain is not a subsytem

    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        double speed = Robot.oi.getDriverSpeed(); // TODO: Why is this negative
        double rotation = Robot.oi.getDriverRotation();

        Robot.drivetrain.arcadeDrive(speed, rotation);
    }

    // Make this return true when this Command no longer needs to run execute
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
