package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.commands.drivetrain.StopDrivetrain;
import frc.robot.commands.endgame.StopEndgame;

public class StopAll extends ParallelCommandGroup {
	
	public StopAll(DrivetrainSubsystem drivetrain, EndgameSubsystem endgame) {
		
		addCommands(new StopDrivetrain(drivetrain),
					new StopEndgame(endgame)
					);

	}

}
