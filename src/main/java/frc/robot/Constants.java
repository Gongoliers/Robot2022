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

        // Inactivity
        public static final double kInactivityThresholdSeconds = 1.0;

        // Voltage Regulation
        public static final double kNormalVoltage = 5.25;
        public static final double kFastVoltage = 10;

        // Power Control
        public static final double kSecondsToReachFullSpeed = 0.3; 
        public static final double kTurnThreshold = 0.2;
        
        // Encoders
        public static final double kWheelDiameterFeet = 4 / 12.0;
        // This was calculated by recording the ticks for one rotation
        public static final double kEncoderDistancePerPulse = (kWheelDiameterFeet * Math.PI) / 17923.0;
    }   

  public static final class IntakeConstants {
    public static final int kIntakeCanId = 32;
    public static final double kIntakeSpeed = 0.3;

    public static final int kSolenoidCAN1 = 5;
    public static final int kSolenoidCAN2 = 6;
  }

  public static final class ShooterConstants {
    public static final int kFeederMotorCANId = 10;
    public static final int kOuttakeMotorCANId = 11;
    public static final int kInterfaceMotorCANId = 12;
    
    public static final double kRampUpSeconds = 1.2;

    public static final double kFeederMotorTargetSpeedHigh = 0.67;
    public static final double kOuttakeMotorTargetSpeedHigh = 0.57;
    public static final double kInterfaceMotorSpeedHigh = 0.35;

    public static final double kFeederMotorTargetSpeedLow = 0.35;
    public static final double kOuttakeMotorTargetSpeedLow = 0.3;
    public static final double kInterfaceMotorSpeedLow = 0.35;

    public static final double kInterfaceMotorRunTime = 3;
    public static final double kInterfaceMotorWaitTime = 1;
  }

  public static final class AutoConstants {
    public static final double kAutoDriveToFenderSpeed = 0.5;
    public static final double kAutoDriveToFenderSeconds = 4.5;

    public static final double kAutoDriveAwayFromFenderSpeed = -0.5;
    public static final double kAutoDriveAwayFromFenderSeconds = 3.5;

    public static final double kDistanceToDriveForHigh = 20.5/12;
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kXboxControllerPort = 1;
    public static final boolean KSingleDriverMode = false;
  }

  public static final class EndgameConstants {
    public static final double kDelayToUnlockPneumatics = 1.0; // THIS IS THE DELAY BETWEEN THE MOTORS SPINNGING AND THE PNEUMATICS BEING TOLD TO DISENGAGE
    public static final int kMotorACAN = 21; // LEFT OF BATTERY (WHEN STANDING BEHIND BATTERY)
    public static final int kMotorBCAN = 20; // RIGHT OF BATTERY (WHEN STANDING BEHIND BATTERY)
    public static final int kSolenoidCAN = 7; // CAN PORT OF SOLENOID
    public static final int kCappedDistance = 29000; // TODO: Configure this | this controls the amount the distance the motor should travel when raising / lowering the endgame system
    public static final double kRaiseMotorSpeed = 0.1;
    public static final double kLowerMotorSpeed = -0.1;
  }

  public static final class CompressorConstants {
    public static final double kLimitedCompressorMaxDriveSpeed = 0.4;
  }
}
