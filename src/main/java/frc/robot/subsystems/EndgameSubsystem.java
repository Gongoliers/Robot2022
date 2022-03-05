package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.thegongoliers.input.odometry.BaseEncoderSensor;
import com.thegongoliers.input.odometry.DistanceSensor;
import com.thegongoliers.input.odometry.VelocitySensor;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
    private BaseEncoderSensor m_encoderSensor;
    
    public EndgameSubsystem() {
        /** Configuring Encoder Values
         * 
         * See __ for documentation regarding Encoders:
         * https://docs.ctre-phoenix.com/en/stable/ch14_MCSensor.html#cross-the-road-electronics-magnetic-encoder-absolute-and-relative
         * 
         * See __ for documentation regarding FeedbackDevice Constants:
         * https://store.ctr-electronics.com/content/api/cpp/html/namespacectre_1_1phoenix_1_1motorcontrol.html#a76df6b51b79bdd3710ddcd6ef43050e7
         */
        // TODO: CONFIGURE THIS TO MATCH SELECTED MOTOR
        m_leftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        DistanceSensor m_DistanceSensor = new DistanceSensor() {
            public double getDistance() {
                return m_leftMotor.getSelectedSensorPosition() * EndgameConstants.kEncoderDistancePerPulse;
            }
        };
        VelocitySensor m_VelocitySensor = new VelocitySensor() {
            public double getVelocity() {
                return m_leftMotor.getSelectedSensorVelocity() * EndgameConstants.kEncoderDistancePerPulse;
            }
        };
        m_encoderSensor = new BaseEncoderSensor(m_DistanceSensor, m_VelocitySensor);
        m_encoderSensor.reset();

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

    public BaseEncoderSensor getEncoders() {
        return m_encoderSensor;
    }

    public void stop() {
        m_motors.stopMotor();
        m_unlockArms.set(false);
    }

    @Override
    public void periodic() {
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
