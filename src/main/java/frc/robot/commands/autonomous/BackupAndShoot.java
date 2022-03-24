package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.commands.drivetrain.SimpleDriveDistance;
import frc.robot.commands.shooter.ShootHigh;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class BackupAndShoot extends SequentialCommandGroup {
    public BackupAndShoot(DrivetrainSubsystem drivetrain, ShooterSubsystem shooter){
        addCommands(
                new SimpleDriveDistance(drivetrain, AutoConstants.kDistanceToDriveForHigh, 0.05, 0.3).withTimeout(3.0),
                new ShootHigh(shooter).withTimeout(8.0)
        );
    }
}
