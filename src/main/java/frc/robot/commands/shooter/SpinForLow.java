package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SpinForLow extends CommandBase {

  private Shooter mShooter;

  public SpinForLow(Shooter shooter) {
    mShooter = shooter;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    mShooter.spinForLow();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    mShooter.stopFeederMotor();
    mShooter.stopOuttakeMotor();
  }
}
