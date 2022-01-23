package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

/**
 * Stops the motors of the drivetrain.
 */
public class StopDrivetrain extends InstantCommand {

    private DrivetrainSubsystem m_drivetrain;

    public StopDrivetrain(DrivetrainSubsystem drivetrain) {

        addRequirements(drivetrain);
        m_drivetrain = drivetrain;

    }

    // Called once this InstantCommand runs
    @Override
    public void initialize() {
        m_drivetrain.stop(); // FIXME: Use the new, correct way to stop the drivetrain
    }

}
