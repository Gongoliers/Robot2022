package frc.robot;

import com.thegongoliers.input.odometry.EncoderSensor;
import com.thegongoliers.output.actuators.GSpeedController;

import edu.wpi.first.wpilibj.Solenoid;

public class EndgameArm2 {

    public static enum EndgameArmState {
        FLOORED, // Bottom
        DESCENDING, // Lowering
        ASCENDING,  // Raising
        CAPPED, // Reached Top
        STOPPED // Stopped
    }

    private final GSpeedController mMotor;
    private final EncoderSensor mEncoderSensor;
    private final InvertableLimitSwitch mLimitSwitch;
    private final Solenoid mArmLockController;
    private EndgameArmState mState = EndgameArmState.STOPPED;
    private double mCappedDistance;
    private double mAscentSpeed;
    private double mDescentSpeed;

    /**
     * This is the endgame arm controller for the 2022 game
     * 
     * @param motor
     * @param encoder
     * @param limitSwitch
     * @param armLockController
     */
    public EndgameArm2(GSpeedController motor, EncoderSensor encoder, InvertableLimitSwitch limitSwitch, Solenoid armLockController) {
        motor.useVoltageControl(12.0);
        mMotor = motor;
        mEncoderSensor = encoder; 
        mLimitSwitch = limitSwitch; 
        mArmLockController = armLockController;
        mAscentSpeed = 0.5;
        mDescentSpeed = -0.5;
    }

    /**
     * Sets the descent speed
     * @param speed Speed of arm, will ALWAYS be negative
     */
    public void setDescentSpeed(double speed) {
        mDescentSpeed = -Math.abs(speed); 
    }

    /**
     * Sets the ascent speed
     * @param speed Speed of arm, will ALWAYS be positive
     */
    public void setAscentSpeed(double speed) {
        mAscentSpeed = Math.abs(speed);
    }

    /**
     * Sets the speed of the motor. This is for
     * TESTING only.
     */
    public void set(double speed) {
        mMotor.set(speed);
    }

    /**
     * Returns State
     */
    public EndgameArmState getState() {
        return mState;
    }

    /**
     * Is Done ?
     */
    public boolean done() {
        return mState == EndgameArmState.CAPPED || mState == EndgameArmState.FLOORED;
    }

    /**
     * Ascends Endgame Arm
     */
    public void ascend() {
        if (!mArmLockController.get()) {
            throw new Error("Solenoid is NOT unlocked. ");
        }
        mState = EndgameArmState.ASCENDING; 
        drive();
    }

    /**
     * Lowers Endgame Arm
     */
    public void descend() {
        mState = EndgameArmState.DESCENDING; 
        drive();
    }

    /**
     * Done Ascending ? 
     * @return
     */
    private boolean positionCapped() {
        return mEncoderSensor.getDistance() >= mCappedDistance; 
    }

    /**
     * Done Descending ?
     * @return
     */
    private boolean positionFloored() {
        return mLimitSwitch.isTriggered();
    }

    /**
     * Drive the arm
     */
    private void drive() {
        // Update State First => then drive arm
        switch (mState) {
            case ASCENDING:
                if (positionCapped()) mState = EndgameArmState.CAPPED;
                else {
                    // mMotor.setDistance(EndgameConstants.kTopDistance); //TODO: NEED PID FIRST
                    mMotor.set(mAscentSpeed);
                }
                break; 
            case DESCENDING:
                if (positionFloored()) mState = EndgameArmState.FLOORED;
                else {
                    mMotor.set(mDescentSpeed);
                }
                break;
            case STOPPED:
            case CAPPED:
            case FLOORED:
                break;
        }
    }

    /**
     * Mandatory stop function
     */
    public void stop() {
        mMotor.stopMotor();
        mState = EndgameArmState.STOPPED; 
    }

}

