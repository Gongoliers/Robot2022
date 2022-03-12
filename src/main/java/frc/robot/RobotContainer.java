// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.StopAll;
import frc.robot.subsystems.CompressorSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.commands.drivetrain.DrivetrainOperatorControl;
import frc.robot.commands.drivetrain.SetTurboDrivetrain;
import frc.robot.commands.endgame.DisengageSafetyLock;
import frc.robot.commands.endgame.EngageSafetyLock;
import frc.robot.commands.endgame.LowerMotor;
import frc.robot.commands.endgame.RaiseMotor;
import frc.robot.commands.shooter.Shoot;
import frc.robot.commands.autonomous.EnableTargetingAlignToTarget;
import frc.robot.commands.autonomous.FullSystemCheck;
import frc.robot.commands.autonomous.LeaveTarmac;
import frc.robot.commands.autonomous.LeaveTarmacAndShoot;
import frc.robot.commands.compressor.StartCompressor;
import frc.robot.commands.compressor.StopCompressor;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final VisionSubsystem vision = new VisionSubsystem();
  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem(vision);
  private final EndgameSubsystem m_endgameSubsystem = new EndgameSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  private final CompressorSubsystem m_compressor = new CompressorSubsystem();
  private final SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  private Joystick m_driverJoystick;
  private XboxController m_xbox;
  private JoystickButton m_turbo, m_stopAll, m_stopAll2, m_alignToTarget;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Driver Joystick Buttons
    m_driverJoystick = new Joystick(Constants.OIConstants.kDriverControllerPort);
    m_xbox = new XboxController(Constants.OIConstants.kXboxControllerPort);
    
    m_turbo = new JoystickButton(m_driverJoystick, 1);
    m_turbo.whenPressed(new SetTurboDrivetrain(m_drivetrainSubsystem, true));
    m_turbo.whenReleased(new SetTurboDrivetrain(m_drivetrainSubsystem, false));

    var stopAllCommand = new StopAll(m_drivetrainSubsystem, m_endgameSubsystem, m_shooterSubsystem, m_intakeSubsystem);
    m_stopAll = new JoystickButton(m_driverJoystick, 11);
    m_stopAll.whenPressed(stopAllCommand);
    m_stopAll2 = new JoystickButton(m_driverJoystick, 12);
    m_stopAll2.whenPressed(stopAllCommand);
    
    m_alignToTarget = new JoystickButton(m_driverJoystick, 9);
    var alignToTargetCommand = new EnableTargetingAlignToTarget(m_drivetrainSubsystem, vision);
    m_alignToTarget.whenPressed(alignToTargetCommand);
    m_alignToTarget.whenReleased(alignToTargetCommand);

    var startCompressorButton = new JoystickButton(m_xbox, Button.kA.value);
    startCompressorButton.whenPressed(new StartCompressor(m_compressor));

    var stopCompressorButton = new JoystickButton(m_xbox, Button.kB.value);
    stopCompressorButton.whenPressed(new StopCompressor(m_compressor));

    // TODO: This is temporary for testing purposes
    var shootButton = new JoystickButton(m_driverJoystick, 7);
    shootButton.whileHeld(new Shoot(m_shooterSubsystem));

    JoystickButton m_raise_endgame = new JoystickButton(m_driverJoystick, 7);
    var raiseEndgameCommand = new RaiseMotor(m_endgameSubsystem);
    var engageSafetyCommand = new EngageSafetyLock(m_endgameSubsystem);
    var disengageSafetyCommand = new DisengageSafetyLock(m_endgameSubsystem);
    m_raise_endgame.whenPressed(disengageSafetyCommand);
    m_raise_endgame.whileHeld(raiseEndgameCommand);
    m_raise_endgame.whenReleased(engageSafetyCommand);

    // TODO: CONFIGURE THIS => SEPARATE disengageSafety / engageSafety
    JoystickButton m_lower_endgame = new JoystickButton(m_driverJoystick, 8);
    var lowerEndgamecommand = new LowerMotor(m_endgameSubsystem);
    m_lower_endgame.whenPressed(disengageSafetyCommand);
    m_lower_endgame.whileHeld(lowerEndgamecommand);
    m_lower_endgame.whenReleased(engageSafetyCommand);

    // Default commands
    m_drivetrainSubsystem.setDefaultCommand(new DrivetrainOperatorControl(this, m_drivetrainSubsystem));

    // Auto chooser
    m_autoChooser.setDefaultOption("Do Nothing", stopAllCommand);
    m_autoChooser.addOption("System Check", new FullSystemCheck(m_drivetrainSubsystem));
    m_autoChooser.addOption("Leave Tarmac", new LeaveTarmac(m_drivetrainSubsystem));
    m_autoChooser.addOption("Leave Tarmac Shoot", new LeaveTarmacAndShoot(m_drivetrainSubsystem, m_shooterSubsystem));

    SmartDashboard.putData("Auto mode", m_autoChooser);
  }

  public double getDriverSpeed() {
     return m_driverJoystick.getY();
  }

  public double getDriverRotation() {
     return m_driverJoystick.getZ();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autoChooser.getSelected();
  }

}
