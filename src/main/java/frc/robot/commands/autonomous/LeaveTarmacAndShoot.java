package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.shooter.Shoot;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class LeaveTarmacAndShoot extends SequentialCommandGroup {
    public LeaveTarmacAndShoot(DrivetrainSubsystem drivetrain, ShooterSubsystem shooter){
        addCommands(
            new LeaveTarmac(drivetrain),
            new Drive(drivetrain, Constants.AutoConstants.kAutoDriveToFenderSpeed).withTimeout(Constants.AutoConstants.kAutoDriveToFenderSeconds),
            new Shoot(shooter).withTimeout(8.0)
        );
    }
}
