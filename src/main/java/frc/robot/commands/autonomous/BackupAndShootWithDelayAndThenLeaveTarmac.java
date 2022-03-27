package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.AutoConstants;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.drivetrain.SimpleDriveDistance;
import frc.robot.commands.shooter.ShootHigh;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class BackupAndShootWithDelayAndThenLeaveTarmac extends SequentialCommandGroup {
    public BackupAndShootWithDelayAndThenLeaveTarmac(DrivetrainSubsystem drivetrain, ShooterSubsystem shooter){
        addCommands(
                new WaitCommand(2.0),
                new SimpleDriveDistance(drivetrain, AutoConstants.kDistanceToDriveForHigh, 0.05, 0.3).withTimeout(3.0),
                new ShootHigh(shooter).withTimeout(8.0),
                new WaitCommand(2.0),
                new Drive(drivetrain, -Constants.AutoConstants.kAutoDriveToFenderSpeed).withTimeout(Constants.AutoConstants.kAutoDriveToFenderSeconds)
        );
    }
}
