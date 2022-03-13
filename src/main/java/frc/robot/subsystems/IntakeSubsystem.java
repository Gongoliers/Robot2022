package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.thegongoliers.output.actuators.GPiston;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    private MotorController m_intakeMotor;
    private final Solenoid m_intakeSolenoid1;
    private final GPiston m_intakePiston1;

    private final Solenoid m_intakeSolenoid2;
    private final GPiston m_intakePiston2;

    public IntakeSubsystem() {
    
        m_intakeMotor = new WPI_TalonSRX(IntakeConstants.kIntakeCanId);

        m_intakeSolenoid1 = new Solenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.kSolenoidCAN1);
        m_intakePiston1 = new GPiston(m_intakeSolenoid1);

        m_intakeSolenoid2 = new Solenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.kSolenoidCAN2);
        m_intakePiston2 = new GPiston(m_intakeSolenoid2);

    }

    @Override
    public void periodic() {
        // TODO: smartdashboard
    }

    public void stopIntake() {
        m_intakeMotor.stopMotor();
        retract();
    }

    public void stopAll() {
        stopIntake();
        retract();
    }

    public void intake() {
        m_intakeMotor.set(IntakeConstants.kIntakeSpeed);
    }

    public void outtake() {
        m_intakeMotor.set(-IntakeConstants.kIntakeSpeed);       
    }

    public void deploy() {
        m_intakePiston1.extend();
        m_intakePiston2.extend();
    }

    public void retract() {
        m_intakePiston1.retract();
        m_intakePiston2.retract();
    }

    public boolean isDeployed() {
        return (m_intakePiston1.isExtended() && m_intakePiston2.isExtended());
    }

}
