package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EndgameSubsystem;

public class HomeEndgame extends CommandBase {

    private final EndgameSubsystem m_subsystem;

    public HomeEndgame(EndgameSubsystem subsystem) {
        addRequirements(subsystem);
        m_subsystem = subsystem;
    }

    @Override
    public void execute() {
        m_subsystem.HomeEndgame();
    }
}
