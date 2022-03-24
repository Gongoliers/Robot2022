package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

/*
 * Drives forward for the specified distance.
 */
public class SimpleDriveDistance extends CommandBase {

    private final DrivetrainSubsystem m_drivetrain;
    private final double m_distance;
    private final double m_threshold;
    private final double m_speed;
    private double m_last_distance;

    public SimpleDriveDistance(DrivetrainSubsystem drivetrain, double distance, double threshold, double speed) {
        addRequirements(drivetrain);
        m_drivetrain = drivetrain;
        m_distance = distance;
        m_threshold = threshold;
        m_speed = speed;
    }

    @Override
    public void initialize() {
        m_last_distance = m_drivetrain.getDistance();
    }

    @Override
    public void execute() {
        if (isFinished()){
            m_drivetrain.stop();
        } else if (getError() > 0) {
            m_drivetrain.arcadeDrive(m_speed, 0.0);
        } else {
            m_drivetrain.arcadeDrive(-m_speed, 0.0);
        }
    }

    @Override
    public boolean isFinished() {
        return Math.abs(getError()) <= m_threshold;
    }

    @Override
    public void end(boolean interrupted) {
        m_drivetrain.stop();
    }

    private double getError() {
        return m_drivetrain.getDistance() - m_last_distance - m_distance;
    }
}
