package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.commands.drivetrain.Drive;

/**
 * Tests every component of every subsytem. Run before matches
 * to check the status of the robot;
 */
public class FullSystemCheck extends SequentialCommandGroup {

    public FullSystemCheck(DrivetrainSubsystem drivetrain) {

        addCommands(

            // Test drivetrain subsystem
            new Drive(drivetrain, 1),
            new Drive(drivetrain, -1)

        );


    }

}
