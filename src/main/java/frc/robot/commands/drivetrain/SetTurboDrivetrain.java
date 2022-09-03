package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;

/**
 * Sets the drivetrain to either turbo or precision mode.
 */
public class SetTurboDrivetrain extends InstantCommand {

    private Drivetrain mDrivetrain;
    private boolean mEnabled;

    public SetTurboDrivetrain(Drivetrain drivetrain, boolean enabled) {
        addRequirements(drivetrain);
        mDrivetrain = drivetrain;

        mEnabled = enabled;
    }

    // Called once when this InstantCommand executes
    @Override
    public void initialize() {
        if (mEnabled) {
            mDrivetrain.enableTurbo();
        } else {
            mDrivetrain.disableTurbo();
        }
    }

}
