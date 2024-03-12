package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  CANSparkMax mLeftFront = new CANSparkMax(DriveConstants.kLeftFrontId, MotorType.kBrushless);
  CANSparkMax mLeftBack = new CANSparkMax(DriveConstants.kLeftBackId, MotorType.kBrushless);
  CANSparkMax mRightFront = new CANSparkMax(DriveConstants.kRightFrontId, MotorType.kBrushless);
  CANSparkMax mRightBack = new CANSparkMax(DriveConstants.kRightBackId, MotorType.kBrushless);

  DifferentialDrive mDrivetrain;

  public DriveSubsystem() {
    mLeftFront.setInverted(true);
    mRightFront.setInverted(false);

    mLeftBack.follow(mLeftFront);
    mRightBack.follow(mRightFront);

    mDrivetrain = new DifferentialDrive(mLeftFront, mRightFront);
  }

  public void tankDrive(double left_speed, double right_speed) {
    mDrivetrain.tankDrive(left_speed, right_speed);
  }

  public void arcadeDrive(double forward_speed, double turn_speed) {
    mDrivetrain.arcadeDrive(forward_speed, turn_speed);
  }
}
