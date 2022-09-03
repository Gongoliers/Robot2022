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
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.DPadButton.Direction;
import frc.robot.commands.StopAll;
import frc.robot.commands.autonomous.BackupAndShoot;
import frc.robot.commands.autonomous.ShootLowThenLeaveTarmac;
import frc.robot.commands.autonomous.BackupAndShootHighThenLeaveTarmac;
import frc.robot.commands.autonomous.LeaveTarmac;
import frc.robot.commands.autonomous.LeaveTarmacAndShoot;
import frc.robot.commands.autonomous.LeaveTarmacAndShootLow;
import frc.robot.commands.compressor.StartCompressor;
import frc.robot.commands.compressor.StopCompressor;
import frc.robot.commands.drivetrain.DrivetrainOperatorControl;
import frc.robot.commands.drivetrain.InvertDirections;
import frc.robot.commands.drivetrain.SetTurboDrivetrain;
import frc.robot.commands.drivetrain.SimpleDriveDistance;
import frc.robot.commands.drivetrain.StopDrivetrain;
import frc.robot.commands.endgame.AscendEndgame;
import frc.robot.commands.endgame.DescendEndgame;
import frc.robot.commands.endgame.HomeEndgame;
import frc.robot.commands.endgame.StopEndgame;
import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.intake.Intake;
import frc.robot.commands.intake.Outtake;
import frc.robot.commands.intake.RetractIntake;
import frc.robot.commands.shooter.ShootHigh;
import frc.robot.commands.shooter.ShootLow;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.subsystems.CompressorSubsystem;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VisionSubsystem;

public class RobotContainer {
    /**
     * Initializing VisionSystem
     */
    private final VisionSubsystem vision = new VisionSubsystem();

    /**
     * Initiating DrivetrainSubsystem
     */
    private final Drivetrain mDrivetrain = new Drivetrain(vision);

    /**
     * Initiating EndgameSubsystem
     */
    private final EndgameSubsystem m_endgame = new EndgameSubsystem();

    /**
     * Initiating ShooterSubsystem
     */
    private final Shooter mShooter = new Shooter();

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
        configureSmartDashboard();

