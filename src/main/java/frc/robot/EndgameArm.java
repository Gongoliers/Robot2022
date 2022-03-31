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

	public EndgameArm(WPI_TalonSRX motor, EncoderSensor encoder, InvertableLimitSwitch limitSwitch) {
		m_motor = motor;
		m_encoder = encoder;
		m_limitSwitch = limitSwitch;
	}

	public WPI_TalonSRX getMotor() {
		return m_motor;
	}

	public EncoderSensor getEncoder() {
		return m_encoder;
	}

	public InvertableLimitSwitch getLimitSwitch() {
		return m_limitSwitch;
	}

	public void setCappedDistance(double distance) {
		m_cappedDistance = distance;
	}

	public void setAscentSpeed(double speed) {
		m_ascentSpeed = speed;
	}

	public void setDescentSpeed(double speed) {
		m_descentSpeed = speed;
	}

	public void engageSafety() {
		m_isSafeToAscend = false;
	}

	public void disengageSafety() {
		m_isSafeToAscend = true;
	}

	public void startAscend() {
		if (m_state != EndgameArmState.CAPPED) {
			m_state = EndgameArmState.ASCENDING;
		}
	}

	public boolean doneAscending() {
		return m_state != EndgameArmState.ASCENDING;
	}

	public void startDescend() {
		if (m_state != EndgameArmState.FLOORED) {
			m_state = EndgameArmState.DESCENDING;
		}
	}

	public boolean doneDescending() {
		return m_state != EndgameArmState.DESCENDING;
	}

	public void stop() {
		m_state = EndgameArmState.STOPPED;
	}

	public void update() {
		switch (m_state) {
			case ASCENDING:
				if (m_encoder.getDistance() >= m_cappedDistance) {
					m_state = EndgameArmState.CAPPED;
				}
				break;
			case DESCENDING:
				if (m_limitSwitch.isTriggered()) {
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

	public void driveArm() {
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
