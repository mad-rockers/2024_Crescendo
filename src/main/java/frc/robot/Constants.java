package frc.robot;

public class Constants {
  public static class ControllerConstants {
    public static int kDriverControllerPort = 0;
    public static double kDriveSpeed = 0.5;
  }

  public static class DriveConstants {
    public static int kLeftFrontId = 1;
    public static int kLeftBackId = 2;
    public static int kRightFrontId = 3;
    public static int kRightBackId = 4;
  }

  public static class ShooterConstants {
    public static int kIntakeRollerPort = 9;
    public static int kFrontShooterPort = 7;
    public static int kRearShooterPort = 8;
    public static int kIntakeLiftId = 7;
    public static int kLimitSwitchPort = 8;

    public static double kIntakeLiftP = 0.1;

    // public static double kIntakeUpPosition = 5.7;
    public static double kIntakeUpPosition = 5.6;
    public static double kIntakeDownPosition = 0;
    public static double kIntakeRollerSpeed = 1;
    public static double kShooterSpeed = 1;

    public static double kFrontShooterSpeed = 1;
    public static double kRearShooterSpeed = 1;
  }
}
