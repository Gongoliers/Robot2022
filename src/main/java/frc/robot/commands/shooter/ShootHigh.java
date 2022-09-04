package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class ShootHigh extends ParallelCommandGroup {
   public ShootHigh(Shooter shooter)  {
        addCommands(
            // Spin up the motor whenever this command is executed 
            new SpinForHigh(shooter),
            // While this is happening...
            new SequentialCommandGroup(
                // Wait some time for the motors to be fully spun up ...
                new WaitCommand(ShooterConstants.kSpinUpTimeHigh),
                // And then inject the ball into the spun up flywheels
                new InjectBall(shooter).withTimeout(ShooterConstants.kInjectorMotorRunTime)
            )
        );
   }
}
