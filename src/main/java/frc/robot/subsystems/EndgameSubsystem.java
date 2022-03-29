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
import frc.robot.PhoenixMotorControllerEncoder;
import frc.robot.Constants.EndgameConstants;

public class EndgameSubsystem extends SubsystemBase {

    enum EndgameArmState {
        STOPPED,
        ASCENDING,
        DESCENDING
    }

    // Left Motor (when standing behind battery)
    private final WPI_TalonSRX m_motorA = new WPI_TalonSRX(EndgameConstants.kMotorACAN);
    private EncoderSensor m_encoderA = new PhoenixMotorControllerEncoder(m_motorA, FeedbackDevice.CTRE_MagEncoder_Relative);
    private InvertableLimitSwitch m_limitSwitchA = new InvertableLimitSwitch(EndgameConstants.kLimitSwitchAPort);
    private EndgameArmState m_motorAState = EndgameArmState.STOPPED;

    // Right Motor (when standing behind battery)
    private final WPI_TalonSRX m_motorB = new WPI_TalonSRX(EndgameConstants.kMotorBCAN);
    private EncoderSensor m_encoderB = new PhoenixMotorControllerEncoder(m_motorB, FeedbackDevice.CTRE_MagEncoder_Relative);
    private InvertableLimitSwitch m_limitSwitchB = new InvertableLimitSwitch(EndgameConstants.kLimitSwitchBPort);
    private EndgameArmState m_motorBState = EndgameArmState.STOPPED;

    // Initializing Pneumatics
    private final Solenoid m_unlockArms = new Solenoid(PneumaticsModuleType.CTREPCM, EndgameConstants.kSolenoidCAN);


    public EndgameSubsystem() {
        /** Configuring Encoder Values
         *
         * See __ for documentation regarding Encoders:
         * https://docs.ctre-phoenix.com/en/stable/ch14_MCSensor.html#cross-the-road-electronics-magnetic-encoder-absolute-and-relative
         *
         * See __ for documentation regarding FeedbackDevice Constants:
         * https://store.ctr-electronics.com/content/api/cpp/html/namespacectre_1_1phoenix_1_1motorcontrol.html#a76df6b51b79bdd3710ddcd6ef43050e7
         */

        m_encoderA.reset();
        m_encoderB.reset();

        m_limitSwitchA.setInverted(true);
        m_limitSwitchB.setInverted(true);

        // Ensure that Solenoid is Unpowered
        m_unlockArms.set(false);

        m_motorA.setInverted(true);
        m_motorB.setInverted(true);

    }

    public void powerPneumatics(Boolean power) {
        if (EndgameTimer.getMatchTime() < 30) {
            m_unlockArms.set(power);
        }
    }

    public AverageEncoderSensor getEncoders() {
        return new AverageEncoderSensor(m_encoderA, m_encoderB);
    }

    public void updateEndgameA() {
        switch (m_motorAState) {
            case ASCENDING:
                if (m_encoderA.getDistance() >= EndgameConstants.kCappedDistanceA) {
                    m_motorAState = EndgameArmState.STOPPED;
                }
                break;
            case DESCENDING:
                if (m_limitSwitchA.isTriggered()) {
                    m_motorAState = EndgameArmState.STOPPED;
                    m_encoderA.reset();
                }
                break;
            case STOPPED:
                break;
        }
    }

    public void updateEndgameB() {
        switch (m_motorBState) {
            case ASCENDING:
                if (m_encoderB.getDistance() >= EndgameConstants.kCappedDistanceB) {
                    m_motorBState = EndgameArmState.STOPPED;
                }
                break;
            case DESCENDING:
                if (m_limitSwitchB.isTriggered()) {
                    m_motorBState = EndgameArmState.STOPPED;
                    m_encoderB.reset();
                }
                break;
            case STOPPED:
                break;
        }
    }


    @Override
    public void periodic() {
        SmartDashboard.putNumber("Endgame A", m_encoderA.getDistance());
        SmartDashboard.putNumber("Endgame B", m_encoderB.getDistance());
        // System.out.println("ENC A:"+m_encoderA.getDistance()+"ENC B"+m_encoderB.getDistance()+"A"+AMotorDone()+"B"+BMotorDone());
        // System.out.println("LA"+!m_limitSwitchA.isTriggered() + "LB"+!m_limitSwitchB.isTriggered());
    }

}
