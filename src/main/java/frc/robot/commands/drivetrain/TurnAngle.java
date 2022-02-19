package frc.robot.commands.drivetrain;

import com.thegongoliers.commands.FollowPathCommand;
import com.thegongoliers.paths.SimplePath;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.DrivetrainSubsystem;

/*
 * Turn the specified angle.
 */
public class TurnAngle extends SequentialCommandGroup {

    public TurnAngle(DrivetrainSubsystem drivetrain, double angle) {

        SimplePath path = new SimplePath();
        path.addRotation(angle);
        addCommands(new FollowPathCommand(drivetrain, drivetrain.getModularDrivetrain(), path));

    }

}
