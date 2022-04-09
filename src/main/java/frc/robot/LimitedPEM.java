package frc.robot;

import com.thegongoliers.math.GMath;
import com.thegongoliers.output.drivetrain.DriveModule;
import com.thegongoliers.output.drivetrain.DriveSpeed;
import frc.robot.VoltageControlModule2;

/**
 * A drivetrain module which will force the drivetrain to accelerate slower. 
 */
public class LimitedPEM implements DriveModule {

    private static final double DEFAULT_TURN_THRESHOLD = 2.0;

    private double mRampingTime;
    private double mTurnThreshold;
    private VoltageControlModule2 mVoltageControlModule;
    private double mLastSeenVoltage;

    /**
     * Default constructor
     * @param secondsToReachFullSpeed the ramping time in seconds from 0 to full speed
     * @param turnThreshold the maximum difference between the two wheel speeds to run the power efficiency module on. Defaults to 2.
     */
    public LimitedPEM(double secondsToReachFullSpeed, double turnThreshold, VoltageControlModule2 voltageControlModule){
        super();
        setRampingTime(secondsToReachFullSpeed);
        setTurnThreshold(turnThreshold);
        mVoltageControlModule = voltageControlModule;
        mLastSeenVoltage = voltageControlModule.getMaxVoltage();
    }

    /**
     * Default constructor
     * @param secondsToReachFullSpeed the ramping time in seconds from 0 to full speed
     */
    public LimitedPEM(double secondsToReachFullSpeed, VoltageControlModule2 voltageControlModule){
        this(secondsToReachFullSpeed, DEFAULT_TURN_THRESHOLD, voltageControlModule);
    }

    @Override
    public DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime) {
        if (shouldApplyRateLimit(desiredSpeed) && mVoltageControlModule.getMaxVoltage() == mLastSeenVoltage){
            return desiredSpeed;
        }

        double maximumRate = getMaxRate(deltaTime);
        mLastSeenVoltage = mVoltageControlModule.getMaxVoltage();
        return applyRateLimit(currentSpeed, desiredSpeed, maximumRate);
    }

    /**
     * @param secondsToReachFullSpeed the ramping time in seconds from 0 to full speed
     */
    public void setRampingTime(double secondsToReachFullSpeed){
        if (secondsToReachFullSpeed < 0) throw new IllegalArgumentException("Seconds to reach full speed must be non-negative");
        mRampingTime = secondsToReachFullSpeed;
    }

    /**
     * @param turnThreshold the maximum difference between the two wheel speeds to run the power efficiency module on. Defaults to 2.
     */
    public void setTurnThreshold(double turnThreshold){
        if (turnThreshold < 0) throw new IllegalArgumentException("Turn threshold must be non-negative");
        mTurnThreshold = turnThreshold;
    }

    private boolean shouldApplyRateLimit(DriveSpeed speed) {
        return Math.abs(speed.getLeftSpeed() - speed.getRightSpeed()) >= mTurnThreshold;
    }

    private double getMaxRate(double deltaTime) {
        return mRampingTime == 0 ? 1 : deltaTime / mRampingTime;
    }

    private DriveSpeed applyRateLimit(DriveSpeed lastSpeed, DriveSpeed desiredSpeed, double maxRate){
        double leftSpeed = GMath.rateLimit(maxRate, desiredSpeed.getLeftSpeed(), lastSpeed.getLeftSpeed());
        double rightSpeed = GMath.rateLimit(maxRate, desiredSpeed.getRightSpeed(), lastSpeed.getRightSpeed());
        return new DriveSpeed(leftSpeed, rightSpeed);
    }
}