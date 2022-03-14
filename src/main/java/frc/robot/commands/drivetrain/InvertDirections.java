package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;

/**
 * Allows the driver to control movement using a joystick / controller.
 */
public class InvertDirections extends InstantCommand {

    private RobotContainer m_robot;

    public InvertDirections(RobotContainer robot) {

        m_robot = robot;

    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_robot.invertLR();
    }

}
