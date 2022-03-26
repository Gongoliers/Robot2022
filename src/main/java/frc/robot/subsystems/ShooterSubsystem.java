package frc.robot.subsystems;

import frc.robot.Constants.ShooterConstants;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import com.thegongoliers.output.actuators.GSpeedController;
import com.thegongoliers.output.interfaces.Shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

	private final WPI_TalonSRX m_feederMotor;
	private final WPI_TalonSRX m_outtakeMotor;

	private Clock m_clock;
	private double feederTime;
	private double feederStartTime;
	private boolean feederRunning;

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
	private double m_interfaceWaitTime;


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

		m_clock = new RobotClock();
		feederTime = -1.0;

		m_interfaceWaitTime = ShooterConstants.kInterfaceMotorSpeedHigh;
	}

	@Override
	public void periodic() {
	}

	public void spinForHigh() {
		// Scale the shooter motors to limit the max speed
		m_feederSpeedController.setScale(ShooterConstants.kFeederMotorTargetSpeedHigh);
		m_outtakeSpeedController.setScale(ShooterConstants.kOuttakeMotorTargetSpeedHigh);
		m_interfaceWaitTime = ShooterConstants.kInterfaceMotorSpeedHigh;
		m_interfaceSpeed = ShooterConstants.kInterfaceMotorSpeedHigh;

		m_feederSpeedController.set(1.0);
		m_outtakeSpeedController.set(1.0);
	}

	public void spinForLow() {
		// Scale the shooter motors to limit the max speed
		m_feederSpeedController.setScale(ShooterConstants.kFeederMotorTargetSpeedLow);
		m_outtakeSpeedController.setScale(ShooterConstants.kOuttakeMotorTargetSpeedLow);
		m_interfaceWaitTime = ShooterConstants.kInterfaceMotorSpeedLow;
		m_interfaceSpeed = ShooterConstants.kInterfaceMotorSpeedLow;

		m_feederSpeedController.set(1.0);
		m_outtakeSpeedController.set(1.0);
	}

	public void feedManual(){
		m_interfaceMotor.set(m_interfaceSpeed);
	}

	public boolean startedUp() {
		var endTime = feederStartTime + ShooterConstants.kRampUpSeconds;
		return m_clock.getTime() > endTime;
		// if (endT )


		// if (m_feederSpeedController.get() >= 0.99 && m_outtakeSpeedController.get() >= 0.99) {
		// 	System.out.println("TRUE" + "m_feeder" + m_feederSpeedController.get() + "\nm_outtake" + m_outtakeSpeedController.get());
		// 	return true;
		// } else {return false;}
	}
	/**
	 * We will be setting feederTime to (-1.0) whenever the shooter is in an off state
	 */
	public void feedTime(){

		// if (feederStartTime == -1.0){
		// 	feederStartTime = m_clock.getTime();
		// }

		// if (!startedUp()){
		// 	stopInterfaceMotor();
		// 	return;
		// }

		if (feederTime == -1.0) {
			feederTime = m_clock.getTime();
			feederRunning = false;
		} else if (feederTDoneShooting()){
			feederTime = m_clock.getTime();
			feederRunning = false;
		} else if (feederTDoneWaiting()) {
			feederTime = m_clock.getTime();
			feederRunning = true;
		}

		m_interfaceMotor.set(feederRunning ? ShooterConstants.kInterfaceMotorSpeedLow : 0.0);
	}

	public boolean feederTDoneShooting(){
		double end_time = feederTime + m_interfaceWaitTime;
		return feederRunning && (m_clock.getTime() > end_time);
	}

	public boolean feederTDoneWaiting(){
		double end_time = feederTime + m_interfaceWaitTime;
		return !feederRunning && (m_clock.getTime() > end_time);
	}

	public void resetFeeder(){
		feederStartTime = -1.0;
		feederTime = -1.0;
		feederRunning = false;
	}

	public void stop() {
		stopFeederMotor();
		stopOuttakeMotor();
		stopInterfaceMotor();
		resetFeeder();
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
