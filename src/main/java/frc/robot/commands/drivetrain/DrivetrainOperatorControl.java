package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

/**
 * Allows the driver to control movement using a joystick / controller.
 */
public class DrivetrainOperatorControl extends CommandBase {

    private DrivetrainSubsystem m_drivetrain;

    public DrivetrainOperatorControl(DrivetrainSubsystem drivetrain) {

        addRequirements(drivetrain);
        m_drivetrain = drivetrain;

    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        double speed = -Robot.oi.getDriverSpeed();
        double rotation = Robot.oi.getDriverRotation();

        m_drivetrain.arcadeDrive(speed, rotation);
    }

    // Make this return true when this Command no longer needs to run execute
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_drivetrain.stop();
    }

}
