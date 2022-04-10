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
        return this;
    }
}
