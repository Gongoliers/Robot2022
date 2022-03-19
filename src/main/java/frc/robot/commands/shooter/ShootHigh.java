package frc.robot.commands.shooter;

import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootHigh extends CommandBase {

	private ShooterSubsystem m_shooter;
	private double m_startTime;
	private Clock m_clock;

	public ShootHigh(ShooterSubsystem shooter) {
		addRequirements(shooter);
		m_shooter = shooter;
		m_clock = new RobotClock();
	}

	@Override
	public void initialize() {
		m_startTime = m_clock.getTime();
	}

	@Override
	public void execute() {
		m_shooter.spinForHigh();
		m_shooter.feedTime();
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
