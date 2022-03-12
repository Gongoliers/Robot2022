package frc.robot.subsystems;

import frc.robot.Constants.ShooterConstants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

	private final WPI_TalonSRX m_feederMotor;
	private final WPI_TalonSRX m_outtakeMotor;

	private double m_feederTargetSpeed;
	private double m_feederSpeed;

	private double m_outtakeTargetSpeed;
	private double m_outtakeSpeed;

	private final int m_spinCountThreshold;
	private int m_spinCount;

	public ShooterSubsystem() {
		m_feederMotor = new WPI_TalonSRX(ShooterConstants.kFeederMotorCANId);
		m_outtakeMotor = new WPI_TalonSRX(ShooterConstants.kOuttakeMotorCANId);
		m_feederTargetSpeed = getFeederTargetSpeed();
		m_outtakeTargetSpeed = getOuttakeTargetSpeed();

		// Converts seconds to spin calls (50 spin calls = 1 second)
		// Force-casts to an integer since fractional calls to the spin function do not occur
		// TODO: Math.ceil instead?
		m_spinCountThreshold = (int) ShooterConstants.kRampUpSeconds * (1000 / 20);
		m_spinCount = 0;
	}

	@Override
	public void periodic() {
	}

	public void spin() {
		m_updateSpeeds();
		m_spinCount++;
		m_feederMotor.set(m_feederSpeed);
		m_outtakeMotor.set(m_outtakeSpeed);
	}

	public void stop() {
		stopFeederMotor();
		stopOuttakeMotor();
	}

	public void stopFeederMotor() {
		m_feederMotor.set(0);
	}

	public void stopOuttakeMotor() {
		m_outtakeMotor.set(0);
	}

	public double getFeederCurrentSpeed() {
		return m_feederSpeed;
	}

	public double getOuttakeCurrentSpeed() {
		return m_outtakeSpeed;
	}

	public double getFeederTargetSpeed() {
		// TODO: Grab this value from SmartDashboard instead
		return ShooterConstants.kFeederMotorTargetSpeed;
	}

	public double getOuttakeTargetSpeed() {
		// TODO: Grab this value from SmartDashboard instead
		return ShooterConstants.kOuttakeMotorTargetSpeed;
	}

	public void startRampUp() {
		m_spinCount = 0;
	}

	private void m_updateSpeeds() {
		if (m_spinCount < m_spinCountThreshold) {
			// Linearly scales the speed based on the time until spin count
			double rampFactor = m_spinCount / m_spinCountThreshold;
			m_feederSpeed = m_feederTargetSpeed * rampFactor;
			m_outtakeSpeed = m_outtakeTargetSpeed * rampFactor;
		} else {
			m_feederSpeed = m_feederTargetSpeed;
			m_outtakeSpeed = m_outtakeTargetSpeed;
		}
	}

}
