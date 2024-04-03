package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class ResetIntake extends Command {
  public ShooterSubsystem m_ShooterSubsystem;

  public boolean m_isFinished;

  public ResetIntake(ShooterSubsystem shooterSubsystem) {
    m_ShooterSubsystem = shooterSubsystem;
    m_isFinished = false;
    addRequirements(m_ShooterSubsystem);
  }

  @Override
  public void initialize() {
    m_ShooterSubsystem.stopAllMotors();
    m_ShooterSubsystem.goToPositionTen();
  }

  @Override
  public void execute() {
    if (m_ShooterSubsystem.getLimitSwitchPressed()) {
      m_ShooterSubsystem.stopAllMotors();
      m_ShooterSubsystem.setEncoderToZero();
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
