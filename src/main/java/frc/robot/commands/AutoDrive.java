package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class AutoDrive extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private DriveSubsystem m_NeoMotorDriveSystem;

  /*
   * Note: the moveSpeed affects distance travelled.
   * The robot will coast, so it'll go farther than the targetDistance.
   */
  private double moveSpeed;
  private double currentDistance_in = 0;
  private double targetDistance;
  // private int isTargetForward;

  private boolean reachedTarget = false;

  public AutoDrive(
      DriveSubsystem neoMotorDriveSystem, double moveSpeedIn, double targetDistanceIn) {
    m_NeoMotorDriveSystem = neoMotorDriveSystem;
    moveSpeed = moveSpeedIn;
    targetDistance = targetDistanceIn;
    addRequirements(m_NeoMotorDriveSystem);
  }

  @Override
  public void initialize() {
    m_NeoMotorDriveSystem.resetDistanceTraveled();
    // isTargetForward =
    //     (int) (Math.abs(targetDistance) / targetDistance); // Gets the sign of the distance
    // targetDistance = targetDistance * isTargetForward; // Absolutes the distance
  }

  @Override
  public void execute() {
    currentDistance_in += m_NeoMotorDriveSystem.getDistanceTraveled();
    if (targetDistance > 0) {
      m_NeoMotorDriveSystem.arcadeDrive(moveSpeed, 0);
    } else {
      m_NeoMotorDriveSystem.arcadeDrive(-moveSpeed, 0);
    }

    if (targetDistance > 0) {
      if (currentDistance_in > targetDistance) {
        reachedTarget = true;
      }
    } else {
      if (currentDistance_in < targetDistance) {
        reachedTarget = true;
      }
    }

    SmartDashboard.putNumber("Current Distance (Inches):", currentDistance_in);
  }

  @Override
  public void end(boolean interrupted) {
    m_NeoMotorDriveSystem.stopMotors();
    reachedTarget = false;
    currentDistance_in = 0;
  }

  @Override
  public boolean isFinished() {
    return reachedTarget;
  }
}
