package frc.robot.commands.compressor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CompressorSubsystem;
import frc.robot.RobotContainer;
import frc.robot.Constants.CompressorConstants;

public class StartLimitedCompressor extends CommandBase {
    
    private CompressorSubsystem m_compressor;
    private RobotContainer rc;
    private boolean paused;
    private boolean end;

    /**
     * This method uses the default speed provided in EndgameConstants
     * @param endgame
     */
    public StartLimitedCompressor(CompressorSubsystem compressor, RobotContainer rc) {
        addRequirements(compressor);
        m_compressor = compressor;
        this.rc = rc;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        if (rc.getDriverSpeed() > CompressorConstants.kLimitedCompressorMaxDriveSpeed && m_compressor.enabled()) {
            paused = true;
            m_compressor.stop();
        } else {
            if (paused) {
                m_compressor.start();
            } else if (m_compressor.enabled() && !paused){
                end = true;
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return (end);
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
        m_compressor.stop();
    }
}
