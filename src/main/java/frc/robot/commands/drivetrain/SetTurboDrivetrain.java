package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;

/**
 * Sets the drivetrain to either turbo or precision mode.
 */
public class SetTurboDrivetrain extends InstantCommand {

    private boolean enabled;

    public SetTurboDrivetrain(boolean enabled) {

        addRequirement(Robot.drivetrain); // FIXME: Robot.drivetrain is not a subsystem
        this.enabled = enabled;

    }

    // Called once when this InstantCommand executes
    @Override
    public void initialize() {
        Robot.drivetrain.setTurboEnabled(enabled);
    }

}
