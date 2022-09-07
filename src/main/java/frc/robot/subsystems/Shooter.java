package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {

  private final WPI_TalonSRX mInjectorMotor;
  private final WPI_TalonSRX mFeederMotor;
  private final WPI_TalonSRX mOuttakeMotor;

  public Shooter() {
    mInjectorMotor = new WPI_TalonSRX(ShooterConstants.kInjectorMotorCANId);
    mFeederMotor = new WPI_TalonSRX(ShooterConstants.kFeederMotorCANId);
    mOuttakeMotor = new WPI_TalonSRX(ShooterConstants.kOuttakeMotorCANId);

    mInjectorMotor.setInverted(true);
    mOuttakeMotor.setInverted(true);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Injector speed (%)", mInjectorMotor.get());
    SmartDashboard.putNumber("Feeder speed (%)", mFeederMotor.get());
    SmartDashboard.putNumber("Outtake speed (%)", mInjectorMotor.get());
  }

  public void injectBall() {
    mInjectorMotor.set(ShooterConstants.kInjectorMotorSpeed);
  }

  public void spinForLow() {
    mFeederMotor.set(ShooterConstants.kFeederMotorSpeedLow);
    mOuttakeMotor.set(ShooterConstants.kOuttakeMotorSpeedLow);
  }

  public void spinForHigh() {
    mFeederMotor.set(ShooterConstants.kFeederMotorSpeedHigh);
    mOuttakeMotor.set(ShooterConstants.kOuttakeMotorSpeedHigh);
  }

  public void stopInjectorMotor() {
    mInjectorMotor.stopMotor();
  }

  public void stopFeederMotor() {
    mFeederMotor.stopMotor();
  }

  public void stopOuttakeMotor() {
    mOuttakeMotor.stopMotor();
  }

  public void stop() {
    stopFeederMotor();
    stopOuttakeMotor();
    stopInjectorMotor();
  }
}
