package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.thegongoliers.input.odometry.AverageEncoderSensor;
import com.thegongoliers.input.odometry.EncoderSensor;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PhoenixMotorControllerEncoder;
import frc.robot.Constants.EndgameConstants;

public class EndgameSubsystem extends SubsystemBase {
    // Left Motor (when standing behind battery)
    private final WPI_TalonSRX m_leftMotor = new WPI_TalonSRX(EndgameConstants.kLeftMotorCAN);

    // Right Motor (when standing behind battery)
    private final WPI_TalonSRX m_rightMotor = new WPI_TalonSRX(EndgameConstants.kRightMotorCAN);

    // Binding Motors Together
    private final MotorControllerGroup m_motors = new MotorControllerGroup(m_leftMotor, m_rightMotor);

    // Initializing Pneumatics
    private final Solenoid m_unlockArms = new Solenoid(PneumaticsModuleType.CTREPCM, EndgameConstants.kSolenoidCAN);
    
    // Initializing EncoderSensor
    private EncoderSensor m_leftEncoder;
    private EncoderSensor m_rightEncoder;
    private AverageEncoderSensor m_encoderSensor;

    // Reckless Mode
    private Boolean error_thrown = false;
    
    public EndgameSubsystem() {
        /** Configuring Encoder Values
         * 
         * See __ for documentation regarding Encoders:
         * https://docs.ctre-phoenix.com/en/stable/ch14_MCSensor.html#cross-the-road-electronics-magnetic-encoder-absolute-and-relative
         * 
         * See __ for documentation regarding FeedbackDevice Constants:
         * https://store.ctr-electronics.com/content/api/cpp/html/namespacectre_1_1phoenix_1_1motorcontrol.html#a76df6b51b79bdd3710ddcd6ef43050e7
         */
        m_leftEncoder = new PhoenixMotorControllerEncoder(m_leftMotor, FeedbackDevice.CTRE_MagEncoder_Relative);
        m_rightEncoder = new PhoenixMotorControllerEncoder(m_rightMotor, FeedbackDevice.CTRE_MagEncoder_Relative);

        m_encoderSensor = new AverageEncoderSensor(m_leftEncoder, m_rightEncoder);
        // TODO: verify all encoders


        // Ensure that Solenoid is Unpowered
        m_unlockArms.set(false);

        // Reverse One Motor Controller
        m_leftMotor.setInverted(true); // TODO: THIS ISN'T RIGHT -- CONFIGURE
        /**
         * TODO: unsure of whether or not this affects Motor Controller group
         * If this does not affect MotorControllerGroup:
         *      1. Change getMotors to getLeftMotor & getRightMotor
         *      2. Change getMotors to setMotors and set from there
         */

    }
    public void powerPneumatics(Boolean power) {
        m_unlockArms.set(power);
    }

    public MotorControllerGroup getMotors() {
        return m_motors;
    }

    public AverageEncoderSensor getEncoders() {
        return new AverageEncoderSensor(m_leftEncoder, m_rightEncoder);
    }

    public void stop() {
        m_motors.stopMotor();
        m_unlockArms.set(false);
    }

    @Override
    public void periodic() {
        /**
         * This ensures that the two arms are at (about) the same height when extending / retracting
         * If an error is resulted, the code will stop the motors, HOWEVER, if the driver tries to 
         * operate the endgame subsystem again, no error will occur. (DOUBLE SAFETY CHECK)
         */
        if ((m_leftEncoder.getDistance() + 2 > m_rightEncoder.getDistance()) && !error_thrown) {
            m_motors.stopMotor();
            error_thrown = true;
        }
        /** 
         * This is a safety check. We will implement a check within the command, 
         *  but this acts as a catch all => ensures that the Endgame does not over
         *  retract
        */
        if (m_encoderSensor.getDistance() > (EndgameConstants.kCappedDistance + 1)) {
            // TODO: Ensure that this aligns with the direction that the sensor
            // returns --> i.e. make sure positive velocity = retracting
            if (m_encoderSensor.getVelocity() > 0) {
                m_motors.stopMotor();
            }
        }
    }
    
}
