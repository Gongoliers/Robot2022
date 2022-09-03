package frc.robot.commands.shooter;

import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SpinForLow extends CommandBase {

	private Shooter mShooter;

	public SpinForLow(Shooter shooter) {
		mShooter = shooter;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		mShooter.setFeederMotor(ShooterConstants.kFeederMotorTargetSpeedLow);
		mShooter.setOuttakeMotor(ShooterConstants.kOuttakeMotorTargetSpeedLow);
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
