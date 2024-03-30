package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class LowerIntake extends Command {
  public ShooterSubsystem m_ShooterSubsystem;

  public boolean m_isFinished = false;

  public LowerIntake(ShooterSubsystem shooterSubsystem) {
    m_ShooterSubsystem = shooterSubsystem;
    addRequirements(m_ShooterSubsystem);
  }

  @Override
  public void initialize() {
    m_ShooterSubsystem.deployIntake();
  }

  @Override
  public void execute() {
    if (m_ShooterSubsystem.getLeftRecieverSwitch()
        || m_ShooterSubsystem.getLeftRecieverSwitch()
        || m_ShooterSubsystem.getLimitSwitchPressed()) {
      m_ShooterSubsystem.stopAllMotors();
      m_isFinished = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_isFinished = false;
  }

  @Override
  public boolean isFinished() {
    return m_isFinished;
  }
}
