package frc.robot.subsystems.endgame;

public class LockedEndgameControl implements EndgameControl {

    private final EndgameArm m_arm;

    public LockedEndgameControl(EndgameArm arm){
        m_arm = arm;
        arm.stop();
        arm.lock();
    }

    @Override
    public EndgameControl run(EndgameAction action) {
        if (action == EndgameAction.Unlock){
            m_arm.unlock();
            return new UnlockedEndgameControl(m_arm);
        }

        if (action == EndgameAction.Descend){
            descend();
        }

        if (action == EndgameAction.Stop){
            m_arm.stop();
            m_arm.lock();
        }
        
        return this;
    }

    private void descend() {
        if (m_arm.isFloored()) {
            m_arm.stop();
            m_arm.calibrate();
            return;
        }

        m_arm.descend();
    }
}
