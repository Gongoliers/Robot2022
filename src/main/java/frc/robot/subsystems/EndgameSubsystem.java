package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.thegongoliers.input.odometry.AverageEncoderSensor;
import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.input.switches.LimitSwitch;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.EndgameTimer;
import frc.robot.PhoenixMotorControllerEncoder;
import frc.robot.Constants.EndgameConstants;

public class EndgameSubsystem extends SubsystemBase {
    // Left Motor (when standing behind battery)
    private final WPI_TalonSRX m_motorA = new WPI_TalonSRX(EndgameConstants.kMotorACAN);

    // Right Motor (when standing behind battery)
    private final WPI_TalonSRX m_motorB = new WPI_TalonSRX(EndgameConstants.kMotorBCAN);

    // Initializing Pneumatics
    private final Solenoid m_unlockArms = new Solenoid(PneumaticsModuleType.CTREPCM, EndgameConstants.kSolenoidCAN);
    
    // Initializing EncoderSensor
    private EncoderSensor m_encoderA;
    private EncoderSensor m_encoderB;

    // Initialize Ignoring Encoders
    private boolean m_ignoreEncoders;

    // Initialize Limit Switches
    private DigitalInput m_limitSwitchA;
    private DigitalInput m_limitSwitchB;
    
    public EndgameSubsystem() {
        /** Configuring Encoder Values
         * 
         * See __ for documentation regarding Encoders:
         * https://docs.ctre-phoenix.com/en/stable/ch14_MCSensor.html#cross-the-road-electronics-magnetic-encoder-absolute-and-relative
         * 
         * See __ for documentation regarding FeedbackDevice Constants:
         * https://store.ctr-electronics.com/content/api/cpp/html/namespacectre_1_1phoenix_1_1motorcontrol.html#a76df6b51b79bdd3710ddcd6ef43050e7
         */
        m_encoderA = new PhoenixMotorControllerEncoder(m_motorA, FeedbackDevice.CTRE_MagEncoder_Relative);
        m_encoderB = new PhoenixMotorControllerEncoder(m_motorB, FeedbackDevice.CTRE_MagEncoder_Relative);

        m_limitSwitchA = new DigitalInput(EndgameConstants.kLimitSwitchAPort);
        m_limitSwitchB = new DigitalInput(EndgameConstants.kLimitSwitchBPort);

        m_encoderA.reset();
        m_encoderB.reset();
        // TODO: verify all encoders


        // Ensure that Solenoid is Unpowered
        m_unlockArms.set(false);

    }
    
    public void powerPneumatics(Boolean power) {
        if (EndgameTimer.getMatchTime() < 30) {
            m_unlockArms.set(power);
        }
    }

    public void setSpeed(double s) {
        if (s != 0) { // s != 0
            if (m_encoderA.getVelocity() > m_encoderB.getVelocity()) {
                if (!AMotorDone(s)) {
                    // Lowering Motor A speed
                    m_motorA.set((m_encoderB.getVelocity() / m_encoderA.getVelocity())*s);
                } else {System.out.println("MOTOR A DONE");}
                if (!BMotorDone(s)) {
                   m_motorB.set(s);
                }
            } else if (m_encoderA.getVelocity() < m_encoderB.getVelocity()) {
                if (!AMotorDone(s)) {
                    m_motorA.set(s);
                } 
                if (!BMotorDone(s)) {
                    m_motorB.set((m_encoderA.getVelocity() / m_encoderB.getVelocity())*s);
                } 
            } else {
                m_motorA.set(s);
                m_motorB.set(s);
            }
            System.out.println("SPEEDS : \nMOTOR A:");
            System.out.println(m_motorA.get());
            System.out.println("\nMOTOR B:");
            System.out.println(m_motorB.get());
        }
    }

    public AverageEncoderSensor getEncoders() {
        return new AverageEncoderSensor(m_encoderA, m_encoderB);
    }

    public EncoderSensor getAEncoder() {return m_encoderA;}

    public EncoderSensor getBEncoder() {return m_encoderB;}

    public void resetEncoders() {
        m_encoderA.reset();
        m_encoderB.reset();
    }

    public void stop() {
        m_motorA.stopMotor();
        m_motorB.stopMotor();
        m_unlockArms.set(false);
    }
    
    public void stopMotors() {
        m_motorA.stopMotor();
        m_motorB.stopMotor();
    }

    public void ignoreEncoders(boolean val) {
        m_ignoreEncoders = val;
    }

    public boolean AMotorDone() {
        return AMotorDone(m_motorA.get());
    }

    public boolean AMotorDone(double speed) {
        if (speed == 0) {return false;}
        if (m_ignoreEncoders) {
            System.out.println("ENDGAME SUBSYSTEM: Ignoring Encoders");
            return false;}
        if (speed > 0) {
            System.out.println("ENDGAME ENCODER A: "+ m_encoderA.getDistance());
            return (m_encoderA.getDistance() >= EndgameConstants.kCappedDistance);
        } else {
            // SPEED IS DECREASING
            return m_limitSwitchA.get();
        }
    }

    public boolean BMotorDone() {
        return BMotorDone(m_motorB.get());
    }

    public boolean BMotorDone(double speed) {
        if (speed == 0) {return false;}
        if (m_ignoreEncoders) {
            System.out.println("ENDGAME SUBSYSTEM: Ignoring Encoders");
            return false;}
        if (speed > 0) {
            System.out.println("ENDGAME ENCODER B: "+ m_encoderB.getDistance());
            return (m_encoderB.getDistance() >= EndgameConstants.kCappedDistance);
        } else {
            // SPEED IS DECREASING
            return m_limitSwitchB.get();
        }
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Endgame", getEncoders().getDistance());
    }
    
}
