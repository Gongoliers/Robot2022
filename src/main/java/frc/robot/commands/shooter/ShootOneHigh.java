package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootOneHigh extends SequentialCommandGroup {

	public ShootOneHigh(ShooterSubsystem shooter) {
		addRequirements(shooter);
		addCommands(
				new InstantCommand(shooter::resetCurrentSensor),
				new ShootHigh(shooter).until(shooter::isCurrentSensorTripped),
				new InstantCommand(shooter::resetCurrentSensor)
		);
	}
}
