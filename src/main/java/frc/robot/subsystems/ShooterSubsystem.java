package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.output.actuators.GSpeedController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PhoenixMotorControllerEncoder;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    // Initializing the motors
    private WPI_TalonSRX m_feederMotor;
    private WPI_TalonSRX m_shooterMotor;

    // Creating a speed controller
    // private GSpeedController m_shooterController;
    
    // Initializing the Encoder
    private EncoderSensor m_shooterEncoder; 

    public ShooterSubsystem() {
        m_feederMotor = new WPI_TalonSRX(ShooterConstants.kFeederMotorPWM);

        m_shooterMotor = new WPI_TalonSRX(ShooterConstants.kShooterMotorPWM);
        m_shooterEncoder = new PhoenixMotorControllerEncoder(m_shooterMotor, FeedbackDevice.CTRE_MagEncoder_Relative);

    }
    
    private void rampUpTime(double speed, double time, WPI_TalonSRX motor) {
        double timeStep = 0.001;
        double currentTime = timeStep;
        double speedStep = speed/time;
        while (currentTime != time) {
            motor.set(speedStep*currentTime);
            currentTime += timeStep;
            // TODO: CHECK THIS SLEEP (sketchy)
            try {
                Thread.sleep((long) (timeStep * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void rampUpIntervals(double speed, double intervals, WPI_TalonSRX motor) {
        int current_interval = 0;
        double step = speed/intervals;
        while (current_interval != intervals) {
            motor.set(step*current_interval);
            while (m_shooterEncoder.getVelocity() <= step*current_interval) {}
            current_interval += 1;
        }
    }
    
    @Override
    public void periodic() {}

    public void stopShooter() {
        m_shooterMotor.stopMotor();
    }

    public void stopFeeder() {
        m_feederMotor.stopMotor();
    }

    public void stopAll() {
        stopShooter();
        stopFeeder();
    }

    public void shootHigh() {
        // TODO: DECIDE & TUNE
        double speed = 0.5;
        rampUpIntervals(speed, 10, m_shooterMotor);
        // rampUpTime(0.5, 0.1)
        feed(speed);
    }

    public void shootLow() {
        // TODO: DECIDE & TUNE
        double speed = 0.5;
        // rampUpIntervals(0.5, 10, m_shooterMotor);
        rampUpTime(speed, 0.1, m_shooterMotor);
        feed(speed);
    }
    
    /**
     * So far, this is the only function I have written a description for, but for a good reason
     * @param flywheel_speed This is NOT the feeder speed, this is the TARGET SPEED for the flywheel
     */
    public void feed(double flywheel_speed) {
        while (!isFlyWheelReady(flywheel_speed));
        m_feederMotor.set(ShooterConstants.kFeederMotorSpeed);
    }


    public boolean isFlyWheelReady(double speed) {
        return m_shooterEncoder.getVelocity() >= speed;
    }

}