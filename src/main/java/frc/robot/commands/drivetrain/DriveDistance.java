package frc.robot.commands.drivetrain;

import com.thegongoliers.commands.FollowPathCommand;
import com.thegongoliers.paths.SimplePath;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.DrivetrainSubsystem;

/*
 * Drives forward for the specified distance.
 */
public class DriveDistance extends SequentialCommandGroup {

    public DriveDistance(DrivetrainSubsystem drivetrain, double distance) {

        SimplePath path = new SimplePath();
        path.addStraightAway(distance);
        addCommands(new FollowPathCommand(drivetrain, drivetrain.getModularDrivetrain(), path));

    }

}
