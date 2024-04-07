package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class LowerIntake extends Command {
  public ShooterSubsystem m_ShooterSubsystem;
  public Timer m_Timer;

  public boolean m_isFinished = false;
  public boolean m_isTimerStarted = false;

  public LowerIntake(ShooterSubsystem shooterSubsystem) {
    m_Timer = new Timer();
    m_ShooterSubsystem = shooterSubsystem;
    addRequirements(m_ShooterSubsystem);
  }

  @Override
  public void initialize() {
    m_Timer.stop();
    m_Timer.reset();
    m_ShooterSubsystem.deployIntake();
  }

  @Override
  public void execute() {
    if (!m_isTimerStarted
        && m_ShooterSubsystem.getLeftRecieverSwitch()
        && m_ShooterSubsystem.getRightRecieverSwitch()) {
      m_Timer.start();
      m_isTimerStarted = true;
    } else if (m_isTimerStarted && m_Timer.get() > 1.0) {
      m_ShooterSubsystem.stopAllMotors("front_shooter_motor");
      m_isFinished = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_Timer.stop();
    m_Timer.reset();
    m_isTimerStarted = false;
    m_isFinished = false;
  }

  @Override
  public boolean isFinished() {
    return m_isFinished;
  }
}
