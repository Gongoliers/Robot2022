package frc.robot;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BooleanSupplier;

import com.thegongoliers.hardware.Hardware;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Button;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.commands.*;
// import frc.robot.commands.autonomous.*;
// import frc.robot.commands.climber.*;
import frc.robot.commands.drivetrain.*;
// import frc.robot.commands.powercell.*;


/**
 * This class binds the commands of the physical operator interface to the commands
 * of the rest of the robot. 
 */
public class OI {
    /**
     * This year, the driver requested a joystick, so controller functionality may be implemented
     * down the line, however, is not a priority. 
     */

    // Assigning port numbers
    public static final int DRIVER_JOYSTICK_PORT = 0;
    public static final int MANIPULATOR_JOYSTICK_PORT = 1;

    // Creating the Joysticks
    public Joystick driverJoystick;
    public Joystick manipulatorJoystick;


     public OI() {
        /**
         * The driver joystick will control the drivetrain with the x and z axis
         * Button 1 on the joystick will enable turbo while held.
         * Button 09-10 on the joystick will stop all active commands when pressed.
         * Button 11-12 on the joystick will align the drivetrain to the vision target.
         */

        driverJoystick = new Joystick(DRIVER_JOYSTICK_PORT);
        manipulatorJoystick = new Joystick(MANIPULATOR_JOYSTICK_PORT);

        //// -- Driver Joystick setup

        Button driverTurbo = new JoystickButton(driverJoystick, 1); // trigger
        driverTurbo.whenReleased(new SetTurboDrivetrain(false));
        driverTurbo.whenPressed(new SetTurboDrivetrain(true));

        Button driverStopAll1 = new JoystickButton(driverJoystick, 11); // bottom row of buttons
        Button driverStopAll2 = new JoystickButton(driverJoystick, 12);
        driverStopAll1.whenPressed(new StopAll());
        driverStopAll2.whenPressed(new StopAll());

        // Button driverAlignTarget1 = new JoystickButton(driverJoystick, 9); // second to bottom row of buttons
        // Button driverAlignTarget2 = new JoystickButton(driverJoystick, 10);
        // driverAlignTarget1.whileHeld(new EnableTargetingAlignToTarget());
        // driverAlignTarget2.whileHeld(new EnableTargetingAlignToTarget());
        // driverAlignTarget1.whenReleased(new DisableTargetingMode());
        // driverAlignTarget2.whenReleased(new DisableTargetingMode());

        Button driveStickMoved = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return Math.abs(getDriverSpeed()) > 0.3 || Math.abs(getDriverRotation()) > 0.3;
            }
        });
        driveStickMoved.whenPressed(new DrivetrainOperatorContol());

     }


}
