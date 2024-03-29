package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDrive extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private DriveSubsystem m_NeoMotorDriveSystem;

  private double moveSpeed;
  private double currentDistance_in = 0;
  private double targetDistance_in;
  private int isTargetForward;

  private boolean reachedTarget = false;

  public AutoDrive(DriveSubsystem neoMotorDriveSystem, double speed, double distance_ft) {
    m_NeoMotorDriveSystem = neoMotorDriveSystem;
    targetDistance_in = distance_ft * 12;
    moveSpeed = speed;
    addRequirements(m_NeoMotorDriveSystem);
  }

  @Override
  public void initialize() {
    m_NeoMotorDriveSystem.resetDistanceTraveled();
    isTargetForward =
        (int) (Math.abs(targetDistance_in) / targetDistance_in); // Gets the sign of the distance
    targetDistance_in = targetDistance_in * isTargetForward; // Absolutes the distance
  }

  @Override
  public void execute() {
    currentDistance_in += m_NeoMotorDriveSystem.getDistanceTraveled();
    m_NeoMotorDriveSystem.arcadeDrive(moveSpeed, 0);

    if (currentDistance_in > targetDistance_in) reachedTarget = true;

    SmartDashboard.putNumber("Current Distance (Inches):", currentDistance_in);
  }

  @Override
  public void end(boolean interrupted) {
    m_NeoMotorDriveSystem.stopMotors();
  }

  @Override
  public boolean isFinished() {
    return reachedTarget;
  }
}
