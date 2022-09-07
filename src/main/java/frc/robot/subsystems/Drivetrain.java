package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kylecorry.pid.PID;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.PowerEfficiencyModule;
import com.thegongoliers.output.drivetrain.TargetAlignmentModule;
import com.thegongoliers.output.drivetrain.VoltageControlModule;
import com.thegongoliers.math.GMath;
import com.thegongoliers.input.odometry.AverageEncoderSensor;
import com.thegongoliers.input.odometry.EncoderSensor;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import frc.robot.PhoenixMotorControllerEncoder;
import frc.robot.Constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
    // Left motors
    private final WPI_TalonFX mLeftMotor_1 = new WPI_TalonFX(DriveConstants.kLeftDriveCAN1);
    private final WPI_TalonFX mLeftMotor_2 = new WPI_TalonFX(DriveConstants.kLeftDriveCAN2);
    private final WPI_TalonFX mLeftMotor_3 = new WPI_TalonFX(DriveConstants.kLeftDriveCAN3);
    private final MotorControllerGroup mLeftMotors = new MotorControllerGroup(mLeftMotor_1, mLeftMotor_2, mLeftMotor_3);

    // Right motors
    private final WPI_TalonFX mRightMotor_1 = new WPI_TalonFX(DriveConstants.kRightDriveCAN1);
    private final WPI_TalonFX mRightMotor_2 = new WPI_TalonFX(DriveConstants.kRightDriveCAN2);
    private final WPI_TalonFX mRightMotor_3 = new WPI_TalonFX(DriveConstants.kRightDriveCAN3);
    private final MotorControllerGroup mRightMotors = new MotorControllerGroup(mRightMotor_1, mRightMotor_2, mRightMotor_3);

    private EncoderSensor mLeftEncoderSensor, mRightEncoderSensor;

    private final ModularDrivetrain mModularDrivetrain;
    private final VoltageControlModule mVoltageControlModule;
    private final PowerEfficiencyModule mPowerEfficiencyModule;
    private final TargetAlignmentModule mTargetAlignmentModule;
    
    public Drivetrain(VisionSubsystem vision) {

        mLeftMotors.setInverted(false);
        // Right-side must be inverted otherwise the right-side drivetrain will
        // rotate opposite the direction it is supposed to
        mRightMotors.setInverted(true);
        
        // Create a differential drivetrain controlling the left and right motors
        var differentialDrivetrain = new DifferentialDrive(mLeftMotors, mRightMotors);
        // TODO: Extract these constants
        differentialDrivetrain.setSafetyEnabled(true);
        differentialDrivetrain.setExpiration(0.1);
        differentialDrivetrain.setMaxOutput(1.0);
        differentialDrivetrain.setDeadband(0.05);
        
        // Initialize encoders on both sides
        var leftAverageEncoder = new AverageEncoderSensor(
            new PhoenixMotorControllerEncoder(mLeftMotor_1, FeedbackDevice.IntegratedSensor),
            new PhoenixMotorControllerEncoder(mLeftMotor_2, FeedbackDevice.IntegratedSensor),
            new PhoenixMotorControllerEncoder(mLeftMotor_3, FeedbackDevice.IntegratedSensor)
        );

        mLeftEncoderSensor = leftAverageEncoder.scaledBy(DriveConstants.kInchesPerEncoderPulse);

        var rightAverageEncoder = new AverageEncoderSensor(
            new PhoenixMotorControllerEncoder(mRightMotor_1, FeedbackDevice.IntegratedSensor),
            new PhoenixMotorControllerEncoder(mRightMotor_2, FeedbackDevice.IntegratedSensor),
            new PhoenixMotorControllerEncoder(mRightMotor_3, FeedbackDevice.IntegratedSensor)
        );

        // The right-side must also be inverted here
        mRightEncoderSensor = rightAverageEncoder.scaledBy(DriveConstants.kInchesPerEncoderPulse).inverted();

        // Initialize a modular drivetrain on top of the differentialDrivetrain
        mModularDrivetrain = ModularDrivetrain.from(differentialDrivetrain);

        mVoltageControlModule = new VoltageControlModule(DriveConstants.kNormalVoltage);

        mPowerEfficiencyModule = new PowerEfficiencyModule(DriveConstants.kSecondsToReachFullSpeed, DriveConstants.kTurnThreshold);

        // TODO: Calibrate this
        mTargetAlignmentModule = new TargetAlignmentModule(vision.getTargetingCamera(),
             new PID(0.12, 0.05, 0.005), new PID(0, 0, 0), false);

        mModularDrivetrain.setModules(mTargetAlignmentModule, mVoltageControlModule, mPowerEfficiencyModule);
    }
    public void arcadeDrive(double forwardSpeed, double turnSpeed) {
        // TODO: Extract turn deadband constant
        var turn = GMath.deadband(turnSpeed, 0.2) * 0.75;
        // The forward speed is inverted here because if the joystick
        // is pushed forward, the values will be negative, whereas
        // negative speed values will mvoe the robot backwards.
        mModularDrivetrain.arcade(-forwardSpeed, turn);
    }

    /**
     * Stop all motors on the drivetrain.
     */
    public void stop() {
        mModularDrivetrain.stop();
    }

    /**
     * Decrease the drivetrain's power by decreasing the maximum voltage.  
     */
    public void disableTurbo() {
        mVoltageControlModule.setMaxVoltage(DriveConstants.kNormalVoltage);
    }

    /**
     * Increase the drivetrain's power by increasing the maximum voltage.  
     */
    public void enableTurbo() {
        mVoltageControlModule.setMaxVoltage(DriveConstants.kFastVoltage);
    }


    /**
     * Get the modular drivetrain
     * @return The modular drivetrain that controls the differential drivetrain
     */
    public ModularDrivetrain getModularDrivetrain() {
        return mModularDrivetrain;
    }

    /**
     * Get the distance travelled by the robot.
     * @return The distance travelled by the robot 
     */
    public double getDistance() {
        return new AverageEncoderSensor(mLeftEncoderSensor, mRightEncoderSensor).getDistance();
    }

    /**
     * Get the speed of the drivetrain wheels.
     * @return The speeds of both sides of the drivetrain in meters per second
     */
    public DifferentialDriveWheelSpeeds getSpeeds() {
        return new DifferentialDriveWheelSpeeds(
            // TODO: getVelocity() should return a velocity in meters per second
            // At the moment, getVelocity() is returning in feet per minute
            Units.feetToMeters(mLeftEncoderSensor.getVelocity()) * DriveConstants.kGearRatio * 2 * Math.PI * Units.inchesToMeters(DriveConstants.kWheelRadius) / 60,
            Units.feetToMeters(mRightEncoderSensor.getVelocity()) * DriveConstants.kGearRatio * 2 * Math.PI * Units.inchesToMeters(DriveConstants.kWheelRadius) / 60
        );
    }

    /**
     * Provide user feedback whenever the drivetrain is updated.
     */
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left distance (ft)", mLeftEncoderSensor.getDistance());
        SmartDashboard.putNumber("Right distance (ft)", mRightEncoderSensor.getDistance());
        SmartDashboard.putNumber("Left speed (ft)", mLeftEncoderSensor.getVelocity());
        SmartDashboard.putNumber("Right speed (ft)", mRightEncoderSensor.getVelocity());
    }

}
