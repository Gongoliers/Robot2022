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
        public static final double kSecondsToReachFullSpeed = 0.6; 
        public static final double kTurnThreshold = 0.2;
        
        // Encoders
        public static final double kWheelDiameterFeet = 4 / 12.0;
        // This was calculated by recording the ticks for one rotation
        public static final double kEncoderDistancePerPulse = 14 / 12.0 / 19004.0;//(kWheelDiameterFeet * Math.PI) / 17923.0;
    }   

  public static final class IntakeConstants {
    public static final int kIntakeCanId = 32;
    public static final double kIntakeSpeed = 0.27;

    public static final int kSolenoidCAN1 = 5;
    public static final int kSolenoidCAN2 = 6;
  }

  public static final class ShooterConstants {
    public static final int kFeederMotorCANId = 10;
    public static final int kOuttakeMotorCANId = 11;
    public static final int kInterfaceMotorCANId = 12;
  
    public static final double kRampUpSeconds = 1.2;

    public static final double kFeederMotorTargetSpeedHigh = 0.67*1.03;
    public static final double kOuttakeMotorTargetSpeedHigh = 0.57*1.03;
    public static final double kInterfaceMotorSpeedHigh = 0.35*1.03;

    public static final double kFeederMotorTargetSpeedLow = 0.4;
    public static final double kOuttakeMotorTargetSpeedLow = 0.35;
    public static final double kInterfaceMotorSpeedLow = 0.35;

    public static final double kInterfaceMotorRunTime = 0.8;
    public static final double kInterfaceMotorWaitTimeHigh = 1.2;
    // public static final double kInterfaceMotorWaitTimeLow = 1.0;
  }

  public static final class AutoConstants {
    public static final double kAutoDriveToFenderSpeed = 0.6;
    public static final double kAutoDriveToFenderSeconds = 3.6;

    public static final double kAutoDriveAwayFromFenderSpeed = -0.55;
    public static final double kAutoDriveAwayFromFenderSeconds = 2.5;

    public static final double kDistanceToDriveForHigh = 27/12.0; // IMPORTANT DIVIDE BY DOUBLE
    // public static final double kDistanceToDriveForHigh = 25/12.0; // IMPORTANT DIVIDE BY DOUBLE
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
    public static final int kSolenoidACAN = 7; // CAN PORT OF SOLENOID
    public static final int kSolenoidBCAN = kSolenoidACAN;
    public static final double kTopDistance = 0.99;//3 / 2.0;// TODO: TESTING ONLY;
    public static final double kCappedDistanceA = 1 / 264700.0; //  this controls the amount the distance the motor should travel when raising / lowering the endgame system
    public static final double kCappedDistanceB = 1 / 263599.0; //  this controls the amount the distance the motor should travel when raising / lowering the endgame system
    public static final double kAscendMotorSpeedA = 0.90;
    public static final double kAscendMotorSpeedB = kAscendMotorSpeedA;
    public static final double kDescendMotorSpeedA = -0.80;
    public static final double kDescendMotorSpeedB = kDescendMotorSpeedA;
    public static final double kHomingSpeed = 0.12;
    public static final int kLimitSwitchAPort = 1;
    public static final int kLimitSwitchBPort = 0;
  }

  public static final class CompressorConstants {
    public static final double kLimitedCompressorMaxDriveSpeed = 0.3;
  }
}
