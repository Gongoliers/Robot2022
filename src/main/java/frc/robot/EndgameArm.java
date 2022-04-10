package frc.robot;

import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.output.actuators.GSpeedController;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants.EndgameConstants;

public class EndgameArm {

	private static enum EndgameArmState {
		ASCENDING,
		DESCENDING,
		HOMING,
		STOPPED,
		CAPPED,
		FLOORED,
	}

	private final GSpeedController m_motor;
	private final EncoderSensor m_encoder;
	private final InvertableLimitSwitch m_limitSwitch;
	private final Solenoid m_armLockController; 
	private EndgameArmState m_state = EndgameArmState.STOPPED;
	private double m_cappedDistance;

	/**
	 * This is the basic endgame arm used in the 2022 game
	 * @param motor
	 * @param encoder
	 * @param limitSwitch
	 */
	public EndgameArm(GSpeedController motor, EncoderSensor encoder, InvertableLimitSwitch limitSwitch, Solenoid locker) {
		motor.useVoltageControl(12.0);
		m_motor = motor;
		m_encoder = encoder;
		m_limitSwitch = limitSwitch;
		m_armLockController = locker;
		m_state = EndgameArmState.DESCENDING;
	}

	/**
	 * Sets the speed of the motor. This should not 
	 * need to be used while coding. 
	 * @param speed
	 */
	public void set(double speed) {
		m_motor.set(speed);
	}

	/**
	 * Set's the Ascent direction
	 */
	public void setDirectionAscend() {
		if (m_state != EndgameArmState.CAPPED) {
			m_state = EndgameArmState.ASCENDING;
		}
	}

	/**
	 * Returns whether or not the EndgameArm is done ascending
	 * @return bool
	 */
	public boolean doneAscending() {
		return m_encoder.getDistance() >= m_cappedDistance;
	}

	/**
	 * Set's the Descent Direction
	 */
	public void setDirectionDescend() {
		if (m_state != EndgameArmState.FLOORED) {
			m_state = EndgameArmState.DESCENDING;
		}
	}

	public void home() {
		if (m_state != EndgameArmState.FLOORED) {
			m_state = EndgameArmState.HOMING;
		}
	}

	/**
	 * Returns whether or not the EndgameArm is done descending
	 * @return bool
	 */
	public boolean doneDescending() {
		return m_limitSwitch.isTriggered();
	}

	/**
	 * Stops the EndgameArm Motor
	 */
	public void stop() {
		m_state = EndgameArmState.STOPPED;
		m_motor.set(0); // safety
	}

	/**
	 * State Update for EndgameArm.java
	 */
	public void update() {
		switch (m_state) {
			case ASCENDING:
				if (doneAscending()) {
					m_state = EndgameArmState.CAPPED;
				}
				break;
			case HOMING:
			case DESCENDING:
				if (doneDescending()) {
					m_state = EndgameArmState.FLOORED;
					m_encoder.reset();
				}
				break;
			case STOPPED:
			case CAPPED:
			case FLOORED:
				break;
		}
	}

	/**
	 * Helper Function to drive arm
	 */
	public void driveArm() {
		switch (m_state) {
			case ASCENDING:
				if (m_armLockController.get()) {
					m_motor.setDistance(EndgameConstants.kTopDistance);
				}
				break;
			case DESCENDING:
				m_motor.set(-0.5);
				break;
			case HOMING:
				m_motor.set(-0.5);
				break;
			case STOPPED:
			case CAPPED:
			case FLOORED:
				m_motor.stopMotor();
				break;
		}
	}

}
