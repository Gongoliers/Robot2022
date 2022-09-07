package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class StopIntake extends CommandBase {
  private Intake m_intake;

  public StopIntake(Intake intake) {
    addRequirements(intake);
    m_intake = intake;
  }

  @Override
  public void initialize() {
    m_intake.stopIntake();
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
