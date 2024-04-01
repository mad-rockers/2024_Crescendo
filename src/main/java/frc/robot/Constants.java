package frc.robot;

public class Constants {
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
}
