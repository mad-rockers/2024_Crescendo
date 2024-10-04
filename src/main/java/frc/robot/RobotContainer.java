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
import frc.robot.commands.AutoShoot;
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

  private final Command m_shootMoveShootMoveShoot =
      Autos.shootMoveGrabMoveShoot(mShooterSubsystem, mDriveSubsystem);

  private final Command m_justMoveBack = new AutoDrive(mDriveSubsystem, 0.85, 72);

  private final Command m_justShoot = new AutoShoot(mShooterSubsystem, mDriveSubsystem);

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  public RobotContainer() {
    // Add commands to the autonomous command chooser
    m_chooser.setDefaultOption("Go for Broke", m_shootMoveShootMoveShoot);
    m_chooser.addOption("Just Move Back", m_justMoveBack);
    m_chooser.addOption("Shoot Once and Move Back", m_shootAndMoveBack);
    m_chooser.addOption("Just Shoot", m_justShoot);

    // Put the chooser on the dashboard
    SmartDashboard.putData(m_chooser);

    configureBindings();
  }

  private void configureBindings() {
    // Temporarily removing the drive system
    // mDriveSubsystem.setDefaultCommand(
    //     mDriveSubsystem.run(
    //         () -> mDriveSubsystem.arcadeDrive(mController.getLeftY(), mController.getRightX())));

    mShooterSubsystem.setDefaultCommand(
        mShooterSubsystem.run(() -> mShooterSubsystem.startFrontShooterMotor()));

    mController.a().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.stowIntake()));
    mController.b().onTrue(new LowerIntake(mShooterSubsystem));
    mController.rightTrigger().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.shoot()));
    mController
        .leftTrigger()
        .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.startFrontShooterMotor()));
    mController.x().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.stopAllMotors()));
    mController.y().onTrue(new ResetIntake(mShooterSubsystem));

    // Using the driver controller's left and right joystick buttons for encoder settings
    mController
        .leftStick()
        .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.setEncoderToNegativeFifty()));
    mController
        .rightStick()
        .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.setEncoderToZero()));

    // Use Start and Back buttons to raise and lower intake lift position
    mController.start().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.incrementIntakeLiftPosition()));
    mController.back().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.decrementIntakeLiftPosition()));

    // Operator control system temporarily removed
    // mOperator
    //     .leftTrigger()
    //     .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.decrementIntakeLiftPosition()));
    // mOperator
    //     .rightTrigger()
    //     .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.incrementIntakeLiftPosition()));
    }

    public Command getAutonomousCommand() {
        return m_chooser.getSelected();
        // return Autos.shootMoveGrabMoveShoot(mShooterSubsystem, mDriveSubsystem);
    }
}
