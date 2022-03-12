package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.thegongoliers.output.actuators.GPiston;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    private WPI_TalonSRX m_intakeMotor;
    private final Solenoid m_intakeSolenoid;
    private final GPiston m_intakePiston;

    public IntakeSubsystem() {
        
        m_intakeMotor = new WPI_TalonSRX(IntakeConstants.kIntakeCanId);
        m_intakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.kSolenoidCAN);
        m_intakePiston = new GPiston(m_intakeSolenoid);

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
        m_intakePiston.extend();
    }

    public void retract() {
        m_intakePiston.retract();
    }

    public boolean isDeployed() {
        return m_intakePiston.isExtended();
    }

}
