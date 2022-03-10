// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kylecorry.pid.PID;

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
        public static final double kWheelDiameterInches = 4 / 12.0;
        // This was calculated by recording the ticks for one rotation
        public static final double kEncoderDistancePerPulse = (kWheelDiameterInches * Math.PI) / 17923.0;
    }   

  public static final class IntakeConstants {
    public static final int kIntakePWM = 7;
    public static final double kIntakeSpeed = 0.2;
  }

  public static final class ShooterConstants {
    // TODO: FIX THESE VALUES
    public static final int kFeederMotorPWM = 2; 
    public static final int kShooterMotorPWM = 3;
    public static final int kShooterEncoderA = 4; 
    public static final int kShooterEncoderB = 5;
    // TODO: Tune this when the design of the shooter is finalized
    public static final double kLowShooterSpeed = 0.35;
    public static final double kHighShooterSpeed = 0.95;
    public static final double kMaxVoltage = 12.5;

    // TODO: Tune this to shoot? learn more about this
    public static final PID kDistancePID = new PID(0.1, 0.0, 0.0);
    public static final PID kVelocityPID = new PID(0.1, 0.0, 0.0);

    public static final double kFeederMotorSpeed = 0.8;

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

  public static final class EndgameConstants {
    public static final int kLeftMotorCAN = 7; // LEFT OF BATTERY (WHEN STANDING BEHIND BATTERY)
    public static final int kRightMotorCAN = 8; // RIGHT OF BATTERY (WHEN STANDING BEHIND BATTERY)
    public static final int kSolenoidCAN = 9; // CAN PORT OF SOLENOID
    public static final int kEncoderDistancePerPulse = 0; // TODO: CONFIGURE THIS
    public static final int kCappedDistance = 1; // TODO: Configure this | this controls the amount the distance the motor should travel when raising / lowering the endgame system
    public static final double kRaiseMotorSpeed = 0.2;
    public static final double kLowerMotorSpeed = -0.2;
  }
}
