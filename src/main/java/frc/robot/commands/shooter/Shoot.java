package frc.robot.commands.shooter;

import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shoot extends CommandBase {

	private ShooterSubsystem m_shooter;
	private long m_startTime;

	public Shoot(ShooterSubsystem shooter) {
		addRequirements(shooter);
		m_shooter = shooter;
	}

	@Override
	public void initialize() {
		m_startTime = System.currentTimeMillis();
	}

	@Override
	public void execute() {
		m_shooter.spin();
		if (System.currentTimeMillis() - m_startTime >= Constants.ShooterConstants.kRampUpSeconds){
			m_shooter.feed();
		}
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
