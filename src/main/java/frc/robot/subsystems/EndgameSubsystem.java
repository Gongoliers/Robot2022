package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kylecorry.pid.PID;
import com.thegongoliers.input.odometry.AverageEncoderSensor;
import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.output.actuators.GSpeedController;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.InvertableLimitSwitch;
import frc.robot.EndgameArm;
import frc.robot.PhoenixMotorControllerEncoder;
import frc.robot.Constants.EndgameConstants;

public class EndgameSubsystem extends SubsystemBase {

    // Left Motor (when standing behind battery)
    private final WPI_TalonSRX m_motorA = new WPI_TalonSRX(EndgameConstants.kMotorACAN);
    private EncoderSensor m_encoderA = new PhoenixMotorControllerEncoder(m_motorA, FeedbackDevice.CTRE_MagEncoder_Relative)
		.scaledBy(EndgameConstants.kCappedDistanceA);
    private InvertableLimitSwitch m_limitSwitchA = new InvertableLimitSwitch(EndgameConstants.kLimitSwitchAPort);
	private GSpeedController m_controllerA = new GSpeedController(m_motorA, m_encoderA, new PID(0.0, 0.0, 0.0), new PID(0.0, 0.0, 0.0));
    private final EndgameArm m_endgameA = new EndgameArm(m_controllerA, m_encoderA, m_limitSwitchA);

    // Right Motor (when standing behind battery)
    private final WPI_TalonSRX m_motorB = new WPI_TalonSRX(EndgameConstants.kMotorBCAN);
    private EncoderSensor m_encoderB = new PhoenixMotorControllerEncoder(m_motorB, FeedbackDevice.CTRE_MagEncoder_Relative)
		.scaledBy(EndgameConstants.kCappedDistanceB);
    private InvertableLimitSwitch m_limitSwitchB = new InvertableLimitSwitch(EndgameConstants.kLimitSwitchBPort);
	private GSpeedController m_controllerB = new GSpeedController(m_motorB, m_encoderB, new PID(0.0, 0.0, 0.0), new PID(0.0, 0.0, 0.0));
    private final EndgameArm m_endgameB = new EndgameArm(m_controllerB, m_encoderB, m_limitSwitchB);

    // Initializing Pneumatics
    private final Solenoid m_unlockArms = new Solenoid(PneumaticsModuleType.CTREPCM, EndgameConstants.kSolenoidCAN);

    public EndgameSubsystem() {
		m_endgameA.getEncoder().reset();
		m_endgameB.getEncoder().reset();

		m_endgameA.getMotor().setInverted(true);
		m_endgameB.getMotor().setInverted(true);
		
		m_endgameA.getLimitSwitch().setInverted(true);
		m_endgameB.getLimitSwitch().setInverted(true); // TODO: Check if limit switches are replaced

		// Ensure that Solenoid is Unpowered
		m_unlockArms.set(false);

    }

	public void enableAscension() {
		m_endgameA.disengageSafety();
		m_endgameB.disengageSafety();
		m_unlockArms.set(true);
	}

	public void disableAscension() {
		m_endgameA.engageSafety();
		m_endgameB.engageSafety();
		m_unlockArms.set(false);
	}

	public void setDirectionAscend() {
		m_endgameA.setDirectionAscend();
		m_endgameB.setDirectionAscend();
	}

	public boolean doneAscending() {
		return m_endgameA.doneAscending() && m_endgameA.doneAscending();
	}

	public void setDirectionDescend() {
		m_endgameA.setDirectionDescend();
		m_endgameB.setDirectionDescend();
	}

	public boolean doneDescending() {
		return m_endgameA.doneDescending() && m_endgameB.doneDescending();
	}

	public void drive() {
		m_endgameA.update();
		m_endgameA.driveArm();
		m_endgameB.update();
		m_endgameB.driveArm();
	}

	public void stop() {
		m_endgameA.stop();
		m_endgameB.stop();
	}

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Endgame A", m_encoderA.getDistance());
        SmartDashboard.putNumber("Endgame B", m_encoderB.getDistance());

    }

}
