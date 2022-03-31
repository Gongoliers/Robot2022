package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.thegongoliers.input.odometry.AverageEncoderSensor;
import com.thegongoliers.input.odometry.EncoderSensor;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.EndgameTimer;
import frc.robot.InvertableLimitSwitch;
import frc.robot.EndgameArm;
import frc.robot.PhoenixMotorControllerEncoder;
import frc.robot.Constants.EndgameConstants;

public class EndgameSubsystem extends SubsystemBase {

    // Left Motor (when standing behind battery)
    private final WPI_TalonSRX m_motorA = new WPI_TalonSRX(EndgameConstants.kMotorACAN);
    private EncoderSensor m_encoderA = new PhoenixMotorControllerEncoder(m_motorA, FeedbackDevice.CTRE_MagEncoder_Relative);
    private InvertableLimitSwitch m_limitSwitchA = new InvertableLimitSwitch(EndgameConstants.kLimitSwitchAPort);
    private final EndgameArm m_endgameA = new EndgameArm(m_motorA, m_encoderA, m_limitSwitchA);

    // Right Motor (when standing behind battery)
    private final WPI_TalonSRX m_motorB = new WPI_TalonSRX(EndgameConstants.kMotorBCAN);
    private EncoderSensor m_encoderB = new PhoenixMotorControllerEncoder(m_motorB, FeedbackDevice.CTRE_MagEncoder_Relative);
    private InvertableLimitSwitch m_limitSwitchB = new InvertableLimitSwitch(EndgameConstants.kLimitSwitchBPort);
    private final EndgameArm m_endgameB = new EndgameArm(m_motorB, m_encoderB, m_limitSwitchB);

    // Initializing Pneumatics
    private final Solenoid m_unlockArms = new Solenoid(PneumaticsModuleType.CTREPCM, EndgameConstants.kSolenoidCAN);

    public EndgameSubsystem() {

		m_endgameA.getMotor().setInverted(true);
		m_endgameA.getLimitSwitch().setInverted(true);
		m_endgameA.getEncoder().reset();

		m_endgameB.getMotor().setInverted(true);
		m_endgameB.getLimitSwitch().setInverted(true);
		m_endgameB.getEncoder().reset();

        // Ensure that Solenoid is Unpowered
        m_unlockArms.set(false);

    }

    public void powerPneumatics(Boolean power) {
        if (EndgameTimer.getMatchTime() < 30) {
            m_unlockArms.set(power);
        }
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

	public void ascend() {
		m_endgameA.ascend();
		m_endgameB.ascend();
	}

	public void descend() {
		m_endgameA.descend();
		m_endgameB.descend();
	}

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Endgame A", m_encoderA.getDistance());
        SmartDashboard.putNumber("Endgame B", m_encoderB.getDistance());
        // System.out.println("ENC A:"+m_encoderA.getDistance()+"ENC B"+m_encoderB.getDistance()+"A"+AMotorDone()+"B"+BMotorDone());
        // System.out.println("LA"+!m_limitSwitchA.isTriggered() + "LB"+!m_limitSwitchB.isTriggered());
    }

}
