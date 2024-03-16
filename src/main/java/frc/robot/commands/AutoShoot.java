package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShoot extends Command {

  private ShooterSubsystem mShooterSubsystem;
  private Timer mTimer = new Timer();
  private boolean mIsFinished = false;

  public AutoShoot(ShooterSubsystem shooterSubsystemIn) {
    mShooterSubsystem = shooterSubsystemIn;
  }

  @Override
  public void initialize() {
    mTimer.stop();
    mTimer.reset();
    mTimer.start();
  }

  @Override
  public void execute() {
    mShooterSubsystem.startFrontShooterMotor();

    if (mTimer.get() > 2) {
      mShooterSubsystem.shoot();
    }

    if (mTimer.get() > 3) {
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
