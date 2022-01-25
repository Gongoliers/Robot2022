package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.commands.drivetrain.StopDrivetrain;

public class StopAll extends ParallelCommandGroup {
	
	public StopAll(DrivetrainSubsystem drivetrain) {
		
		addCommands(
				new StopDrivetrain(drivetrain)
		);

	}

}
