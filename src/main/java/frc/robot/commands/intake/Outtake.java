package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class Outtake extends CommandBase {
  private Intake m_intake;

  public Outtake(Intake intake) {
    addRequirements(intake);
    m_intake = intake;
  }

  @Override
  public void execute() {
    m_intake.outtake();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    m_intake.stopIntake();
  }
}
