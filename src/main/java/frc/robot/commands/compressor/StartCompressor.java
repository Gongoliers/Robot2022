package frc.robot.commands.compressor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.CompressorSubsystem;

public class StartCompressor extends InstantCommand {
    public StartCompressor(CompressorSubsystem compressor){
        super(() -> compressor.start());
    }
}
