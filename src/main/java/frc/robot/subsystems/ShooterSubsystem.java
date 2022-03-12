package frc.robot.subsystems;

import frc.robot.Constants.ShooterConstants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.thegongoliers.output.actuators.GSpeedController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

	private final WPI_TalonSRX m_feederMotor;
	private final WPI_TalonSRX m_outtakeMotor;

	/**
	 * This motor acts as a feeder from the intake subsystem to
	 * the shooter subsystem. This is being included in the 
	 * shooter subsystem because it will start once the shooterReady()
	 * function returns true, and end in conjuction with the shooter ending
	 */
	private final WPI_TalonSRX m_interfaceMotor;

	private final GSpeedController m_feederSpeedController;
	private final GSpeedController m_outtakeSpeedController;

	private double m_feederTSpeed;
	private double m_outtakeTSpeed;
	private double m_interfaceSpeed;


	public ShooterSubsystem() {
		m_feederMotor = new WPI_TalonSRX(ShooterConstants.kFeederMotorCANId);
		m_outtakeMotor = new WPI_TalonSRX(ShooterConstants.kOuttakeMotorCANId);
		m_interfaceMotor = new WPI_TalonSRX(ShooterConstants.kInterfaceMotorCANId);

		m_feederSpeedController = new GSpeedController(m_feederMotor);
		m_outtakeSpeedController = new GSpeedController(m_outtakeMotor);

		m_feederSpeedController.setSecondsToFullSpeed(ShooterConstants.kRampUpSeconds);
		m_outtakeSpeedController.setSecondsToFullSpeed(ShooterConstants.kRampUpSeconds);

		m_feederTSpeed = ShooterConstants.kFeederMotorTargetSpeed;
		m_outtakeTSpeed = ShooterConstants.kOuttakeMotorTargetSpeed;
		m_interfaceSpeed = ShooterConstants.kInterfaceMotorSpeed;
	}

	@Override
	public void periodic() {
	}

	public void spin() {
		m_feederSpeedController.set(m_feederTSpeed);
		m_outtakeSpeedController.set(m_outtakeTSpeed);
		if (shooterReady()) {
			m_interfaceMotor.set(m_interfaceSpeed);
		}
	}	

	public void stop() {
		stopFeederMotor();
		stopOuttakeMotor();
		stopInterfaceMotor();
	}

	public void stopFeederMotor() {
		m_feederSpeedController.stopMotor();
	}

	public void stopOuttakeMotor() {
		m_outtakeSpeedController.stopMotor();
	}

	public void stopInterfaceMotor() {
		m_interfaceMotor.stopMotor();
	}

	public boolean outtakeReady() {
		return (m_outtakeSpeedController.get() >= m_outtakeTSpeed);
	}

	public boolean feederReady() {
		return (m_feederSpeedController.get() >= m_feederTSpeed);
	}

	public boolean shooterReady() {
		return (outtakeReady() && feederReady());
	}

}
