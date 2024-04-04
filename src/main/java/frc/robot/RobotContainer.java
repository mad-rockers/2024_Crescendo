// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.AutoDrive;
import frc.robot.commands.Autos;
import frc.robot.commands.LowerIntake;
import frc.robot.commands.ResetIntake;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotContainer {
  CommandXboxController mController =
      new CommandXboxController(ControllerConstants.kDriverControllerPort);

  CommandXboxController mOperator =
      new CommandXboxController(
          ControllerConstants
              .kOperatorControllerPort); // This is for the second controller the oeprator uses

  DriveSubsystem mDriveSubsystem = new DriveSubsystem();
  ShooterSubsystem mShooterSubsystem = new ShooterSubsystem();
  CameraSubsystem mCameraSubsystem = new CameraSubsystem();

  private final Command m_shootAndMoveBack =
      Autos.shootThenMoveBack(mShooterSubsystem, mDriveSubsystem);

  private final Command m_justMoveBack = new AutoDrive(mDriveSubsystem, 0.85, 72);

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  public RobotContainer() {
    // Add commands to the autonomous command chooser
    m_chooser.setDefaultOption("Shoot and Move Back", m_shootAndMoveBack);
    m_chooser.addOption("Just Move Back", m_justMoveBack);

    // Put the chooser on the dashboard
    SmartDashboard.putData(m_chooser);

    configureBindings();
  }

  private void configureBindings() {
    mDriveSubsystem.setDefaultCommand(
        mDriveSubsystem.run(
            () -> mDriveSubsystem.arcadeDrive(mController.getLeftY(), mController.getRightX())));

    mController.a().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.stowIntake()));
    mController.b().onTrue(new LowerIntake(mShooterSubsystem));
    mController.rightTrigger().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.shoot()));
    mController
        .leftTrigger()
        .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.startFrontShooterMotor()));
    mController.x().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.stopAllMotors()));
    mController.y().onTrue(new ResetIntake(mShooterSubsystem));

    // Operator capabilities for adjustments
    mOperator
        .leftTrigger()
        .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.decrementIntakeLiftPosition()));
    mOperator
        .rightTrigger()
        .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.incrementIntakeLiftPosition()));

    mOperator
        .leftBumper()
        .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.setEncoderToNegativeFifty()));
    mOperator
        .rightBumper()
        .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.setEncoderToZero()));
  }

  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
