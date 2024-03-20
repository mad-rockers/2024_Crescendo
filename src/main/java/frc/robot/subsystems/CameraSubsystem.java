package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CameraSubsystem extends SubsystemBase {
  /*
   * Helpful Resources:
   * Limelight Docs: https://readthedocs.org/projects/limelight/downloads/pdf/latest/
   * Limelight Complete NetworkTable: https://docs.limelightvision.io/docs/docs-limelight/apis/complete-networktables-api
   */

  // NetworkTable entries for Limelight
  private final NetworkTable m_limelightTable =
      NetworkTableInstance.getDefault().getTable("limelight");

  // Angle info
  // tx -> angle left or right from the camera
  // ty -> angle up for down from the camera
  // ta -> area on screen the apriltag takes up
  // tv -> check whether target is valid
  private final NetworkTableEntry m_tx = m_limelightTable.getEntry("tx");
  private final NetworkTableEntry m_ty = m_limelightTable.getEntry("ty");
  private final NetworkTableEntry m_ta = m_limelightTable.getEntry("ta");
  private final NetworkTableEntry m_tv = m_limelightTable.getEntry("tv");
  private final NetworkTableEntry m_tid = m_limelightTable.getEntry("tid");

  private final NetworkTableEntry m_TargetPoseRS =
      m_limelightTable.getEntry("targetpose_robotspace");

  /*
   * Constant heights for various parts of the distance equation.
   */
  private static final double CAMERA_HEIGHT =
      28.25; // Height of the camera from the ground in inches
  private static final double CAMERA_ANGLE =
      Constants.CameraConstants.CameraData[4]; // Angle of the camera in degrees

  public CameraSubsystem() {

    // All of these values need to be in meters.

    NetworkTableInstance.getDefault()
        .getTable("limelight")
        .getEntry("camerapose_robotspace_set")
        .setDoubleArray(Constants.CameraConstants.CameraData);
  }

  /// GETTERS///

  /*
   * NOTE:
   * All of these values should return from the center of the robot assuming that the cameraData has been set up properly, see above.
   * All units will be in meters.
   *
   */

  // Angle Offsets
  public double getXAngleOffset() {
    return m_tx.getDouble(0.0);
  }

  public double getYAngleOffset() {
    return m_ty.getDouble(0.0);
  }

  // Distance of the april tag horizontally from the robot.
  public double getXDistOffset() {
    return m_TargetPoseRS.getDoubleArray(new double[6])[0];
  }

  // Distance of the tag vertically from the robot.
  public double getYDistOffset() {
    return m_TargetPoseRS.getDoubleArray(new double[6])[1];
  }

  // Ditsance of the tag forward from the robot.
  public double getZDistOffset() {
    return m_TargetPoseRS.getDoubleArray(new double[6])[2];
  }

  // Pitch of the tag
  public double getXRotation() {
    return m_TargetPoseRS.getDoubleArray(new double[6])[3];
  }

  // Yaw of the tag
  public double getYRotation() {
    return m_TargetPoseRS.getDoubleArray(new double[6])[4];
  }

  // Roll of the tag
  public double getZRotation() {
    return m_TargetPoseRS.getDoubleArray(new double[6])[5];
  }

  public double getVisualArea() {
    return m_ta.getDouble(0.0);
  }

  public boolean isTargetVisible() {
    return m_tv.getDouble(0) == 1;
  }

  public int getTagID() {
    return (int) (m_tid.getInteger(0));
  }

  /// DISTANCES
  public double getTagDistance2D() {
    double distance =
        Math.sqrt(getXDistOffset() * getXDistOffset() + getZDistOffset() * getZDistOffset());
    return distance;
  }

  public double getTagDistance3D() {
    double distance =
        Math.sqrt(
            getXDistOffset() * getXDistOffset()
                + getYDistOffset() * getYDistOffset()
                + getZDistOffset() * getZDistOffset());
    return distance;
  }

  public double getTagDistanceBackup() {
    if (!isTargetVisible()) {
      /*
       * Return a negative number as another indicator that the target is not visible.
       */
      return -1;
    }

    double targetOffsetAngleVertical = getYAngleOffset();
    /*
     * Adjust calculation to account for the vertical angle of the camera.
     */
    double angleToTargetRad = Math.toRadians(targetOffsetAngleVertical + CAMERA_ANGLE);

    /*
     * Account for the height difference between the camera and the target, not just the target's height from the ground.
     */
    if (getTagID() < 0) return 0;

    double heightDifference =
        Constants.FieldConstants.AprilTagHeights_in[getTagID()] - CAMERA_HEIGHT;

    /*
     * distance = height / tan(angle)
     */
    return heightDifference / Math.tan(angleToTargetRad);
  }

  @Override
  public void periodic() {
    /*
     * Can the target be seen?
     */
    SmartDashboard.putBoolean("Can See Target?", isTargetVisible());
    SmartDashboard.putNumber("Tag ID", getTagID());
    SmartDashboard.putNumber("Tag Height (P)", getYDistOffset() * 3.281 * 12);
    if (getTagID() > 0)
      SmartDashboard.putNumber(
          "Tag Height (A)", Constants.FieldConstants.AprilTagHeights_in[getTagID()]);
    /*
     * Display the distance (in inches) to the target. If there is no target visible, then this should display a "-1".
     */
    SmartDashboard.putNumber("Distance to Target:", getTagDistance2D());
    SmartDashboard.putNumber("Backup Dist to T1arget", getTagDistanceBackup());
  }
}
