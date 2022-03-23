package frc.robot.commands.shooter;

import com.thegongoliers.input.time.Clock;
import com.thegongoliers.input.time.RobotClock;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootHighIndexed extends CommandBase {

    private ShooterSubsystem m_shooter;
    private double m_startTime;
    private Clock m_clock;
    private boolean m_tripped = false;

    public ShootHighIndexed(ShooterSubsystem shooter) {
        addRequirements(shooter);
        m_shooter = shooter;
        m_clock = new RobotClock();
    }

    @Override
    public void execute() {
        m_shooter.spinForHigh();

        if (m_shooter.isCurrentSensorTripped()){
            m_tripped = true;
            m_shooter.resetCurrentSensor();
            m_startTime = m_clock.getTime();
        }

        if (m_tripped && m_clock.getTime() - m_startTime > Constants.ShooterConstants.kInterfaceMotorWaitTime){
            m_tripped = false;
        }

        if (m_shooter.isAtFullSpeed() && !m_tripped) {
            m_shooter.feedManual();
        } else {
            m_shooter.stopInterfaceMotor();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stop();
    }

}
