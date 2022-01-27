package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.thegongoliers.output.actuators.GSpeedController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    // Initializing the motors
    private WPI_TalonSRX m_feederMotor;
    private WPI_TalonSRX m_shooterMotor;

    // Creating a speed controller
    private GSpeedController m_shooterController;
    
    // Initializing the Encoder
    private Encoder m_shooterEncoder; 

    public ShooterSubsystem() {
        m_feederMotor = new WPI_TalonSRX(ShooterConstants.kFeederMotorPWM);

    }

    @Override
    public void periodic() {}

    @Override
    public void simulationPeriodic() {}
}
