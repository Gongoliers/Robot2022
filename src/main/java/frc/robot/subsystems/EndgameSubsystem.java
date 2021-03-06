package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kylecorry.pid.PID;
import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.output.actuators.GSpeedController;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.InvertableLimitSwitch;
import frc.robot.subsystems.endgame.EndgameAction;
import frc.robot.subsystems.endgame.EndgameArm;
import frc.robot.PhoenixMotorControllerEncoder;
import frc.robot.Constants.EndgameConstants;

public class EndgameSubsystem extends SubsystemBase {

    // Initializing Pneumatics
    private final Solenoid m_armUnlockControllerA = new Solenoid(PneumaticsModuleType.CTREPCM, EndgameConstants.kSolenoidACAN);
    private final Solenoid m_armUnlockControllerB = m_armUnlockControllerA;//new Solenoid(PneumaticsModuleType.CTREPCM, EndgameConstants.kSolenoidBCAN);

    // Left Motor (when standing behind battery)
    private final WPI_TalonFX m_motorA = new WPI_TalonFX(EndgameConstants.kMotorACAN);
    private EncoderSensor m_encoderA = new PhoenixMotorControllerEncoder(m_motorA, FeedbackDevice.IntegratedSensor)
            .scaledBy(EndgameConstants.kCappedDistanceA);
    private InvertableLimitSwitch m_limitSwitchA = new InvertableLimitSwitch(EndgameConstants.kLimitSwitchAPort);
    /**
     * Drive Arms Up x desired feet (important record distance on both smartdashboard and mark arm w sharpie TICKS * FEET/TICK)(use other program)
     * Set caluclated value to top distance (half to not overdrive)
     * <p>
     * Oscilating = on one test
     * <p>
     * 1. Change EndgameConstants.kTopFeet to tuned ^^
     * 2. Change P Value to reach mark marked on endgame arm consistantly
     * 2a. It's better for the endgame arm to hit the mark and continue around it than to not reach mark
     * 3. Change D value (start at 0.02) until the EndgameArm stops moving around that point. This does not matter
     * if it stops directly on that point (just need arm to stop moving around)
     * 4. If D does not make arm stop at line, use I term (middle) (start at 0.01) (always positive tune until arm
     * reaches point)
     */
    private GSpeedController m_controllerA = new GSpeedController(m_motorA, m_encoderA, new PID(0.5, 0.0, 0.0), new PID(0.0, 0.0, 0.0));
    private final EndgameArm m_endgameA = new EndgameArm(m_controllerA, m_encoderA, m_limitSwitchA, m_armUnlockControllerA);

    // Right Motor (when standing behind battery)
    private final WPI_TalonFX m_motorB = new WPI_TalonFX(EndgameConstants.kMotorBCAN);
    private EncoderSensor m_encoderB = new PhoenixMotorControllerEncoder(m_motorB, FeedbackDevice.IntegratedSensor)
            .scaledBy(EndgameConstants.kCappedDistanceB);
    private InvertableLimitSwitch m_limitSwitchB = new InvertableLimitSwitch(EndgameConstants.kLimitSwitchBPort);
    private GSpeedController m_controllerB = new GSpeedController(m_motorB, m_encoderB, new PID(0.5, 0.0, 0.0), new PID(0.0, 0.0, 0.0));
    private final EndgameArm m_endgameB = new EndgameArm(m_controllerB, m_encoderB, m_limitSwitchB, m_armUnlockControllerB);

    public EndgameSubsystem() {
        m_encoderA.reset();
        m_encoderB.reset();

        m_motorA.setNeutralMode(NeutralMode.Brake);
        m_motorB.setNeutralMode(NeutralMode.Brake);

        m_limitSwitchA.setInverted(true);
        m_limitSwitchB.setInverted(true);

        initSpeeds();
    }

    void initSpeeds() {
        m_endgameA.setAscendSpeed(EndgameConstants.kAscendMotorSpeedA);
        m_endgameA.setDescendSpeed(EndgameConstants.kDescendMotorSpeedA);
        m_endgameB.setAscendSpeed(EndgameConstants.kAscendMotorSpeedB);
        m_endgameB.setDescendSpeed(EndgameConstants.kDescendMotorSpeedB);
    }

    public void run(EndgameAction action) {
        initSpeeds();
        m_endgameA.run(action);
        m_endgameB.run(action);
    }

    public void HomeEndgame() {
        m_endgameA.setDescendSpeed(EndgameConstants.kHomingSpeed);
        m_endgameB.setDescendSpeed(EndgameConstants.kHomingSpeed);
        m_endgameA.run(EndgameAction.Descend);
        m_endgameB.run(EndgameAction.Descend);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Endgame A", m_encoderA.getDistance());
        SmartDashboard.putNumber("Endgame B", m_encoderB.getDistance());
        SmartDashboard.putBoolean("Endgame A Btm", m_limitSwitchA.isTriggered());
        SmartDashboard.putBoolean("Endgame B Btm", m_limitSwitchB.isTriggered());
    }

}
