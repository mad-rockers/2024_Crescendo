package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AutonomousConstants;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  CANSparkMax mLeftFront = new CANSparkMax(DriveConstants.kLeftFrontId, MotorType.kBrushless);
  CANSparkMax mLeftBack = new CANSparkMax(DriveConstants.kLeftBackId, MotorType.kBrushless);
  CANSparkMax mRightFront = new CANSparkMax(DriveConstants.kRightFrontId, MotorType.kBrushless);
  CANSparkMax mRightBack = new CANSparkMax(DriveConstants.kRightBackId, MotorType.kBrushless);

  private final RelativeEncoder mLeftFrontEncoder;
  private final RelativeEncoder mLeftBackEncoder;
  private final RelativeEncoder mRightFrontEncoder;
  private final RelativeEncoder mRightBackEncoder;

  private final SparkPIDController mLeftFrontPID;
  private final SparkPIDController mRightFrontPID;

  private double prevLeftRotations = 0;
  private double prevRightRotations = 0;

  DifferentialDrive mDrivetrain;

  public DriveSubsystem() {
    mLeftFront.setInverted(true);
    mRightFront.setInverted(false);

    mLeftBack.follow(mLeftFront);
    mRightBack.follow(mRightFront);

    mDrivetrain = new DifferentialDrive(mLeftFront, mRightFront);

    mLeftFrontEncoder = mLeftFront.getEncoder();
    mLeftBackEncoder = mLeftBack.getEncoder();
    mRightFrontEncoder = mRightFront.getEncoder();
    mRightBackEncoder = mRightBack.getEncoder();

    mLeftFrontPID = mLeftFront.getPIDController();
    mRightFrontPID = mRightFront.getPIDController();
  }

  private double applyDeadBand(double input) {
    if (Math.abs(input) > (0.1)) { // Control deadband here
      return input;
    }
    return 0.0;
  }

  private double dampenSpeed(double input) {
    return (Math.abs(input) * input * 0.5); // Control speeddampener here
  }

  public void tankDrive(double left_speed, double right_speed) {
    mDrivetrain.tankDrive(
        dampenSpeed(applyDeadBand(left_speed)), dampenSpeed(applyDeadBand(right_speed)));
  }

  public void arcadeDrive(double forward_speed, double turn_speed) {
    mDrivetrain.arcadeDrive(
        dampenSpeed(applyDeadBand(forward_speed)), dampenSpeed(applyDeadBand(turn_speed)));
  }

  public void resetDistanceTraveled() {
    prevLeftRotations = mLeftFrontEncoder.getPosition();
    prevRightRotations = mRightFrontEncoder.getPosition();
  }

  public double getDistanceTraveled() {
    // Get number of rotations
    double currentLeftRotations = mLeftFrontEncoder.getPosition();
    double currentRightRotations = mRightFrontEncoder.getPosition();

    // Distances in inches
    double leftDistance =
        (currentLeftRotations - prevLeftRotations)
            * 2
            * Math.PI
            * AutonomousConstants.WHEEL_RADIUS_IN
            / AutonomousConstants.DRIVE_GEARBOX_RATIO;
    double rightDistance =
        (currentRightRotations - prevRightRotations)
            * 2
            * Math.PI
            * AutonomousConstants.WHEEL_RADIUS_IN
            / AutonomousConstants.DRIVE_GEARBOX_RATIO;

    // Reset Counts
    prevLeftRotations = currentLeftRotations;
    prevRightRotations = currentRightRotations;

    double averageDistance = (leftDistance + rightDistance) / 2;

    return averageDistance;
  }

  public void stopMotors() {
    mLeftFront.set(0);
    mRightFront.set(0);
  }
}
