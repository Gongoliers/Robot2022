package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.Constants.AutoConstants;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.drivetrain.SimpleDriveDistance;
import frc.robot.commands.shooter.ShootHigh;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class BackupAndShootHighThenLeaveTarmac extends SequentialCommandGroup {
  public BackupAndShootHighThenLeaveTarmac(Drivetrain drivetrain, Shooter shooter) {
    addCommands(
        new WaitCommand(2.0),
        new SimpleDriveDistance(drivetrain, AutoConstants.kDistanceToDriveForHigh, 0.05, 0.3)
            .withTimeout(3.0),
        new ShootHigh(shooter).withTimeout(AutoConstants.kShootHighTime),
        new WaitCommand(1.5),
        new Drive(drivetrain, -Constants.AutoConstants.kAutoDriveToFenderSpeed)
            .withTimeout(Constants.AutoConstants.kAutoDriveToFenderSeconds));
  }
}
