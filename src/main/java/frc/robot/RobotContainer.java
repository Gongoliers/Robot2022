// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.StopAll;

import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.EndgameSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.commands.drivetrain.DrivetrainOperatorControl;
import frc.robot.commands.drivetrain.SetTurboDrivetrain;
import frc.robot.commands.autonomous.EnableTargetingAlignToTarget;
import frc.robot.commands.autonomous.FullSystemCheck;

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
  private final SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  private Joystick m_driverJoystick;
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
    
    m_turbo = new JoystickButton(m_driverJoystick, 1);
    m_turbo.whenPressed(new SetTurboDrivetrain(m_drivetrainSubsystem, true));
    m_turbo.whenReleased(new SetTurboDrivetrain(m_drivetrainSubsystem, false));

    var stopAllCommand = new StopAll(m_drivetrainSubsystem, m_endgameSubsystem, m_shooterSubsystem);
    m_stopAll = new JoystickButton(m_driverJoystick, 11);
    m_stopAll.whenPressed(stopAllCommand);
    m_stopAll2 = new JoystickButton(m_driverJoystick, 12);
    m_stopAll2.whenPressed(stopAllCommand);
    
    m_alignToTarget = new JoystickButton(m_driverJoystick, 9);
    var alignToTargetCommand = new EnableTargetingAlignToTarget(m_drivetrainSubsystem, vision);
    m_alignToTarget.whenPressed(alignToTargetCommand);
    m_alignToTarget.whenReleased(alignToTargetCommand);

    // Default commands
    m_drivetrainSubsystem.setDefaultCommand(new DrivetrainOperatorControl(this, m_drivetrainSubsystem));

    // Auto chooser
    m_autoChooser.setDefaultOption("DO NOTHING", stopAllCommand);
    m_autoChooser.addOption("SYSTEM CHECK", new FullSystemCheck(m_drivetrainSubsystem));

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
