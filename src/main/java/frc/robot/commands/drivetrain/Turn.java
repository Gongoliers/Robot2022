package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

/*
 * Turns the robot at a particular speed until stopped/interrupted.
 * Turns right if the speed is positive or left if the speed is negative.
 */
public class Turn extends CommandBase {

    private Drivetrain mDrivetrain;
    private double mSpeed;

    public Turn(Drivetrain drivetrain, double speed) {
        addRequirements(drivetrain);
        mDrivetrain = drivetrain;

        mSpeed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        mDrivetrain.arcadeDrive(0, mSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        mDrivetrain.stop();
    }

}
