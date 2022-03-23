package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootOneLow extends SequentialCommandGroup {

	public ShootOneLow(ShooterSubsystem shooter) {
		addRequirements(shooter);
		addCommands(
				new InstantCommand(shooter::resetCurrentSensor),
				new ShootLow(shooter).until(shooter::isCurrentSensorTripped),
				new InstantCommand(shooter::resetCurrentSensor)
		);
	}
}
