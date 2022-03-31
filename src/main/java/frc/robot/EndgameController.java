package frc.robot;

public class EndgameController {
    private final EndgameArm m_endgameArmA;
    private final EndgameArm m_endgameArmB;
    private double m_ascentSpeed, m_descentSpeed;

    /**
     * This function will take both EndgameArms as parameters, and will
     * interface the speed used for climbing.
     * @param endgameArmA
     * @param endgameArmB
     */
    public EndgameController(EndgameArm endgameArmA, EndgameArm endgameArmB) {
        m_endgameArmA = endgameArmA;
        m_endgameArmB = endgameArmB;
    }

    /**
     * Sets EndgameArm speeds to speeds previously
     * configured
     */
    public void Ascend() {
        set(m_ascentSpeed);
    }

    /**
     * Sets EndgameArm speeds to speeds previously
     * configured
     */
    public void Descend() {
        set(m_descentSpeed);
    }

    /**
     * Sets the speed of the EndGameController
     * This will (should) eliminate the need
     * for speed calibrations
     * @param speed
     */
    public void set(double speed) {
        if (speed > 0) {
            setRaising(speed);
        } else if (speed < 0) {
            setLowering(speed);
        }
    }
    
    /**
     * Configures Default Ascent Speed
     * @param speed
     */
    public void setAscentSpeed(double speed) {
        m_ascentSpeed = speed;
    }

    /**
     * Configures the Default Descent Speed
     * @param speed
     */
    public void setDescentSpeed(double speed) {
        m_descentSpeed = speed;
    }

    /**
     * Hopefully this works lmao
     * @param speed
     */
    private void setRaising(double speed) {
        // Disables Interface Control
        m_endgameArmA.toggleInterface();
        m_endgameArmB.toggleInterface();

        // Mapping to speedA and speedB
        double speedA = speed;
        double speedB = speed;

        // Calculate Slippage
        double armACounts = m_endgameArmA.getCappedDistance();
        double armBCounts = m_endgameArmB.getCappedDistance();
        
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Get Encoders ==> Method #1 (Using Counts)
        double armADistance = m_endgameArmA.getEncoder().getDistance();
        double armBDistance = m_endgameArmB.getEncoder().getDistance();

        /** 
         * Math hurts.
         * 
         * The easiest (and only way) to do this will be to use 
         * percentage of climb completed. 
         */
        double armAPercentage = armADistance / armACounts;
        double armBPercentage = armBDistance / armBCounts;

        /**
         * Kind of some complicated math, so let's review it
         * 
         * If armA is more done than armB, then we are going to
         * limit the speed of armA, and vice versa
         */
        speedA = armAPercentage >= armBPercentage ? (armBPercentage/armAPercentage)*speed : 
                                                    (armAPercentage/armBPercentage)*speed;
        
        speedB = armBPercentage >= armAPercentage ? (armAPercentage/armBPercentage)*speed :
                                                    (armBPercentage/armAPercentage)*speed;


        /**
         * Cleaning Up the Numbers Outputted
         * 
         * This logic checks to ensure that the speed is not greater
         * than 1.0, and if it is not limits the number to two decimal
         * places as to not overconstrain the motor controller
         */
        speedA = 1.0 >= speedA ? 1.0 : Math.round(speedA * 100) / 100;
        speedB = 1.0 >= speedB ? 1.0 : Math.round(speedB * 100) / 100;

        // Set speeds
        m_endgameArmA.set(m_endgameArmA.doneAscending() ? 0.0 : speed);
        m_endgameArmB.set(m_endgameArmB.doneDescending() ? 0.0 : speed);

        // Relinquishes Control
        m_endgameArmA.toggleInterface();
        m_endgameArmB.toggleInterface();

    }

    /**
     * Identical to setRaising except for:
     *  - // SetSpeed
     *  = // MathHurts
     * @param speed
     */
    private void setLowering(double speed) {
        // Disables Interface Control
        m_endgameArmA.toggleInterface();
        m_endgameArmB.toggleInterface();

        // Mapping to speedA and speedB
        double speedA = speed;
        double speedB = speed;

        // Calculate Slippage
        double armACounts = m_endgameArmA.getCappedDistance();
        double armBCounts = m_endgameArmB.getCappedDistance();
        
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Get Encoders ==> Method #1 (Using Counts)
        double armADistance = m_endgameArmA.getEncoder().getDistance();
        double armBDistance = m_endgameArmB.getEncoder().getDistance();

        /** 
         * Math hurts.
         * 
         * The easiest (and only way) to do this will be to use 
         * percentage of climb completed. 
         */
        double armAPercentage = (armACounts - armADistance) / armACounts;
        double armBPercentage = (armBCounts - armBDistance) / armBCounts;

        /**
         * Kind of some complicated math, so let's review it
         * 
         * If armA is more done than armB, then we are going to
         * limit the speed of armA, and vice versa
         */
        speedA = armAPercentage >= armBPercentage ? (armBPercentage/armAPercentage)*speed : 
                                                    (armAPercentage/armBPercentage)*speed;
        
        speedB = armBPercentage >= armAPercentage ? (armAPercentage/armBPercentage)*speed :
                                                    (armBPercentage/armAPercentage)*speed;


        /**
         * Cleaning Up the Numbers Outputted
         * 
         * This logic checks to ensure that the speed is not greater
         * than 1.0, and if it is not limits the number to two decimal
         * places as to not overconstrain the motor controller
         */
        speedA = 1.0 >= speedA ? 1.0 : Math.round(speedA * 100) / 100;
        speedB = 1.0 >= speedB ? 1.0 : Math.round(speedB * 100) / 100;

        // Set speeds
        m_endgameArmA.set(m_endgameArmA.doneDescending() ? 0.0 : speed*-1);
        m_endgameArmB.set(m_endgameArmB.doneDescending() ? 0.0 : speed*-1);

        // Relinquishes Control
        m_endgameArmA.toggleInterface();
        m_endgameArmB.toggleInterface();

    }

}
