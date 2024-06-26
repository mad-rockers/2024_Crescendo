// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

// import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public final class Autos {
  /** Example static factory for an autonomous command. */
  //   public static Command exampleAuto(ExampleSubsystem subsystem)
  //   {
  //     return Commands.sequence(subsystem.exampleMethodCommand(), new ExampleCommand(subsystem));
  //   }
  public static Command shootThenMoveBack(
      ShooterSubsystem shooterSubsystem, DriveSubsystem neoMotorDriveSystem) {
    return Commands.sequence(
        new AutoShoot(shooterSubsystem, neoMotorDriveSystem),
        new AutoDrive(neoMotorDriveSystem, 0.85, 108));
  }

  public static Command shootMoveGrabMoveShoot(
      ShooterSubsystem shooterSubsystem, DriveSubsystem neoMotorDriveSystem) {
    return Commands.sequence(
        new AutoShoot(shooterSubsystem, neoMotorDriveSystem), // 5.5 seconds
        shooterSubsystem.runOnce(() -> shooterSubsystem.deployIntake()), // 1.0 seconds
        new Wait(0.5),
        new AutoDrive(neoMotorDriveSystem, 0.95, 72),
        new AutoDrive(neoMotorDriveSystem, 0.75, -72),
        // new Wait(1),
        shooterSubsystem.runOnce(() -> shooterSubsystem.stowIntake()),
        new Wait(0.5),
        // new LowerIntake(shooterSubsystem),
        // new Wait(0.15),
        // shooterSubsystem.runOnce(() -> shooterSubsystem.stowIntake()),
        new AutoShoot(shooterSubsystem, neoMotorDriveSystem), // 5.5 seconds
        new AutoDrive(neoMotorDriveSystem, 0.85, 72));
  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
