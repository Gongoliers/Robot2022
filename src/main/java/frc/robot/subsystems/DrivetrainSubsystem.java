package frc.robot.subsystems;

import java.util.List;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.PowerEfficiencyModule;
import com.thegongoliers.output.drivetrain.VoltageControlModule;
import com.thegongoliers.input.odometry.WPIEncoderSensor;
import com.thegongoliers.math.GMath;
import com.thegongoliers.input.odometry.BaseEncoderSensor;
import com.thegongoliers.input.odometry.DistanceSensor;
import com.thegongoliers.input.odometry.VelocitySensor;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.NavxGyro;
import frc.robot.Constants.DriveConstants;

public class DrivetrainSubsystem extends SubsystemBase {
    // Initializing Motor Controllers
    
    //  ---  Left Motors
    private final WPI_TalonFX m_leftMotor_1 = new WPI_TalonFX(DriveConstants.kLeftDriveCAN1);
    private final WPI_TalonFX m_leftMotor_2 = new WPI_TalonFX(DriveConstants.kLeftDriveCAN2);
    private final WPI_TalonFX m_leftMotor_3 = new WPI_TalonFX(DriveConstants.kLeftDriveCAN3);

    //  --- Right Motors
    private final WPI_TalonFX m_rightMotor_1 = new WPI_TalonFX(DriveConstants.kRightDriveCAN1);
    private final WPI_TalonFX m_rightMotor_2 = new WPI_TalonFX(DriveConstants.kRightDriveCAN2);
    private final WPI_TalonFX m_rightMotor_3 = new WPI_TalonFX(DriveConstants.kRightDriveCAN3);
    
    // Initializing Motor Controller Group
    private final MotorControllerGroup m_leftMotors = new MotorControllerGroup(m_leftMotor_1, m_leftMotor_2, m_leftMotor_3);
    private final MotorControllerGroup m_rightMotors = new MotorControllerGroup(m_rightMotor_1, m_rightMotor_2, m_rightMotor_3);

    // Initializing EncoderSensors
    private BaseEncoderSensor m_leftEncoderSensor, m_rightEncoderSensor;

    // Initializing the Modular Drivetrain
    private ModularDrivetrain m_modularDrivetrain;
    private VoltageControlModule m_voltageControlModule;
    private PowerEfficiencyModule m_powerEfficiencyModule;

    // Turbo
    private boolean m_turboEnabled = false; 

    
    // Initiating NAVX
    public AHRS m_navx = new AHRS(SerialPort.Port.kMXP);
    
    public DrivetrainSubsystem() {

        m_rightMotors.setInverted(true);
        
        // Assembling the Drivetrain
        final DifferentialDrive m_drivetrain = new DifferentialDrive(m_leftMotors, m_rightMotors);
        m_drivetrain.setSafetyEnabled(true);
        m_drivetrain.setExpiration(0.1);
        m_drivetrain.setMaxOutput(1.0);
        m_drivetrain.setDeadband(0.05);

        m_leftMotor_1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_leftMotor_2.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_leftMotor_3.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

        m_rightMotor_1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_rightMotor_2.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        m_rightMotor_3.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        
        // Encoder Helpers
        // TODO: VERIFY CHANGES
        DistanceSensor m_leftEncoderPosition = new DistanceSensor() {
            public double getDistance() {
                var ticks = (m_leftMotor_1.getSelectedSensorPosition() + m_leftMotor_2.getSelectedSensorPosition() + m_leftMotor_3.getSelectedSensorPosition())/3;
                return ticks * Constants.DriveConstants.kEncoderDistancePerPulse;
                
        }};
        VelocitySensor m_leftEncodeVelocity = new VelocitySensor() {
            public double getVelocity() {
                var ticks = (m_leftMotor_1.getSelectedSensorVelocity() + m_leftMotor_2.getSelectedSensorVelocity() + m_leftMotor_3.getSelectedSensorVelocity())/3;
                return ticks * Constants.DriveConstants.kEncoderDistancePerPulse;
            }
        };

        DistanceSensor m_rightEncoderPosition = new DistanceSensor() {
            public double getDistance() {
                var ticks = (m_rightMotor_1.getSelectedSensorPosition() + m_rightMotor_2.getSelectedSensorPosition() + m_rightMotor_3.getSelectedSensorPosition())/3;
                return -ticks * Constants.DriveConstants.kEncoderDistancePerPulse;
            }
        };
        VelocitySensor m_rightEncodeVelocity = new VelocitySensor() {
            public double getVelocity() {
                var ticks = (m_rightMotor_1.getSelectedSensorVelocity() + m_rightMotor_2.getSelectedSensorVelocity() + m_rightMotor_3.getSelectedSensorVelocity())/3;
                return -ticks * Constants.DriveConstants.kEncoderDistancePerPulse;
            }
        };

        // Encoders
        m_leftEncoderSensor = new BaseEncoderSensor(m_leftEncoderPosition, m_leftEncodeVelocity);
        m_rightEncoderSensor = new BaseEncoderSensor(m_rightEncoderPosition, m_rightEncodeVelocity);
        
        // Modular Drivetrain
        m_modularDrivetrain = ModularDrivetrain.from(m_drivetrain);
        
        // Voltage Control Module 
        m_voltageControlModule = new VoltageControlModule(DriveConstants.kFastVoltage);

        // Power Effeciency Module
        m_powerEfficiencyModule = new PowerEfficiencyModule(DriveConstants.kSecondsToReachFullSpeed, DriveConstants.kTurnThreshold);


        m_modularDrivetrain.setModules(m_voltageControlModule, m_powerEfficiencyModule);
    }

    public void arcadeDrive(double forwardSpeed, double turnSpeed) {
        // TODO: Extract turn deadband constant
        var turn = GMath.deadband(turnSpeed, 0.2);
        m_modularDrivetrain.arcade(-forwardSpeed, turn);
    }

    public void stop() {
        m_modularDrivetrain.stop();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left distance (in)", m_leftEncoderSensor.getDistance());
        SmartDashboard.putNumber("Right distance (in)", m_rightEncoderSensor.getDistance());
        SmartDashboard.putNumber("Left speed (in)", m_leftEncoderSensor.getVelocity());
        SmartDashboard.putNumber("Right speed (in)", m_rightEncoderSensor.getVelocity());
    }


    /**
     * This function will enable or disable turbo mode.
     * setTurboEnabled(true) = robot travels at turbo speed
     * setTurboEnabled(false) = robot travels at normal speed
     */
    public void setTurboEnabled(boolean turboEnabled) {
        m_turboEnabled = turboEnabled;
        if (turboEnabled) {
            m_voltageControlModule.setMaxVoltage(DriveConstants.kFastVoltage);
        } else {
            m_voltageControlModule.setMaxVoltage(DriveConstants.kNormalVoltage);
        }
    }

    public ModularDrivetrain getModularDrivetrain() {
        return m_modularDrivetrain;
    }

}
