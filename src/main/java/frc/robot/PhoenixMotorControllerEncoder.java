package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.thegongoliers.input.odometry.EncoderSensor;

public class PhoenixMotorControllerEncoder implements EncoderSensor {

    private BaseMotorController mMotor;
    private FeedbackDevice mDevice;
    private double mDistance;

    public PhoenixMotorControllerEncoder(BaseMotorController motor, FeedbackDevice device) {
        mMotor = motor;
        mDevice = device;
        reselectFeedbackDevice();
    }

    public void reselectFeedbackDevice(){
        mMotor.configSelectedFeedbackSensor(mDevice);
    }

    public double getDistance(){
        return mMotor.getSelectedSensorPosition() - mDistance;
    }

    public void reset(){
        mDistance = mMotor.getSelectedSensorPosition();
    }

    @Override
    public double getVelocity() {
        return mMotor.getSelectedSensorVelocity();
    }



}
