package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CompressorSubsystem extends SubsystemBase {

    private final Compressor m_compressor;

    public CompressorSubsystem(){
        m_compressor = new Compressor(PneumaticsModuleType.CTREPCM);
        stop();
    }

    public void start(){
        m_compressor.enableDigital();
    }

    public void stop(){
        m_compressor.disable();
    }

    public boolean enabled(){
        return m_compressor.enabled();
    }
}
