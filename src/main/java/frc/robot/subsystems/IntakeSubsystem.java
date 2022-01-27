package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;;

public class IntakeSubsystem extends SubsystemBase {
    private WPI_TalonSRX m_intakeMotor;

    public IntakeSubsystem() {
        m_intakeMotor = new WPI_TalonSRX(IntakeConstants.kIntakePWM);
        m_intakeMotor.setInverted(false);
    }

    @Override
    public void periodic() {
        //smartdashboard
    }

    public void stopIntake() {
        m_intakeMotor.stopMotor();
    }

    public void cancelMove() {
        //todo implement
    }

    public void stopAll() {
        stopIntake();
        cancelMove();
    }

    public void intake() {
        m_intakeMotor.set(IntakeConstants.kIntakePWM);
    }

    public void outtake() {
        m_intakeMotor.set(-IntakeConstants.kIntakeSpeed);        
    }

    public void deploy() {
        //TODO: placeholder
    }

    public void retract() {
        //TODO: placeholder
    }

}
