package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.thegongoliers.input.odometry.EncoderSensor;

public class EndgameArm {

	private static enum EndgameArmState {
		ASCENDING,
		DESCENDING,
		STOPPED,
		CAPPED,
		FLOORED,
	}

	private final WPI_TalonSRX m_motor;
	private final EncoderSensor m_encoder;
	private final InvertableLimitSwitch m_limitSwitch;
	private EndgameArmState m_state = EndgameArmState.STOPPED;
	private boolean m_isSafeToAscend = false;
	private double m_cappedDistance, m_ascentSpeed, m_descentSpeed;
	private boolean m_interface = false;

	/**
	 * This is the basic endgame arm used in the 2022 game
	 * @param motor
	 * @param encoder
	 * @param limitSwitch
	 */
	public EndgameArm(WPI_TalonSRX motor, EncoderSensor encoder, InvertableLimitSwitch limitSwitch) {
		m_motor = motor;
		m_encoder = encoder;
		m_limitSwitch = limitSwitch;
	}

	/**
	 * Gets the speed of the motor
	 * @return
	 */
	public double get() {
		return m_motor.get();
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
	 * Helper for EndgameController.java
	 */
	public void toggleInterface() {
		m_interface = !m_interface;
	}

	/**
	 * Gets the motor from EndgameArm
	 * @return
	 */
	public WPI_TalonSRX getMotor() {
		return m_motor;
	}

	/**
	 * Gets the encoder from EndgameArm
	 * @return
	 */
	public EncoderSensor getEncoder() {
		return m_encoder;
	}

	/**
	 * Gets the EndgameArm's limit switch
	 * @return
	 */
	public InvertableLimitSwitch getLimitSwitch() {
		return m_limitSwitch;
	}

	/**
	 * Important function that will need to be called
	 * to configure this object.
	 * @param distance
	 */
	public void setCappedDistance(double distance) {
		m_cappedDistance = distance;
	}

	/**
	 * Helper function for EndgameController
	 * @return cappedDistance for EndgameArm
	 */
	public double getCappedDistance() {
		return m_cappedDistance;
	}

	/**
	 * Sets the ascentSpeed for EndgameArm when
	 * being controlled individually
	 * @param speed
	 */
	public void setAscentSpeed(double speed) {
		m_ascentSpeed = speed;
	}

	/**
	 * Sets the ddescentSpeed for EndgameArm when
	 * being controlled individually
	 * @param speed
	 */
	public void setDescentSpeed(double speed) {
		m_descentSpeed = speed;
	}

	/**
	 * Engages the Safety
	 */
	public void engageSafety() {
		m_isSafeToAscend = false;
	}

	/**
	 * Disengages the safety
	 */
	public void disengageSafety() {
		m_isSafeToAscend = true;
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
		m_motor.stopMotor(); // safety
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
		if (m_interface) {return;};
		switch (m_state) {
			case ASCENDING:
				if (m_isSafeToAscend) {
					m_motor.set(m_ascentSpeed);
				}
				break;
			case DESCENDING:
				m_motor.set(m_descentSpeed);
				break;
			case STOPPED:
			case CAPPED:
			case FLOORED:
				m_motor.stopMotor();
				break;
		}
	}

}
