package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.compressor.StartLimitedCompressor;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.intake.Intake;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class LeaveTarmacAndShoot extends SequentialCommandGroup {
    public LeaveTarmacAndShoot(DrivetrainSubsystem drivetrain, ShooterSubsystem shooter, IntakeSubsystem intake){
        addCommands(
            new DeployIntake(intake),
            parallel(
                new Intake(intake),
                new LeaveTarmac(drivetrain)
            ).withTimeout(Constants.AutoConstants.kAutoDriveAwayFromFenderSeconds),
            new Drive(drivetrain, Constants.AutoConstants.kAutoDriveToFenderSpeed).withTimeout(Constants.AutoConstants.kAutoDriveToFenderSeconds),
            new BackupAndShoot(drivetrain, shooter).withTimeout(8.0)
        );
    }
}
