package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.shooter.ShootLow;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class BackupAndShootLowThenLeaveTarmac extends SequentialCommandGroup {
    public BackupAndShootLowThenLeaveTarmac(DrivetrainSubsystem drivetrain, ShooterSubsystem shooter){
        addCommands(
                new WaitCommand(2.0),
                new ShootLow(shooter).withTimeout(8.0),
                new WaitCommand(1.5),
                new Drive(drivetrain, -AutoConstants.kAutoDriveToFenderSpeed).withTimeout(AutoConstants.kAutoDriveToFenderSeconds)
        );
    }
}
