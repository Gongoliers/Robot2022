package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.commands.drivetrain.StopDrivetrain;
import frc.robot.commands.endgame.StopEndgame;
import frc.robot.commands.intake.StopIntakeSystem;
import frc.robot.commands.shooter.StopShooter;

public class StopAll extends ParallelCommandGroup {
	
	public StopAll(DrivetrainSubsystem drivetrain, EndgameSubsystem endgame, ShooterSubsystem shooter, IntakeSubsystem intake) {
		
		addCommands(new StopDrivetrain(drivetrain),
					new StopEndgame(endgame),
					new StopShooter(shooter),
					new StopIntakeSystem(intake)
					);

	}

}
