package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShoot extends Command {

  private ShooterSubsystem mShooterSubsystem;
  private DriveSubsystem mDriveSubsystem;
  private Timer mTimer = new Timer();
  private boolean mIsFinished = false;

  public AutoShoot(ShooterSubsystem shooterSubsystemIn, DriveSubsystem driveSubsystemIn) {
    mShooterSubsystem = shooterSubsystemIn;
    mDriveSubsystem = driveSubsystemIn;
  }

  @Override
  public void initialize() {
    mTimer.stop();
    mTimer.reset();
    mTimer.start();
  }

  @Override
  public void execute() {
    mDriveSubsystem.arcadeDrive(0, 0);
    mShooterSubsystem.startFrontShooterMotor();

    if (mTimer.get() > 2) {
      mShooterSubsystem.shoot();
    }

    if (mTimer.get() > 5) {
      mIsFinished = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    mShooterSubsystem.stopAllMotors();
  }

  @Override
  public boolean isFinished() {
    return mIsFinished;
  }
}
