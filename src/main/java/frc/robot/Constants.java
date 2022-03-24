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
        public static final double kNormalVoltage = 7;
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

    public static final double kInterfaceMotorRunTime = 2;
    public static final double kInterfaceMotorWaitTime = 1.2;

    // TODO: Tune these
    public static final double kCurrentSpikeThreshold = 20;
    public static final int kCurrentSensorPort = 9;
  }

  public static final class AutoConstants {
    public static final double kAutoDriveToFenderSpeed = 0.6;
    public static final double kAutoDriveToFenderSeconds = 4;

    public static final double kAutoDriveAwayFromFenderSpeed = -0.55;
    public static final double kAutoDriveAwayFromFenderSeconds = 2.5;

    public static final double kDistanceToDriveForHigh = 25/12.0; // IMPORTANT DIVIDE BY DOUBLE
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kXboxControllerPort = 1;
    public static final boolean KSingleDriverMode = false;
  }

  public static final class EndgameConstants {
    public static final double kDelayToUnlockPneumatics = 1.0; // THIS IS THE DELAY BETWEEN THE MOTORS SPINNGING AND THE PNEUMATICS BEING TOLD TO DISENGAGE
    public static final int kMotorACAN = 20; // LEFT OF BATTERY (WHEN STANDING BEHIND BATTERY)
    public static final int kMotorBCAN = 21; // RIGHT OF BATTERY (WHEN STANDING BEHIND BATTERY)
    public static final int kSolenoidCAN = 7; // CAN PORT OF SOLENOID
    public static final int kCappedDistanceA = 31000; //  this controls the amount the distance the motor should travel when raising / lowering the endgame system
    public static final int kCappedDistanceB = 28700; //  this controls the amount the distance the motor should travel when raising / lowering the endgame system
    public static final double kRaiseMotorSpeedA = 0.11*8; // AKA ARM RAISE SPEED
    public static final double kRaiseMotorSpeedB = 0.1*8;
    public static final double kLowerMotorSpeedA = -0.09*5; // AKA CLIMB SPEED
    public static final double kLowerMotorSpeedB = -0.1*5;
    public static final int kLimitSwitchAPort = 1;
    public static final int kLimitSwitchBPort = 0;
  }

  public static final class CompressorConstants {
    public static final double kLimitedCompressorMaxDriveSpeed = 0.3;
  }
}
