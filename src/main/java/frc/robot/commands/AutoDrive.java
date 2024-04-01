package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDrive extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private DriveSubsystem m_NeoMotorDriveSystem;

  /*
   * Note: the MOVE_SPEED affects distance travelled.
   * The robot will coast, so it'll go farther than the targetDistance.
   */
  private static final double MOVE_SPEED = 0.85;
  private double currentDistance_in = 0;
  private double targetDistance = 72;
  private int isTargetForward;

  private boolean reachedTarget = false;

  public AutoDrive(DriveSubsystem neoMotorDriveSystem) {
    m_NeoMotorDriveSystem = neoMotorDriveSystem;
    addRequirements(m_NeoMotorDriveSystem);
  }

  @Override
  public void initialize() {
    m_NeoMotorDriveSystem.resetDistanceTraveled();
    isTargetForward =
        (int) (Math.abs(targetDistance) / targetDistance); // Gets the sign of the distance
    targetDistance = targetDistance * isTargetForward; // Absolutes the distance
  }

  @Override
  public void execute() {
    currentDistance_in += m_NeoMotorDriveSystem.getDistanceTraveled();
    m_NeoMotorDriveSystem.arcadeDrive(MOVE_SPEED, 0);

    if (currentDistance_in > targetDistance) reachedTarget = true;

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
