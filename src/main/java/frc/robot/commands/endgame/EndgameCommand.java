package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.subsystems.endgame.EndgameAction;

public class EndgameCommand extends CommandBase {

    private final EndgameSubsystem m_subsystem;
    private final EndgameAction m_action;

    public EndgameCommand(EndgameSubsystem subsystem, EndgameAction action) {
        addRequirements(subsystem);
        m_subsystem = subsystem;
        m_action = action;
    }

    @Override
    public void execute() {
        m_subsystem.run(m_action);
    }
}
