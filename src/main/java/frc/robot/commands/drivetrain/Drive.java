package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

/**
 * Drives forward at a specified speed until stopped/interrupted.
 */
public class Drive extends CommandBase {

    private Drivetrain mDrivetrain;
    private double mSpeed;

    public Drive(Drivetrain drivetrain, double speed) {
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
        mDrivetrain.arcadeDrive(mSpeed, 0);
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
