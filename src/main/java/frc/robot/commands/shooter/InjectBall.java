package frc.robot.commands.shooter;

import frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class InjectBall extends CommandBase {

	private Shooter mShooter;
	private double mSpeed;

	public InjectBall(Shooter shooter, double speed) {
		mShooter = shooter;
		mSpeed = speed;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		mShooter.setInjectorMotor(mSpeed);
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
