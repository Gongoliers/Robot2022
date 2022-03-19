package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.shooter.ShootHigh;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class BackupAndShoot extends SequentialCommandGroup {
    public BackupAndShoot(DrivetrainSubsystem drivetrain, ShooterSubsystem shooter){
        addCommands(
            new DriveDistance(drivetrain, (double) AutoConstants.kDistanceToDriveForHigh).withTimeout(1.5), // AFTER TESTING IT SEEMS OPTIMAL LOCATION FOR SHOOTING FOR HIGH IS 20.5 INCHES
            new ShootHigh(shooter).withTimeout(8.0)
        );
    }
}
