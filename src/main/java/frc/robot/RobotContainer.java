// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ControllerConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotContainer {
  CommandXboxController mController =
      new CommandXboxController(ControllerConstants.kDriverControllerPort);

  DriveSubsystem mDriveSubsystem = new DriveSubsystem();
  ShooterSubsystem mShooterSubsystem = new ShooterSubsystem();
  CameraSubsystem mCameraSubsystem = new CameraSubsystem();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    mDriveSubsystem.setDefaultCommand(
        mDriveSubsystem.run(
            () ->
                mDriveSubsystem.arcadeDrive(
                    mController.getLeftY(),
                    mController.getRightX())));

    mController.a().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.stowIntake()));
    mController.b().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.deployIntake()));
    mController.rightTrigger().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.shoot()));
    mController
        .leftTrigger()
        .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.startFrontShooterMotor()));
    mController.x().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.stopAllMotors()));
    mController.leftBumper().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.reset()));
    mController
        .rightBumper()
        .onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.getIntakeMostOfTheWayDown()));
  }

  public Command getAutonomousCommand() {
    return Autos.moveBack(mDriveSubsystem);
  }
}
