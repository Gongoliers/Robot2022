package frc.robot.subsystems;

import java.util.List;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.PowerEfficiencyModule;
import com.thegongoliers.output.drivetrain.StabilityModule;
import com.thegongoliers.output.drivetrain.VoltageControlModule;
import com.thegongoliers.output.drivetrain.PathFollowerModule;
import com.thegongoliers.input.odometry.WPIEncoderSensor;
import com.thegongoliers.input.odometry.VelocitySensor;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.NavxGyro;
import frc.robot.Constants.DriveConstants;

public class DrivetrainSubsystem extends SubsystemBase {
    // Initializing Motors
    private final WPI_TalonFX m_leftMotors = new WPI_TalonFX(DriveConstants.kLeftDrivePWM);
    private final WPI_TalonFX m_rightMotors = new WPI_TalonFX(DriveConstants.kRightDrivePWM);

    // Initializing Encoders
    private Encoder m_leftEncoder, m_rightEncoder;

    // Initializing EncoderSensors
    private WPIEncoderSensor m_leftEncoderSensor, m_rightEncoderSensor;

    // Initializing the Modular Drivetrain
    private ModularDrivetrain m_modularDrivetrain;
    private VoltageControlModule m_voltageControlModule;
    private StabilityModule m_stabilityModule;
    private PowerEfficiencyModule m_powerEfficiencyModule;
    private PathFollowerModule m_pathFollowerModule;

    // Turbo
    private boolean m_turboEnabled = false; 

    
    // Initiating NAVX
    public AHRS m_navx = new AHRS(SerialPort.Port.kMXP);
    
    public DrivetrainSubsystem() {
        
        // Assembling the Drivetrain
        final DifferentialDrive m_drivetrain = new DifferentialDrive(m_leftMotors, m_rightMotors);
        m_drivetrain.setSafetyEnabled(true);
        m_drivetrain.setExpiration(0.1);
        m_drivetrain.setMaxOutput(1.0);
        m_drivetrain.setDeadband(0.05);
        
        // TODO: setup encoders to use actual encoders
        // Encoders
        m_leftEncoder = new Encoder(0, 1); // TODO: Configure RobotContainer to store these values
        m_leftEncoderSensor = new WPIEncoderSensor(m_leftEncoder);
        m_rightEncoder = new Encoder(0, 1); // TODO: Configure RobotContainer to store these values
        m_rightEncoderSensor = new WPIEncoderSensor(m_rightEncoder);

        // NavX Gyro Initialization
        Gyro m_gyro = new NavxGyro(m_navx);
        
        // Modular Drivetrain
        m_modularDrivetrain = ModularDrivetrain.from(m_drivetrain);
        
        // Stability Module
        m_stabilityModule = new StabilityModule(m_gyro, 0.05, 0.25);
        m_stabilityModule.setTurnThreshold(0.075);

        // Voltage Control Module 
        m_voltageControlModule = new VoltageControlModule(DriveConstants.kNormalVoltage);
        //TODO: PATH FOLLOWER MODULE HERE

        // Path Follower Module
        m_pathFollowerModule = new PathFollowerModule(m_gyro, List.of(m_leftEncoderSensor, m_rightEncoderSensor), 0.5, 0.02); // TODO: Tune

        //TODO: TARGET ALIGNMENT MODULE HERE



    }

    public void arcadeDrive(double forwardSpeed, double turnSpeed) {
        m_modularDrivetrain.arcade(-forwardSpeed, turnSpeed);
    }

    public void stop() {
        m_modularDrivetrain.stop();
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