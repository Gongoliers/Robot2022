package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.shooter.Shoot;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class BackupFootAndShoot extends SequentialCommandGroup {
    public BackupFootAndShoot(DrivetrainSubsystem drivetrain, ShooterSubsystem shooter){
        addCommands(
            new DriveDistance(drivetrain, (double) 1.0),
            new Shoot(shooter).withTimeout(8.0)
        );
    }
}
