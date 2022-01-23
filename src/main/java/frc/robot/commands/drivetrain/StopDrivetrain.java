package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Robot;

/**
 * Stops the motors of the drivetrain.
 */
public class StopDrivetrain extends InstantCommand {

    public StopDrivetrain() {

        addRequirement(Robot.drivetrain); // FIXME: Robot.drivetrain is not a subsystem

    }

    // Called once this InstantCommand runs
    @Override
    public void initialize() {
        Robot.drivetrain.stop();
    }

}
