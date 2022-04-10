package frc.robot.subsystems.endgame;

public class UnlockedEndgameControl implements EndgameControl {

    private final EndgameArm m_arm;

    public UnlockedEndgameControl(EndgameArm arm) {
        m_arm = arm;
        m_arm.unlock();
    }

    @Override
    public EndgameControl run(EndgameAction action) {
        if (action == EndgameAction.Stop) {
            return new LockedEndgameControl(m_arm);
        }

        if (action == EndgameAction.Ascend) {
            ascend();
        }

        if (action == EndgameAction.Descend) {
            descend();
        }

        return this;
    }


    private void ascend() {
        if (m_arm.isLocked()) {
            m_arm.stop();
            // This will ensure that the lock has time to trigger
            return;
        }

        if (m_arm.isCapped()){
            m_arm.stop();
            return;
        }

        m_arm.ascend();
    }

    private void descend() {

        if (m_arm.isLocked()){
            m_arm.stop();
            // This will ensure that the lock has time to trigger
            return;
        }

        if (m_arm.isFloored()) {
            m_arm.stop();
            m_arm.calibrate();
            return;
        }

        m_arm.descend();
    }
}
