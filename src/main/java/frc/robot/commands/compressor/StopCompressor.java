package frc.robot.commands.compressor;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.CompressorSubsystem;

public class StopCompressor extends InstantCommand {
    public StopCompressor(CompressorSubsystem compressor){
        super(() -> compressor.stop());
    }
}
