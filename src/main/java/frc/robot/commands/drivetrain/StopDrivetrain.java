package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;

/** Stops the motors of the drivetrain. */
public class StopDrivetrain extends InstantCommand {

  private Drivetrain mDrivetrain;

  public StopDrivetrain(Drivetrain drivetrain) {
    addRequirements(drivetrain);
    mDrivetrain = drivetrain;
  }

  // Called once this InstantCommand runs
  @Override
  public void initialize() {
    mDrivetrain.stop();
  }
}
