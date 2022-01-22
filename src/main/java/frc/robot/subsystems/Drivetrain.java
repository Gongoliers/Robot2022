package frc.robot.subsystems;

import frc.robot.NavxGyro;
import frc.robot.RobotMap;
import frc.robot.commands.drivetrain.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;
import com.kylecorry.pid.PID;
import com.thegongoliers.output.drivetrain.ModularDrivetrain;
import com.thegongoliers.output.drivetrain.PathFollowerModule;
import com.thegongoliers.output.drivetrain.PowerEfficiencyModule;
import com.thegongoliers.output.drivetrain.StabilityModule;
import com.thegongoliers.output.drivetrain.TargetAlignmentModule;
import com.thegongoliers.output.drivetrain.VoltageControlModule;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonFX;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Our drivetrain is composed of a 6-CIM (3 per side) West Coast drop-center
 * drivetrain. We use an encoder for each side and a NavX for an IMU/gyro.
 */
public class Drivetrain extends SubsystemBase {

    public static final double NORMAL_VOLTAGE = 10.5;
    public static final double SLOW_VOLTAGE = 5.25;

    private TalonFX leftDriveController;
    private TalonFX rightDriveController;
    private Encoder leftDriveEncoder;
    private Encoder rightDriveEncoder;

    public AHRS navx = new AHRS(SerialPort.Port.kMXP);

    private ModularDrivetrain modularDrivetrain;
    private VoltageControlModule voltageControlModule;
    private StabilityModule stabilityModule;

    private boolean turboEnabled = false;
    private Map<SubsystemBase, Double> enforcedMaxVoltages = new HashMap<>();

    public Drivetrain(Vision vision) {
        leftDriveController = new TalonFX(RobotMap.LEFT_DRIVE_PWM);
        leftDriveController.setInverted(false);

        rightDriveController = new TalonFX(RobotMap.RIGHT_DRIVE_PWM);
        rightDriveController.setInverted(false);

        leftDriveEncoder = new Encoder(RobotMap.LEFT_DRIVE_ENCODER_A, RobotMap.LEFT_DRIVE_ENCODER_B);
        leftDriveEncoder.setDistancePerPulse(1.0);

        rightDriveEncoder = new Encoder(RobotMap.RIGHT_DRIVE_ENCODER_A, RobotMap.RIGHT_DRIVE_ENCODER_B);
        rightDriveEncoder.setDistancePerPulse(1.0);

        DifferentialDrive differentialDrive = new DifferentialDrive(leftDriveController, rightDriveController);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);
        differentialDrive.setDeadband(0.05);

        modularDrivetrain = ModularDrivetrain.from(differentialDrive);
        Gyro gyro = new NavxGyro(navx);

        stabilityModule = new StabilityModule(gyro, 0.05, 0.25);
        stabilityModule.setTurnThreshold(0.075);

        PathFollowerModule pathFollowerModule = new PathFollowerModule(gyro,
                List.of(leftDriveEncoder, rightDriveEncoder), 0.5, 0.02);  // TODO: tune
        pathFollowerModule.setForwardTolerance(0.5); // 1/2 foot
        pathFollowerModule.setTurnTolerance(1); // 1 degree

        // ***************************************************** TODO: THIS IS UNTUNED
        // OLD: new PID(0.12, 0.04, 0.005)
        TargetAlignmentModule targetAlignmentModule = new TargetAlignmentModule(vision.getTargetingCamera(),
                new PID(0.12, 0.05, 0.005), new PID(0, 0, 0), false); // TODO: tune

        voltageControlModule = new VoltageControlModule(NORMAL_VOLTAGE);

        PowerEfficiencyModule powerEfficiencyModule = new PowerEfficiencyModule(0.25, 0.2);

        modularDrivetrain.setModules(stabilityModule, pathFollowerModule, targetAlignmentModule, voltageControlModule,
                powerEfficiencyModule);

    }

    public void resetStabilityModule() {
        stabilityModule.reset();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new DrivetrainOperatorContol());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        SmartDashboard.putNumber("Heading", navx.getAngle());
        SmartDashboard.putNumber("Distance", (leftDriveEncoder.getDistance() + rightDriveEncoder.getDistance()) / 2);
        SmartDashboard.putNumber("Speed", (leftDriveEncoder.getRate() + rightDriveEncoder.getRate()) / 2);
    }

    public void arcadeDrive(double forwardSpeed, double turnSpeed) {
        modularDrivetrain.arcade(-forwardSpeed, turnSpeed);
    }

    public void stop() {
        modularDrivetrain.stop();
    }

    public ModularDrivetrain getModularDrivetrain() {
        return modularDrivetrain;
    }

    /**
     * This function can also be known as "turtle mode", as when this function is
     * disabled (it is enabled by default), the robot will drastically slow down.
     * This will be used for apporaching the control panel on the field, or
     * precisely position the robot.
     * 
     * Turbo (true) = full speed Turtle (false) = super slow
     */
    public void setTurboEnabled(boolean turboEnabled) {
        this.turboEnabled = turboEnabled;
        if (turboEnabled) {
            voltageControlModule.setMaxVoltage(getMaxVoltage());
        } else {
            voltageControlModule.setMaxVoltage(getMaxVoltage() / 2);
        }
    }

    public void addEnforcedMaxVoltage(Subsystem requester, double maxVoltage) {
        enforcedMaxVoltages.put(requester, maxVoltage);
        setTurboEnabled(turboEnabled);
    }

    public void removeEnforcedMaxVoltage(Subsystem requester) {
        enforcedMaxVoltages.remove(requester);
        setTurboEnabled(turboEnabled);
    }

    private double getMaxVoltage() {
        return enforcedMaxVoltages.values().stream().min(Double::compareTo).orElse(NORMAL_VOLTAGE);
    }

}
