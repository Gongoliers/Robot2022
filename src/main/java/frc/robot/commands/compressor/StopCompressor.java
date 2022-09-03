package frc.robot.commands.compressor;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Compressor;

public class StopCompressor extends InstantCommand {
    public StopCompressor(Compressor compressor){
        super(() -> compressor.stop());
    }
}
