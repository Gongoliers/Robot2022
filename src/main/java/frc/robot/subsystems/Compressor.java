package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Compressor extends SubsystemBase {

    private final edu.wpi.first.wpilibj.Compressor mCompressor;

    public Compressor() {
        mCompressor = new edu.wpi.first.wpilibj.Compressor(PneumaticsModuleType.CTREPCM);
        stop();
    }

    public void start() {
        mCompressor.enableDigital();
    }

    public void stop() {
        mCompressor.disable();
    }

    public boolean isEnabled() {
        return mCompressor.enabled();
    }
}
