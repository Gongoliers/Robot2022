package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

/*
 * Drives forward for the specified distance.
 */
public class SimpleDriveDistance extends CommandBase {

  private final Drivetrain mDrivetrain;
  private final double mDistance;
  private final double mThreshold;
  private final double mSpeed;
  private double mPreviousDistance;

  public SimpleDriveDistance(
      Drivetrain drivetrain, double distance, double threshold, double speed) {
    addRequirements(drivetrain);
    mDrivetrain = drivetrain;

    mDistance = distance;
    mThreshold = threshold;
    mSpeed = speed;
  }

  @Override
  public void initialize() {
    mPreviousDistance = mDrivetrain.getDistance();
  }

  @Override
  public void execute() {
    if (isFinished()) {
      mDrivetrain.stop();
    } else if (getError() > 0) {
      mDrivetrain.arcadeDrive(mSpeed, 0.0);
    } else {
      mDrivetrain.arcadeDrive(-mSpeed, 0.0);
    }
  }

  @Override
  public boolean isFinished() {
    return Math.abs(getError()) <= mThreshold;
  }

  @Override
  public void end(boolean interrupted) {
    mDrivetrain.stop();
  }

  private double getError() {
    return mDrivetrain.getDistance() - mPreviousDistance - mDistance;
  }
}
