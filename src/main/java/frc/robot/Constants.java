// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {
        // --> CAN IDs (motors)
        public static final int kLeftDriveCAN1 = 1;
        public static final int kLeftDriveCAN2 = 2;
        public static final int kLeftDriveCAN3 = 3;

        public static final int kRightDriveCAN1 = 4;
        public static final int kRightDriveCAN2 = 5;
        public static final int kRightDriveCAN3 = 6;

        // Voltage Regulation
        public static final double kNormalVoltage = 5.25;
        public static final double kFastVoltage = 12;

        // Power Control
        public static final double kSecondsToReachFullSpeed = 0.25; 
        public static final double kTurnThreshold = 0.2;
        
        // Encoders
        public static final double kWheelDiameterInches = 4;
        // This was calculated by recording the ticks for one rotation
        public static final double kEncoderDistancePerPulse = (kWheelDiameterInches * Math.PI) / 17923.0;
    }   

  public static final class IntakeConstants {
  }

  public static final class ShooterConstants {
  }

  public static final class AutoConstants {
    public static final double kAutoDriveDistanceInches = 60;
    public static final double kAutoBackupDistanceInches = 20;
    public static final double kAutoDriveSpeed = 0.5;
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final boolean KSingleDriverMode = false;
  }
}
