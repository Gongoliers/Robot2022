package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.subsystems.DrivetrainSubsystem;

public class LeaveTarmac extends SequentialCommandGroup {
    public LeaveTarmac(DrivetrainSubsystem drivetrain){
        addCommands(
            new Drive(drivetrain, Constants.AutoConstants.kAutoDriveAwayFromFenderSpeed).withTimeout(Constants.AutoConstants.kAutoDriveAwayFromFenderSeconds)
        );
    }
}
