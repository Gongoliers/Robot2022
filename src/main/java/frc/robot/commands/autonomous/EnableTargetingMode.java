package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Vision;

public class EnableTargetingMode extends InstantCommand {

    private final Vision m_vision;

    public EnableTargetingMode(Vision vision){
        m_vision = vision;
        addRequirements(vision);
    }

    @Override
    public void initialize() {
        m_vision.setDriverMode(false);
    }
}
