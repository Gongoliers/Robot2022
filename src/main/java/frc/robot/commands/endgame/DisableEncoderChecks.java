package frc.robot.commands.endgame;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.EndgameSubsystem;

public class DisableEncoderChecks extends InstantCommand{

    private EndgameSubsystem m_endgame;

    public DisableEncoderChecks(EndgameSubsystem endgame) {
        addRequirements(endgame);
        m_endgame = endgame;
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_endgame.ignoreEncoders(true);
    }

}
