package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class EndgameTimer {
    private static boolean override;

    /**
     * This function will return Timer.getMatchTime() unless EndgameTimer.breakTimer() is called.
     * @return MatchTime / 0 (if EndgameTimer.breakTimer() is called)
     */
    public static double getMatchTime() {
        if (!override) {
            return DriverStation.getMatchTime();
        } else {
            return 0;
        }
    }

    public static void breakTimer() {
        override = true;
    }
    
}
