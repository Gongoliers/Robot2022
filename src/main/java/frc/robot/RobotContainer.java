package frc.robot;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BooleanSupplier;

import com.thegongoliers.hardware.Hardware;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.OIConstants;
import frc.robot.DPadButton.Direction;
import frc.robot.commands.StopAll;
import frc.robot.commands.autonomous.EnableTargetingAlignToTarget;
import frc.robot.commands.autonomous.FullSystemCheck;
import frc.robot.commands.autonomous.LeaveTarmac;
import frc.robot.commands.autonomous.LeaveTarmacAndShoot;
import frc.robot.commands.compressor.StartCompressor;
import frc.robot.commands.compressor.StopCompressor;
import frc.robot.commands.drivetrain.DrivetrainOperatorControl;
import frc.robot.commands.drivetrain.InvertDirections;
import frc.robot.commands.drivetrain.SetTurboDrivetrain;
import frc.robot.commands.endgame.DisengageSafetyLock;
import frc.robot.commands.endgame.EngageSafetyLock;
import frc.robot.commands.endgame.LowerMotorWithDelayAndSafety;
import frc.robot.commands.endgame.OverrideMatchTimer;
import frc.robot.commands.endgame.RaiseMotorWithDelayAndSafety;
import frc.robot.commands.endgame.StopEndgameWithDelay;
import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.intake.Intake;
import frc.robot.commands.intake.Outtake;
import frc.robot.commands.intake.RetractIntake;
import frc.robot.commands.shooter.Shoot;
import frc.robot.subsystems.CompressorSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class RobotContainer {
    /**
     * Initializing VisionSystem
     */
    private final VisionSubsystem vision = new VisionSubsystem();

    /**
     * Initiating DrivetrainSubsystem
     */
    private final DrivetrainSubsystem m_drivetrain = new DrivetrainSubsystem(vision);

    /**
     * Initiating EndgameSubsystem
     */
    private final EndgameSubsystem m_endgame = new EndgameSubsystem();

    /**
     * Initiating ShooterSubsystem
     */
    private final ShooterSubsystem m_shooter = new ShooterSubsystem();

    /**
     * Initiating IntakeSubsystem
     */
    private final IntakeSubsystem m_intake = new IntakeSubsystem();

    /**
     * Initiating CompressorSubsystem
     */
    //private final CompressorSubsystem m_compressor = new CompressorSubsystem();

    /**
     * Initiating SendableChooser
     */
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();
    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }

    /**
     * Initiating Controllers
     */
    private Joystick m_driverJoystick;
    private XboxController m_manipulatorController;

    // This is a multiplier to switch left/right directions
    private int lrAdjust = 1;

    /**
     * The container for the robot. 
     * Contains subsystems, OI devices, and commands
     */
    public RobotContainer() {
        // Initialize button bindings
        configureDriverBindings();
        configureManipulatorBindings();
        configureAutonomous();

        // m_intake.setDefaultCommand(new StopIntake(m_intake));
        // m_shooter.setDefaultCommand(new StopShooter(m_shooter));
    }

    /**
     * This function adds all Autonomous Options to the SmartDashboard
     */
    private void configureAutonomous() {
        autoChooser.setDefaultOption("Do Nothing", new StopAll(m_drivetrain, m_endgame, m_shooter, m_intake));
        autoChooser.addOption("System Check", new FullSystemCheck(m_drivetrain));
        autoChooser.addOption("Leave Tarmac", new LeaveTarmac(m_drivetrain));
        autoChooser.addOption("Leave Tarmac Shoot", new LeaveTarmacAndShoot(m_drivetrain, m_shooter, m_intake));
    
        SmartDashboard.putData("Auto mode", autoChooser);
    }

    /** These Functions Allow the DriverOperatedCommand to get speed and rotation */
    public double getDriverSpeed() {
        return lrAdjust*m_driverJoystick.getY();
     }
   
     public double getDriverRotation() {
        return m_driverJoystick.getZ();
     }

     public void invertLR() {
         lrAdjust*=-1;
     }
    
     public void quickRumble(boolean left) {
        m_manipulatorController.setRumble(left ? RumbleType.kLeftRumble : RumbleType.kRightRumble, 1.0);
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                m_manipulatorController.setRumble(left ? RumbleType.kLeftRumble : RumbleType.kRightRumble, 0);
            }
        }, 500);
    }

    /**
     * 
     * END OF FRC CODE
     * THE FOLLOWING CODE IS SOLELY DEDICATED TO MAPPING BINDINGS
     * 
     */

    /**
     * SECTION 1:
     *  -- Configuring The Driver's Bindings
     * 
     */
    private void configureDriverBindings() {
        // Driver Joystick Buttons
        m_driverJoystick = new Joystick(OIConstants.kDriverControllerPort);

        /**
         * Turbo Button Binding
         *  -- Enables / Disables Turbo Functionality
         * 
         * Binding:
         *  -- Joystick Trigger
         */
        JoystickButton turbo = new JoystickButton(m_driverJoystick, 1);
        turbo.whenPressed(new SetTurboDrivetrain(m_drivetrain, true));
        turbo.whenReleased(new SetTurboDrivetrain(m_drivetrain, false));

        /**
         * Driver's Joystick Axis
         *  -- Allows the driver to control the robot
         * 
         * Binding:
         *  -- Y (Axis 2) Controls speed +/-
         *  -- Z (Axis 3) Controls rotation
         */
        m_drivetrain.setDefaultCommand(new DrivetrainOperatorControl(this, m_drivetrain));

        /**
         * Driver's Stop All Command
         *  -- Stops all Active Subsystems on the Robot
         * 
         * Binding: 
         *  -- Button 4 (bottom left button @ top of joystick)
         */
        JoystickButton stopAll = new JoystickButton(m_driverJoystick, 4);
        stopAll.whenPressed(new StopAll(m_drivetrain, m_endgame, m_shooter, m_intake));

        /**
         * Align to Target Command
         *  -- Aligns Robot to Target
         * 
         * Binding:
         *  -- Button 5 (upper right button @ top of joystick)
         */
        JoystickButton alignToTarget = new JoystickButton(m_driverJoystick, 5);
        // TODO: Disabled for now until Limelight is installed
        // alignToTarget.whenPressed(new EnableTargetingAlignToTarget(m_drivetrain, vision));

        /**
         * Switch Left & Right Directions
         *  -- Switches Left & Right Directions
         *  -- Requested by Driveteam
         * 
         * Binding:
         *  -- Button 2 (button on side of axis)
         */
        JoystickButton switchDirections = new JoystickButton(m_driverJoystick, 2);
        switchDirections.whenPressed(new InvertDirections(this));

        /**
         * Override Match Timer
         *  -- Overrides Match Timer Lock for Endgaem
         *  -- Requested by Brad
         * 
         * Binding: 
         *  -- Button 11
         */
        JoystickButton overrideTimer = new JoystickButton(m_driverJoystick, 11);
        overrideTimer.whenPressed(new OverrideMatchTimer());
    }
    private void configureManipulatorBindings() {
        // Manipulator Xbox Controller
        m_manipulatorController = new XboxController(OIConstants.kXboxControllerPort);

// COMPRESSOR SUBSYSTEM

        /**
         * Start Compressor Command
         *  -- Starts the Compressor (for Pneumatics)
         * 
         * Binding:
         *  -- X button
         */
        JoystickButton startCompressor = new JoystickButton(m_manipulatorController, XboxController.Button.kX.value);
        //startCompressor.whenPressed(new StartCompressor(m_compressor));

        /**
         * Stop Compressor Command
         *  -- Stops the Compressor (for Pneumatics)
         * 
         * Binding:
         *  -- B button
         */
        JoystickButton stopCompressor = new JoystickButton(m_manipulatorController, XboxController.Button.kB.value);
       // stopCompressor.whenPressed(new StopCompressor(m_compressor));

// ENDGAME SUBSYSTEM
        /** 
         * Raise Endgame
         *  -- Raises Endgame
         * 
         * Binding:
         *  -- Up DPAD Button
         */
        DPadButton raiseEndgame = new DPadButton(m_manipulatorController, Direction.UP);
        raiseEndgame.whenPressed(new RaiseMotorWithDelayAndSafety(m_endgame));
        raiseEndgame.whenReleased(new StopEndgameWithDelay(m_endgame));


        DPadButton toggleEndgame = new DPadButton(m_manipulatorController, Direction.LEFT);
        toggleEndgame.whenPressed(new DisengageSafetyLock(m_endgame));
        toggleEndgame.whenReleased(new EngageSafetyLock(m_endgame));


        // NOTE ABOUT ENDGAME: STOPENDGAME AUTOMATICALLY LOCKS THE PNEUMATICS

        /**
         * Lower Endgame
         *  -- Lowers Endgame
         * 
         * Binding:
         *  -- Down DPAD Button
         */
        DPadButton lowerEndgame = new DPadButton(m_manipulatorController, Direction.DOWN);
        lowerEndgame.whenPressed(new LowerMotorWithDelayAndSafety(m_endgame));
        lowerEndgame.whenReleased(new StopEndgameWithDelay(m_endgame));

// Shooter Subsystem

        /**
         * Shoot Balls
         *  -- Shoots Balls
         * 
         * Binding:
         *  -- Right Trigger
         */
        Button shootBalls = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return m_manipulatorController.getRightTriggerAxis() > 0.8;
            }
        });
        shootBalls.whileHeld(new Shoot(m_shooter));
        //TODO: interrupted

