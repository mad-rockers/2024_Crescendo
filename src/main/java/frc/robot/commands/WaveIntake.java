package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.ShooterSubsystem;

public class WaveIntake extends Command {
  private final ShooterSubsystem mShooterSubsystem;
  private boolean isLifted = false;
  private long lastToggleTime = 0;

  public WaveIntake(ShooterSubsystem shooterSubsystem) {
    mShooterSubsystem = shooterSubsystem;
    addRequirements(mShooterSubsystem);
  }

  @Override
  public void initialize() {
    lastToggleTime = System.currentTimeMillis();
  }

  @Override
  public void execute() {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastToggleTime >= ShooterConstants.WAVING_TOGGLE_INTERVAL_MS) {
      if (isLifted) {
        mShooterSubsystem.stowIntake();
      } else {
        mShooterSubsystem.deployIntake();
      }
      isLifted = !isLifted;
      lastToggleTime = currentTime;
    }
  }

  @Override
  public void end(boolean interrupted) {
    mShooterSubsystem.stopAllMotors();
  }

  @Override
  public boolean isFinished() {
    return false; // Run until explicitly interrupted
  }
}
