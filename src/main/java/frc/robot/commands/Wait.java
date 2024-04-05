package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class Wait extends Command {
  public ShooterSubsystem m_ShooterSubsystem;
  public Timer m_Timer;

  public double m_HowLong;
  public boolean m_isFinished = false;

  public Wait(double howLongIn) {
    m_Timer = new Timer();
    m_HowLong = howLongIn;
  }

  @Override
  public void initialize() {
    m_Timer.stop();
    m_Timer.reset();
    m_Timer.start();
  }

  @Override
  public void execute() {
    if (m_Timer.get() > 2.0) {
      m_isFinished = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    m_Timer.stop();
    m_Timer.reset();
    m_isFinished = false;
  }

  @Override
  public boolean isFinished() {
    return m_isFinished;
  }
}
