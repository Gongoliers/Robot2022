package frc.robot.subsystems;

import java.util.List;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import com.kylecorry.pid.PID;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.PathFollowerModule;
import com.thegongoliers.output.drivetrain.PowerEfficiencyModule;
import com.thegongoliers.output.drivetrain.StabilityModule;
import com.thegongoliers.output.drivetrain.TargetAlignmentModule;
import com.thegongoliers.output.drivetrain.TractionControlModule;
import com.thegongoliers.output.drivetrain.VoltageControlModule;
import com.thegongoliers.math.GMath;
import com.thegongoliers.input.odometry.AverageEncoderSensor;
import com.thegongoliers.input.odometry.EncoderSensor;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.NavxGyro;
import frc.robot.PhoenixMotorControllerEncoder;
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
    private EncoderSensor m_leftEncoderSensor, m_rightEncoderSensor;

    // Initializing the Modular Drivetrain
    private ModularDrivetrain m_modularDrivetrain;
    private VoltageControlModule m_voltageControlModule;
    private PowerEfficiencyModule m_powerEfficiencyModule;

    
    // Initiating NAVX
    public AHRS m_navx = new AHRS(SerialPort.Port.kMXP);
    private Gyro m_gyro = new NavxGyro(m_navx);
    
    public DrivetrainSubsystem(VisionSubsystem vision) {

        m_rightMotors.setInverted(true);
        
        // Assembling the Drivetrain
        final DifferentialDrive m_drivetrain = new DifferentialDrive(m_leftMotors, m_rightMotors);
        m_drivetrain.setSafetyEnabled(true);
        m_drivetrain.setExpiration(0.1);
        m_drivetrain.setMaxOutput(1.0);
        m_drivetrain.setDeadband(0.05);
        
        // Encoders
        var leftAverageEncoder = new AverageEncoderSensor(
            new PhoenixMotorControllerEncoder(m_leftMotor_1, FeedbackDevice.IntegratedSensor),
            new PhoenixMotorControllerEncoder(m_leftMotor_2, FeedbackDevice.IntegratedSensor),
            new PhoenixMotorControllerEncoder(m_leftMotor_3, FeedbackDevice.IntegratedSensor)
        );

        var rightAverageEncoder = new AverageEncoderSensor(
            new PhoenixMotorControllerEncoder(m_rightMotor_1, FeedbackDevice.IntegratedSensor),
            new PhoenixMotorControllerEncoder(m_rightMotor_2, FeedbackDevice.IntegratedSensor),
            new PhoenixMotorControllerEncoder(m_rightMotor_3, FeedbackDevice.IntegratedSensor)
        );

        m_leftEncoderSensor = leftAverageEncoder.scaledBy(Constants.DriveConstants.kEncoderDistancePerPulse);
        m_rightEncoderSensor = rightAverageEncoder.scaledBy(Constants.DriveConstants.kEncoderDistancePerPulse).inverted();
        
        // Modular Drivetrain
        m_modularDrivetrain = ModularDrivetrain.from(m_drivetrain);
        m_modularDrivetrain.setInactiveResetSeconds(DriveConstants.kInactivityThresholdSeconds);
        
        var stability = new StabilityModule(m_gyro, 0.02, 0.35);

        var tractionControl = new TractionControlModule(m_leftEncoderSensor, m_rightEncoderSensor, 0.05, 0.2);

        // Voltage Control Module 
        m_voltageControlModule = new VoltageControlModule(DriveConstants.kNormalVoltage);

        // Power Effeciency Module
        m_powerEfficiencyModule = new PowerEfficiencyModule(DriveConstants.kSecondsToReachFullSpeed, DriveConstants.kTurnThreshold);

        // Path follower module
        // TODO: Calibrate this
        var pathFollowerModule = new PathFollowerModule(m_gyro,
                List.of(m_leftEncoderSensor, m_rightEncoderSensor), 0.25, 0.02);
        pathFollowerModule.setForwardTolerance(0.5); // 0.5 feet
        pathFollowerModule.setTurnTolerance(1); // 1 degree

        // Target alignment
        // TODO: Calibrate this
        var targetAlignmentModule = new TargetAlignmentModule(vision.getTargetingCamera(),
             new PID(0.12, 0.05, 0.005), new PID(0, 0, 0), false);

        m_modularDrivetrain.setModules(tractionControl, pathFollowerModule, targetAlignmentModule, m_voltageControlModule, m_powerEfficiencyModule);
    }

    public void arcadeDrive(double forwardSpeed, double turnSpeed) {
        // TODO: Extract turn deadband constant
        var turn = GMath.deadband(turnSpeed, 0.2) * 0.75;
        m_modularDrivetrain.arcade(-forwardSpeed, turn);
    }

    public void stop() {
        m_modularDrivetrain.stop();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left distance (ft)", m_leftEncoderSensor.getDistance());
        SmartDashboard.putNumber("Right distance (ft)", m_rightEncoderSensor.getDistance());
        SmartDashboard.putNumber("Left speed (ft)", m_leftEncoderSensor.getVelocity());
        SmartDashboard.putNumber("Right speed (ft)", m_rightEncoderSensor.getVelocity());
    }


    /**
     * This function will enable or disable turbo mode.
     * setTurboEnabled(true) = robot travels at turbo speed
     * setTurboEnabled(false) = robot travels at normal speed
     */
    public void setTurboEnabled(boolean turboEnabled) {
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
