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
        // The radius of the wheels in inches
        public static final double kWheelRadius = 2.0;
        // This was calculated by recording the ticks for one rotation
        public static final double kEncoderTicksPerRotation = 19004.0;
        // The gear reduction of the gearbox (i.e. how many motor rotations per wheel rotation)
        public static final double kGearRatio = 11.25;
        // The circumference of the wheel
        // If the wheel makes one rotation and perfectly translates, the wheel will have moved this
        // number of **inches**
        public static final double kDistanceTravelledPerWheelRotation = Math.PI * 2 * kWheelRadius;
        public static final double kInchesPerEncoderPulse = kDistanceTravelledPerWheelRotation / kEncoderTicksPerRotation;
    }   

  public static final class IntakeConstants {
    public static final int kIntakeCanId = 33;
    public static final double kIntakeSpeed = 0.27;

    public static final int kSolenoidCAN1 = 5;
    public static final int kSolenoidCAN2 = 6;
  }

  public static final class ShooterConstants {
    // FIXME: Order of CAN IDs changed to match the order the ball travels
    public static final int kInjectorMotorCANId = 10;
    public static final int kFeederMotorCANId = 11;
    public static final int kOuttakeMotorCANId = 12;
  
    // The amount of time the injector motor should run to ensure a single ball goes through
    public static final double kInjectorMotorRunTime = 0.8;
    public static final double kInjectorMotorSpeed = 0.35;

    // The amount of time the feeder and outtake flywheels have to spin up before a ball is injected
    public static final double kSpinUpTimeHigh = 1.2;
    public static final double kSpinUpTimeLow = 1.0;

    public static final double kFeederMotorSpeedHigh = 0.7;
    public static final double kOuttakeMotorSpeedHigh = 0.6;

    public static final double kFeederMotorSpeedLow = 0.4;
    public static final double kOuttakeMotorSpeedLow = 0.35;
  }

  public static final class AutoConstants {
    public static final double kAutoDriveToFenderSpeed = 0.6;
    public static final double kAutoDriveToFenderSeconds = 3.6;

    public static final double kAutoDriveAwayFromFenderSpeed = -0.55;
    public static final double kAutoDriveAwayFromFenderSeconds = 2.5;

    public static final double kDistanceToDriveForHigh = 27 / 12.0;

    // The amount of time the robot will spend shooting
    // IMPORTANT: This must be greater than the amount of time the command needs to run
    public static final double kShootHighTime = 8.0;
    public static final double kShootLowTime = 8.0;
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
}
