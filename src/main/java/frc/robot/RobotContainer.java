// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ControllerConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotContainer {
  CommandXboxController mController =
      new CommandXboxController(ControllerConstants.kDriverControllerPort);

  DriveSubsystem mDriveSubsystem = new DriveSubsystem();
  ShooterSubsystem mShooterSubsystem = new ShooterSubsystem();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    mDriveSubsystem.setDefaultCommand(
        mDriveSubsystem.run(
            () ->
                mDriveSubsystem.arcadeDrive(
                    mController.getLeftY() * ControllerConstants.kDriveSpeed,
                    mController.getRightX() * ControllerConstants.kDriveSpeed)));

    mController.a().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.stowIntake()));
    mController.b().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.deployIntake()));
    mController.y().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.shoot()));
    mController.x().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.stopAllMotors()));
    mController.rightBumper().onTrue(mShooterSubsystem.runOnce(() -> mShooterSubsystem.reset()));
    // mController.x().onTrue(
    //   mShooterSubsystem.runOnce(
    //     () -> mShooterSubsystem.reset()
    //   )
    // );
    /*mController.a().whileTrue(
      mShooterSubsystem.run(
        () -> mShooterSubsystem.intake()
      )
    );
    mController.b().whileTrue(
      mShooterSubsystem.run(
        () -> mShooterSubsystem.shoot()
      )
    );
    mController.rightBumper().whileTrue(
      mShooterSubsystem.run(
        () -> mShooterSubsystem.deployIntake()
      )
    );
    mController.leftBumper().whileTrue(
      mShooterSubsystem.run(
        () -> mShooterSubsystem.stowIntake()
      )
    );
    mShooterSubsystem.setDefaultCommand(
      mShooterSubsystem.run(
        () -> mShooterSubsystem.stop()
      )
    );*/
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
