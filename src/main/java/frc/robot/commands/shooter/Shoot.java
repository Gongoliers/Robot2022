package frc.robot.commands.shooter;

import frc.robot.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shoot extends CommandBase {

	private ShooterSubsystem m_shooter;

	public Shoot(ShooterSubsystem shooter) {
		addRequirements(shooter);
		m_shooter = shooter;
	}

	@Override
	public void initialize() {
		m_shooter.startRampUp();
	}

	@Override
	public void execute() {
		m_shooter.spin();
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		m_shooter.stop();
	}

}
