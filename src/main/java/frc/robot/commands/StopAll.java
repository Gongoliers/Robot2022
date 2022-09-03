package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.endgame.StopEndgame;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.drivetrain.StopDrivetrain;
import frc.robot.commands.intake.StopIntakeSystem;
import frc.robot.commands.shooter.StopShooter;

public class StopAll extends ParallelCommandGroup {
	
	public StopAll(Drivetrain drivetrain, EndgameSubsystem endgame, Shooter shooter, Intake intake) {
		
		addCommands(new StopDrivetrain(drivetrain),
					new StopEndgame(endgame),
					new StopShooter(shooter),
					new StopIntakeSystem(intake)
					);

	}

}
