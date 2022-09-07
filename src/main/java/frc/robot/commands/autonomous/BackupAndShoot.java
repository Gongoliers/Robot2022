package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutoConstants;
import frc.robot.commands.drivetrain.SimpleDriveDistance;
import frc.robot.commands.shooter.ShootHigh;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class BackupAndShoot extends SequentialCommandGroup {
  public BackupAndShoot(Drivetrain drivetrain, Shooter shooter) {
    addCommands(
        new SimpleDriveDistance(drivetrain, AutoConstants.kDistanceToDriveForHigh, 0.05, 0.3)
            .withTimeout(3.0),
        new ShootHigh(shooter).withTimeout(AutoConstants.kShootHighTime));
  }
}
