package frc.robot;

public class EndgameArm {

	enum EndgameArmState {
		ASCENDING,
		DESCENDING
		STOPPED,
		CAPPED,
		FLOORED,
	}

	private final WPI_TalonSRX m_motor;
	private final EncoderSensor m_encoder;
	private final InvertableLimitSwitch m_limitSwitch;
	private EndgameArmState m_state = STOPPED;
	private boolean m_safeToAscend = false;
	private double m_cappedDistance, m_ascentSpeed, m_descentSpeed;

	public EndgameArm(WPI_TalonSRX motor, EncoderSensor encoder, InvertableLimitSwitch limitSwitch) {
		m_motor = motor;
		m_encoder = encoder;
		m_limitSwitch = limitSwitch;
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
		m_safeToAscend = false;
	}

	public void disengageSafety() {
		m_safeToAscend = true;
	}

	public void update() {
		switch (m_state) {
			case ASCENDING:
				if (m_encoder.getDistance() >= m_cappedDistance) {
					m_state = CAPPED;
				}
				break;
			case DESCENDING:
				if (m_limitSwitch.isTriggered()) {
					m_state = FLOORED;
					m_encoder.reset();
				}
				break;
			case STOPPED:
			case CAPPED:
			case FLOORED:
				break;
		}
	}

	public driveArm() {
		switch (m_state) {
			case ASCENDING:
				if (m_safeToAscend) {
					m_motor.set(m_ascentSpeed);
				}
				break;
			case DESCENDING:
				m_motor.set(m_descentSpeed);
				break;
			case STOPPED:
			case CAPPED:
			case FLOORED:
				break;
		}
	}

}
