package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

/** Allows the driver to control movement using a joystick / controller. */
public class DrivetrainOperatorControl extends CommandBase {

  private RobotContainer mRobot;
  private Drivetrain mDrivetrain;

  public DrivetrainOperatorControl(RobotContainer robot, Drivetrain drivetrain) {
    addRequirements(drivetrain);
    mDrivetrain = drivetrain;

    mRobot = robot;
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {}

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    double driveSpeed = mRobot.getDriverSpeed();
    double driveRotation = mRobot.getDriverRotation();

    mDrivetrain.arcadeDrive(driveSpeed, driveRotation);
  }

  // Make this return true when this Command no longer needs to run execute
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
