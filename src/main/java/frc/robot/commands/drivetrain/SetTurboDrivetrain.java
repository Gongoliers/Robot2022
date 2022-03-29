package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

/**
 * Sets the drivetrain to either turbo or precision mode.
 */
public class SetTurboDrivetrain extends InstantCommand {

    private DrivetrainSubsystem m_drivetrain;
    private boolean m_enabled;

    public SetTurboDrivetrain(DrivetrainSubsystem drivetrain, boolean enabled) {

        m_drivetrain = drivetrain;
        m_enabled = enabled;

    }

    // Called once when this InstantCommand executes
    @Override
    public void initialize() {
        m_drivetrain.setTurboEnabled(m_enabled);
    }

}
