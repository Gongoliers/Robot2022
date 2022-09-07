package frc.robot.subsystems.endgame;

import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.output.actuators.GSpeedController;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;
import frc.robot.InvertableLimitSwitch;

public class EndgameArm {

  private final GSpeedController m_motor;
  private final EncoderSensor m_encoder;
  private final InvertableLimitSwitch m_limitSwitch;
  private final Solenoid m_armLockController;
  private EndgameControl m_control;

  private double m_descendSpeed, m_ascendSpeed;

  /**
   * This is the basic endgame arm used in the 2022 game
   *
   * @param motor
   * @param encoder
   * @param limitSwitch
   * @param locker
   */
  public EndgameArm(
      GSpeedController motor,
      EncoderSensor encoder,
      InvertableLimitSwitch limitSwitch,
      Solenoid locker) {
    motor.useVoltageControl(12.0);
    m_motor = motor;
    m_encoder = encoder;
    m_limitSwitch = limitSwitch;
    m_armLockController = locker;
    m_control = new LockedEndgameControl(this);
    m_descendSpeed = 0.0;
    m_ascendSpeed = 0.0;
  }

  public void run(EndgameAction action) {
    m_control = m_control.run(action);
  }

  public void setAscendSpeed(double speed) {
    m_ascendSpeed = Math.abs(speed);
  }

  public void setDescendSpeed(double speed) {
    m_descendSpeed = -Math.abs(speed);
  }

  void unlock() {
    m_armLockController.set(true);
  }

  boolean isLocked() {
    return !m_armLockController.get();
  }

  boolean isCapped() {
    return m_encoder.getDistance() >= Constants.EndgameConstants.kTopDistance;
  }

  boolean isFloored() {
    return m_limitSwitch.isTriggered();
  }

  void calibrate() {
    m_encoder.reset();
  }

  void lock() {
    m_armLockController.set(false);
  }

  void stop() {
    m_motor.stopMotor();
  }

  void ascend() {
    m_motor.set(m_ascendSpeed);
  }

  void descend() {
    m_motor.set(m_descendSpeed);
  }
}
