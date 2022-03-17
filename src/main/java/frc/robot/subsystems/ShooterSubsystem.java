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

	private double m_interfaceSpeed;


	public ShooterSubsystem() {
		m_feederMotor = new WPI_TalonSRX(ShooterConstants.kFeederMotorCANId);
		m_outtakeMotor = new WPI_TalonSRX(ShooterConstants.kOuttakeMotorCANId);
		m_interfaceMotor = new WPI_TalonSRX(ShooterConstants.kInterfaceMotorCANId);

		m_interfaceMotor.setInverted(true);
		m_outtakeMotor.setInverted(true);

		m_feederSpeedController = new GSpeedController(m_feederMotor);
		m_outtakeSpeedController = new GSpeedController(m_outtakeMotor);

		/**
		 * If we were counting by spins, the formula that we would use is 
		 * m_spinCountThreshold = (int) ShooterConstants.kRampUpSeconds * (1000 / 20)
		 */
		m_feederSpeedController.setSecondsToFullSpeed(ShooterConstants.kRampUpSeconds);
		m_outtakeSpeedController.setSecondsToFullSpeed(ShooterConstants.kRampUpSeconds);

		
	}
	
	@Override
	public void periodic() {
	}
	
	public void spinForHigh() {
		// Scale the shooter motors to limit the max speed
		m_feederSpeedController.setScale(ShooterConstants.kFeederMotorTargetSpeedHigh);
		m_outtakeSpeedController.setScale(ShooterConstants.kOuttakeMotorTargetSpeedHigh);
		m_interfaceSpeed = ShooterConstants.kInterfaceMotorSpeedHigh;
		
		m_feederSpeedController.set(1.0);
		m_outtakeSpeedController.set(1.0);
	}
	
	public void spinForLow() {
		// Scale the shooter motors to limit the max speed
		m_feederSpeedController.setScale(ShooterConstants.kFeederMotorTargetSpeedLow);
		m_outtakeSpeedController.setScale(ShooterConstants.kOuttakeMotorTargetSpeedLow);
		m_interfaceSpeed = ShooterConstants.kInterfaceMotorSpeedLow;
		
		m_feederSpeedController.set(1.0);
		m_outtakeSpeedController.set(1.0);
	}

	public void feed(){
		m_interfaceMotor.set(m_interfaceSpeed);
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
}
