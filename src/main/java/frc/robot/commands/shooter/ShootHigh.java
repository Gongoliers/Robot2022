package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootHigh extends CommandBase {
    private ShooterSubsystem m_shooter;

    public ShootHigh(ShooterSubsystem shooter) {
        addRequirements(shooter);
        m_shooter = shooter; 

    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        m_shooter.shootHigh();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {

    }
}
