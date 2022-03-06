package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.thegongoliers.input.odometry.BaseEncoderSensor;

/**
 * Allows for the conversion of the TalonFX Encoder Value to an Encoder 
 */
public class TalonFXEncoder extends BaseEncoderSensor {

    private WPI_TalonFX mTalonFX;

    public TalonFXEncoder(WPI_TalonFX talonFX) {
        super(talonFX::getSelectedSensorPosition, talonFX::getSelectedSensorVelocity);
        mTalonFX = talonFX;
        useIntegratedSensor();
    }

    public void useIntegratedSensor(){
        mTalonFX.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    }
    
}
