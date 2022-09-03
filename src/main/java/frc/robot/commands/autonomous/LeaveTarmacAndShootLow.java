package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.Constants.AutoConstants;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.intake.RunIntake;
import frc.robot.commands.shooter.ShootHigh;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class LeaveTarmacAndShootLow extends SequentialCommandGroup {
    public LeaveTarmacAndShootLow(Drivetrain drivetrain, Shooter shooter, Intake intake){
        addCommands(
            new DeployIntake(intake),
            parallel(
                new RunIntake(intake),
                new LeaveTarmac(drivetrain)
            ).withTimeout(Constants.AutoConstants.kAutoDriveAwayFromFenderSeconds),
            new Drive(drivetrain, Constants.AutoConstants.kAutoDriveToFenderSpeed).withTimeout(Constants.AutoConstants.kAutoDriveToFenderSeconds),
            new ShootHigh(shooter).withTimeout(AutoConstants.kShootHighTime)
        );
    }
}
