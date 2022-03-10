package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.VisionSubsystem;

public class EnableTargetingMode extends InstantCommand {

    private final VisionSubsystem m_vision;

    public EnableTargetingMode(VisionSubsystem vision){
        m_vision = vision;
        addRequirements(vision);
    }

    @Override
    public void initialize() {
        m_vision.setDriverMode(false);
    }
}
