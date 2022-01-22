package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.PowerEfficiencyModule;
import com.thegongoliers.output.drivetrain.StabilityModule;
import com.thegongoliers.output.drivetrain.VoltageControlModule;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.NavxGyro;
import frc.robot.Constants.DriveConstants;

public class DrivetrainSubsystem extends SubsystemBase {
    // Initializing Right Side Motors
    private final WPI_TalonFX m_rightMotors = new WPI_TalonFX(DriveConstants.kRightDrivePWM);
    private final WPI_TalonFX m_leftMotors = new WPI_TalonFX(DriveConstants.kLeftDrivePWM);

    // Initializing the Modular Drivetrain
    private ModularDrivetrain m_modularDrivetrain;
    private VoltageControlModule m_voltageControlModule;
    private StabilityModule m_stabilityModule;
    private PowerEfficiencyModule m_powerEfficiencyModule;

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
        
        // TODO: get encoder value
        // Encoders

        // NavX Gyro Initialization
        Gyro m_gyro = new NavxGyro(m_navx);
        
        // Modular Drivetrain
        m_modularDrivetrain = ModularDrivetrain.from(m_drivetrain);
        
        // Stability Module
        m_stabilityModule = new StabilityModule(m_gyro, 0.05, 0.25);
        m_stabilityModule.setTurnThreshold(0.075);

        // Voltage Control Module 
        m_voltageControlModule = new VoltageControlModule(DriveConstants.kFastVoltage)
        //TODO: PATH FOLLOWER MODULE HERE

        //TODO: TARGET ALIGNMENT MODULE HERE



    }
    
}
