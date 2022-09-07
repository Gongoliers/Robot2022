package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class InjectBall extends CommandBase {

  private Shooter mShooter;

  public InjectBall(Shooter shooter) {
    mShooter = shooter;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    mShooter.injectBall();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    mShooter.stopInjectorMotor();
  }
}
