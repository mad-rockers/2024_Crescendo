package frc.robot;

public class Constants {
  public class FieldConstants {
    // https://firstfrc.blob.core.windows.net/frc2024/Manual/2024GameManual.pdf, page 37.
    public static double[] AprilTagHeights_in = {
      0, // ID 0, Doesn't exist
      48.5, // ID 1, Source Right, Red
      48.5, // ID 2, Source Left, Red
      51.875, // ID 3, Speaker Right, Red
      51.875, // ID 4, Speaker Center, Red
      48.125, // ID 5, AMP, Red
      48.125, // ID 6, AMP, Blue
      57.0, // ID 7, Speaker Center, Blue
      51.875, // ID 8, Speaker Left, Blue
      48.5, // ID 9, Source Right, Blue
      48.5, // ID 10, Source Left, Blue
      47.5, // ID 11, Stage Close Left, Red
      47.5, // ID 12, Stage Close Right, Red
      47.5, // ID 13, Stage Far, Red
      47.5, // ID 14, Stage Far, Blue
      47.5, // ID 15, Stage Left, Blue
      47.5 // ID 16, Stage Right, Blue
    };
  }

  public static class ControllerConstants {
    public static int kDriverControllerPort = 0;
    public static int kOperatorControllerPort = 1;
  }

  public static class DriveConstants {
    public static int kLeftFrontId = 1;
    public static int kLeftBackId = 2;
    public static int kRightFrontId = 3;
    public static int kRightBackId = 4;

    public static double kDriveSpeed = 0.65;
  }

  public static class AutonomousConstants {
    public static final double WHEEL_RADIUS_IN = 3;
    public static final double DRIVE_GEARBOX_RATIO = (50 / 14) * (45 / 19);
  }

  public static class ShooterConstants {
    public static int kIntakeRollerPort = 0;
    public static int kFrontShooterPort = 8;
    public static int kRearShooterPort = 9;
    public static int kIntakeLiftId = 7;
    public static int kIntakeReceiverLeftID = 6;
    public static int kIntakeReceiverRightID = 7;

    public static int kAngleLimitSwitchPort = 8;

    public static double kIntakeLiftP = 0.1;

    public static double kIntakeUpPosition = 0;
    public static double kIntakeDownPosition = -50;
    public static double kIntakeRollerSpeed = 1;
    public static double kShooterSpeed = 1;

    public static double kFrontShooterSpeed = 1;
    public static double kRearShooterSpeed = 1;
  }

  public static class CameraConstants {
    public static double[] CameraData = {
      0.152, // [0] Distance forward from center of the robot.
      0.000, // [1] Distance horizontally from the center of the robot.
      0.394, // [2] Distance vertically from the center of the robot.
      0, // [3] Roll of the camera
      15, // [4] Pitch of the camera
      0 // [5] Yaw of the camera
    };
  }
}
