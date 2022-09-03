package frc.robot.commands.compressor;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Compressor;

public class StartCompressor extends InstantCommand {
    public StartCompressor(Compressor compressor){
        super(() -> compressor.start());
    }
}
