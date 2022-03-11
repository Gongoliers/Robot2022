package frc.robot.commands.endgame;

import com.thegongoliers.input.odometry.AverageEncoderSensor;
import com.thegongoliers.input.odometry.EncoderSensor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.Constants.EndgameConstants;

public class LowerMotor extends CommandBase {
    
    private EndgameSubsystem m_endgame;
    public LowerMotor(EndgameSubsystem endgame) {
        addRequirements(endgame);
    }

     // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
    
        /**
         * This code is designed to ensure that the two arms raise at similar rates
         */

        // This is the maximum amount of distance one arm can be away from the other
        double max_distance = 1; // in

        // Initializing the EncoderSensors from the Endgame Subsystem
        EncoderSensor left = m_endgame.getLeftEncoderSensor();
        EncoderSensor right = m_endgame.getRightEncoderSensor();

        /**
         * Checking to see if the distance between the two arms is greater
         * than the max distance
         * 
         * I am comparing distances rather than velocities for readability
         * however when given some further thought, this is unreliable
         */

        double max_velocity = max_distance * EndgameConstants.kLowerMotorSpeed;
        if (Math.abs(left.getVelocity() - right.getVelocity()) > max_velocity) {

            // Grabbing the Speed of the two arms
            double leftRate = Math.abs(left.getVelocity());
            double rightRate = Math.abs(right.getVelocity());

            // This is the strength of the correction, leaving at 1 for now
            double strength = 1;
            /**
             * This code has been MAJORLY simplified
             * 
             * Formula for Ratio:
             *      ((maxSpeed - minSpeed) / minSpeed) * strength
             * 
             * HOWEVER, we already know what the max and min speeds are from the
             * if condition so we can logically declare that left will be the 
             * maxSpeed on the first condtional
             * 
             * If we reuse this code, the EndgameConstants.kLowerSpeed - <=== is VERY IMPORTANT
             * We expect EndgameConstants.kLowerSpeed to be a POSITIVE VALUE, so we must SUBTRACT to make it lower
             */
            if (leftRate > rightRate) {
                // slow left rate
                m_endgame.getLeftMotor().set(EndgameConstants.kLowerMotorSpeed - Math.abs(((leftRate - rightRate) / leftRate) * strength) );
                m_endgame.getRightMotor().set(EndgameConstants.kLowerMotorSpeed);
            } else {
                m_endgame.getLeftMotor().set(EndgameConstants.kLowerMotorSpeed);
                m_endgame.getRightMotor().set(EndgameConstants.kLowerMotorSpeed - Math.abs(((rightRate - leftRate) / rightRate) * strength) );
            }
        } else {
            m_endgame.getMotors().set(EndgameConstants.kLowerMotorSpeed);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        AverageEncoderSensor m_encoder = m_endgame.getEncoders();
        /** TODO: Does this BaseEncoderSensor update every time this function is called?
         *      If not, change .getEncoders to .getEncoderDistance, have it return a variable
         *      and the variable will be updated in the Subsystem's perioidic
         */
        if (m_encoder.getDistance() <= -EndgameConstants.kCappedDistance) {
            return true;
        } else {
            return false;
        }
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }
}