        // m_intake.setDefaultCommand(new StopIntake(m_intake));
        // m_shooter.setDefaultCommand(new StopShooter(m_shooter));
    }

    /**
     * This function adds all Autonomous Options to the SmartDashboard
     */
    private void configureAutonomous() {
        autoChooser.addOption("Do Nothing", new StopAll(mDrivetrain, m_endgame, mShooter, m_intake));
        autoChooser.addOption("Leave Tarmac", new LeaveTarmac(mDrivetrain));
        autoChooser.addOption("Shoot Low then Leave Tarmac", new ShootLowThenLeaveTarmac(mDrivetrain, mShooter));
        autoChooser.addOption("Shoot High then Leave Tarmac", new BackupAndShootHighThenLeaveTarmac(mDrivetrain, mShooter));
        autoChooser.addOption("Leave Tarmac Shoot High", new LeaveTarmacAndShoot(mDrivetrain, mShooter, m_intake));
        autoChooser.addOption("Leave Tarmac Shoot Low", new LeaveTarmacAndShootLow(mDrivetrain, mShooter, m_intake));
        autoChooser.setDefaultOption("Leave Tarmac Shoot High", new LeaveTarmacAndShoot(mDrivetrain, mShooter, m_intake));

        SmartDashboard.putData("Auto mode", autoChooser);
    }

    /**
     * This function adds custom options to smartdashboard
     */
    private void configureSmartDashboard() {
        SmartDashboard.putData("Reset Endgame", new DescendEndgame(m_endgame));
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

     public void stopAll() {
         new StopAll(mDrivetrain, m_endgame, mShooter, m_intake).schedule();
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
        turbo.whenPressed(new SetTurboDrivetrain(mDrivetrain, true));
        turbo.whenReleased(new SetTurboDrivetrain(mDrivetrain, false));

        /**
         * Driver's Joystick Axis
         *  -- Allows the driver to control the robot
         *
         * Binding:
         *  -- Y (Axis 2) Controls speed +/-
         *  -- Z (Axis 3) Controls rotation
         */
        mDrivetrain.setDefaultCommand(new DrivetrainOperatorControl(this, mDrivetrain));

        /**
         * Driver's Stop All Command
         *  -- Stops all Active Subsystems on the Robot
         *
         * Binding:
         *  -- Button 4 (bottom left button @ top of joystick)
         */
        JoystickButton stopAll = new JoystickButton(m_driverJoystick, 4);
        stopAll.whenPressed(new StopAll(mDrivetrain, m_endgame, mShooter, m_intake));

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
         * Backup for High Goal
         *  -- Backs Up from Fender to Shoot for high goal
         *
         * Binding:
         *  -- Button 10
         */
        // TODO: Use this
        var backup = new SimpleDriveDistance(mDrivetrain, AutoConstants.kDistanceToDriveForHigh, 0.05, 0.3).withTimeout(3.0);
        JoystickButton positionForHigh = new JoystickButton(m_driverJoystick, 10);
        positionForHigh.whenPressed(backup);

        /**
         * Override Match Timer
         *  -- Overrides Match Timer Lock for Endgaem
         *  -- Reevaluate if this is necessary
         *
         * Binding:
         *  -- Button 11
         */
        //JoystickButton overrideTimer = new JoystickButton(m_driverJoystick, 11);
        //overrideTimer.whenPressed(new ResetEndgameOverride());

        /**
         * Backup and Shoot
         *  -- Backups and Shoot
         *
         * Binding:
         *  -- Button ??
         */
        JoystickButton manipulatorBackupFootandShoot = new JoystickButton(m_driverJoystick, 5);
        manipulatorBackupFootandShoot.whenPressed(new BackupAndShoot(mDrivetrain, mShooter));
        manipulatorBackupFootandShoot.whenReleased(new ParallelCommandGroup(new StopDrivetrain(mDrivetrain), new StopShooter(mShooter)));

        /**
         * Shoot High
         *  -- Shoots High
         *
         * Binding:
         *  -- Button ???
         */
        JoystickButton manipulatorShoot = new JoystickButton(m_driverJoystick, 3);
        manipulatorShoot.whileHeld(new ShootHigh(mShooter));
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
        startCompressor.whenPressed(new StartCompressor(m_compressor));
        startCompressor.whenReleased(new StopCompressor(m_compressor));
        //startCompressor.whenReleased(new SequentialCommandGroup(new StopCompressor(m_compressor), new StartLimitedCompressor(m_compressor, this)));

        /**
         * Stop Compressor Command
         *  -- Stops the Compressor (for Pneumatics)
         *
         * Binding:
         *  -- B button
         */
        JoystickButton stopCompressor = new JoystickButton(m_manipulatorController, XboxController.Button.kB.value);
        stopCompressor.whenPressed(new StopCompressor(m_compressor));

// ENDGAME SUBSYSTEM

        // TODO: See if this is needed
        // m_endgame.setDefaultCommand(new StopEndgame(m_endgame));

        /**
         * Raise Endgame
         *  -- Raises Endgame
         *
         * Binding:
         *  -- Up DPAD Button
         */
        DPadButton raiseEndgame = new DPadButton(m_manipulatorController, Direction.UP);
        raiseEndgame.whenPressed(new AscendEndgame(m_endgame));
        raiseEndgame.whenReleased(new StopEndgame(m_endgame));

        // NOTE ABOUT ENDGAME: STOPENDGAME AUTOMATICALLY LOCKS THE PNEUMATICS

        /**
         * Lower Endgame
         *  -- Lowers Endgame
         *
         * Binding:
         *  -- Down DPAD Button
         */
        DPadButton lowerEndgame = new DPadButton(m_manipulatorController, Direction.DOWN);
        lowerEndgame.whenPressed(new DescendEndgame(m_endgame));
        // TODO: Evaluate if this line is necessary
        lowerEndgame.whenReleased(new StopEndgame(m_endgame));

        DPadButton homeEndgame = new DPadButton(m_manipulatorController, Direction.LEFT);
        homeEndgame.whenPressed(new HomeEndgame(m_endgame));
        homeEndgame.whenReleased(new StopEndgame(m_endgame));

        /**
         * Break Safety
         *  -- Disables timer safety
         *  -- Re-add if necessary
         *
         * Binding:
         *  -- Right DPAD Button
         */
        //Trigger endgameEnabled = new Trigger(EndgameTimer::isTimerBroken).negate();
        //Trigger overrideEndgame = new DPadButton(m_manipulatorController, Direction.RIGHT).and(endgameEnabled);
        //overrideEndgame.whenActive(new OverrideEndgame());

        //Trigger endgameDisabled = new Trigger(EndgameTimer::isTimerBroken);
        //Trigger overrideEncoders = new DPadButton(m_manipulatorController, Direction.RIGHT).and(endgameDisabled);
        //overrideEncoders.whenActive(new DisableEncoderChecks(m_endgame));

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
        shootBalls.whileHeld(new ShootHigh(mShooter));

        /**
         * Shoot Balls Low
         *  -- Shoots Balls
         *
         * Binding:
         *  -- Right Bumper
         */
        JoystickButton shootBallsLow = new JoystickButton(m_manipulatorController, XboxController.Button.kRightBumper.value);
        shootBallsLow.whileHeld(new ShootLow(mShooter));

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
        m_compressor.start();
    }

    public void stopCompressor(){
        m_compressor.stop();
    }
}
