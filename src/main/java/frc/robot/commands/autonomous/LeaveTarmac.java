package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.subsystems.DrivetrainSubsystem;

public class LeaveTarmac extends SequentialCommandGroup {
    public LeaveTarmac(DrivetrainSubsystem drivetrain){
        addCommands(
            new DriveDistance(drivetrain, Constants.AutoConstants.kAutoDriveDistanceFeet)
        );
    }
}
