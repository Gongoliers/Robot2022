package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.commands.drivetrain.Drive;
import frc.robot.commands.shooter.ShootLow;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class ShootLowThenLeaveTarmac extends SequentialCommandGroup {
  public ShootLowThenLeaveTarmac(Drivetrain drivetrain, Shooter shooter) {
    addCommands(
        new WaitCommand(2.0),
        new Drive(drivetrain, AutoConstants.kAutoDriveToFenderSpeed).withTimeout(1.0),
        new ShootLow(shooter).withTimeout(AutoConstants.kShootLowTime),
        new WaitCommand(1.5),
        new Drive(drivetrain, -AutoConstants.kAutoDriveToFenderSpeed)
            .withTimeout(AutoConstants.kAutoDriveToFenderSeconds));
  }
}
