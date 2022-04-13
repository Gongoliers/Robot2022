package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.subsystems.DrivetrainSubsystem;

public class LeaveTarmac extends SequentialCommandGroup {

    public LeaveTarmac(DrivetrainSubsystem drivetrain, double waitTime) {
        addCommands(
                new WaitCommand(waitTime),
                getDriveCommand(drivetrain)
        );
    }

    public LeaveTarmac(DrivetrainSubsystem drivetrain) {
        addCommands(getDriveCommand(drivetrain));
    }

    private Command getDriveCommand(DrivetrainSubsystem drivetrain) {
        return new Drive(drivetrain, Constants.AutoConstants.kAutoDriveAwayFromFenderSpeed).withTimeout(Constants.AutoConstants.kAutoDriveAwayFromFenderSeconds);
    }
}
