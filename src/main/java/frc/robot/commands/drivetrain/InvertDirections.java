package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.RobotContainer;

/**
 * Allows the driver to control movement using a joystick / controller.
 */
public class InvertDirections extends CommandBase {

    private RobotContainer m_robot;

    public InvertDirections(RobotContainer robot) {

        m_robot = robot;

    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_robot.invertLR();
    }

    // Make this return true when this Command no longer needs to run execute
    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }

}
