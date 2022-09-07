package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class RetractIntake extends CommandBase {
  private Intake m_intake;

  public RetractIntake(Intake intake) {
    addRequirements(intake);
    m_intake = intake;
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    m_intake.retract();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