// Intake Subsystem

        /**
         * Deploy Intake
         *  -- Deploys the Intake
         * 
         * Binding:
         *  -- Y
         */
        JoystickButton deployIntake = new JoystickButton(m_manipulatorController, XboxController.Button.kY.value);
        deployIntake.whenPressed(new DeployIntake(m_intake));

        /**
         * Retract Intake
         *  -- Retracts the Intake
         * 
         * Binding: 
         *  -- A
         */
        JoystickButton retractIntake = new JoystickButton(m_manipulatorController, XboxController.Button.kA.value);
        retractIntake.whenPressed(new RetractIntake(m_intake));

        /**
         * Activate Intake
         *  -- Activates the Intake
         * 
         * Binding: 
         *  -- Left Trigger
         */
        Button intakeBalls = Hardware.makeButton(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() {
                return m_manipulatorController.getLeftTriggerAxis() > 0.8;
            }
        });
        intakeBalls.whileHeld(new Intake(m_intake));

        /**
         * Outtake
         *  -- Outtakes Any Balls
         * 
         * Binding:
         *  -- Left Bumper
         */
        JoystickButton outtakeBalls = new JoystickButton(m_manipulatorController, XboxController.Button.kLeftBumper.value);
        outtakeBalls.whileHeld(new Outtake(m_intake));



    }

    public void startCompressor(){
       // m_compressor.start();
    }

    public void stopCompressor(){
      //  m_compressor.stop();
    }
}
