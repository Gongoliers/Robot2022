package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.StopAll;
import frc.robot.commands.autonomous.EnableTargetingAlignToTarget;
import frc.robot.commands.compressor.StartCompressor;
import frc.robot.commands.compressor.StopCompressor;
import frc.robot.commands.drivetrain.SetTurboDrivetrain;
import frc.robot.commands.endgame.DisengageSafetyLock;
import frc.robot.commands.endgame.EngageSafetyLock;
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
    private final CompressorSubsystem m_compressor = new CompressorSubsystem();

    /**
     * Initiating SendableChooser
     */
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();

    /**
     * Initiating Controllers
     */
    private Joystick m_driverJoystick;
    private XboxController m_manipulatorController;

    /**
     * The container for the robot. 
     * Contains subsystems, OI devices, and commands
     */
    public RobotContainer() {
        // Initialize button bindings
        configureDriverBindings();
    }

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
         * Driver's Stop All Command
         *  -- Stops all Active Subsystems on the Robot
         * 
         * Binding: 
         *  -- TODO: CONFIGURE BINDING
         */
        JoystickButton stopAll = new JoystickButton(m_driverJoystick, 0);
        stopAll.whenPressed(new StopAll(m_drivetrain, m_endgame, m_shooter, m_intake));

        /**
         * Align to Target Command
         *  -- Aligns Robot to Target
         * 
         * Binding:
         *  -- TODO: CONFIGURE BINDING
         */
        JoystickButton alignToTarget = new JoystickButton(m_driverJoystick, 0);
        alignToTarget.whenPressed(new EnableTargetingAlignToTarget(m_drivetrain, vision));

        /**
         * 
         */
    }
    private void configureManipulatorBindings() {
        // Manipulator Xbox Controller
        m_manipulatorController = new XboxController(OIConstants.kXboxControllerPort);

        /**
         * Start Compressor Command
         *  -- Starts the Compressor (for Pneumatics)
         * 
         * Binding:
         *  -- A button
         */
        JoystickButton startCompressor = new JoystickButton(m_manipulatorController, Button.kA.value);
        startCompressor.whenPressed(new StartCompressor(m_compressor));

        /**
         * Stop Compressor Command
         *  -- Stops the Compressor (for Pneumatics)
         * 
         * Binding:
         *  -- B button
         */
        JoystickButton stopCompressor = new JoystickButton(m_manipulatorController, Button.kB.value);
        stopCompressor.whenPressed(new StopCompressor(m_compressor));

        /**
         * Toggle Endgame Lock
         *  -- Engages/Disengages Endgame Safety
         * 
         * Binding:
         *  -- Left Bumper button
         */
        JoystickButton toggleEndgameSafety = new JoystickButton(m_manipulatorController, Button.kLeftBumper.value);
        toggleEndgameSafety.whenPressed();




    }
}
