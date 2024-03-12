package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
  VictorSP mIntakeRoller = new VictorSP(ShooterConstants.kIntakeRollerPort);
  VictorSP mFrontShooter = new VictorSP(ShooterConstants.kFrontShooterPort);
  VictorSP mRearShooter = new VictorSP(ShooterConstants.kRearShooterPort);

  CANSparkMax mIntakeLift = new CANSparkMax(ShooterConstants.kIntakeLiftId, MotorType.kBrushless);
  SparkPIDController mIntakeLiftPID = mIntakeLift.getPIDController();

  DigitalInput mLimitSwitch = new DigitalInput(8);

  public ShooterSubsystem() {
    mIntakeLift.setInverted(false);
    mIntakeLift.setIdleMode(IdleMode.kBrake);

    mIntakeRoller.setInverted(true);

    mIntakeLiftPID.setP(ShooterConstants.kIntakeLiftP);
    mIntakeLiftPID.setI(0);
    mIntakeLiftPID.setD(0);
    mIntakeLiftPID.setOutputRange(-0.3, 0.3);

    mFrontShooter.setInverted(true);
    // mFrontShooter.set(ShooterConstants.kShooterSpeed);
    mRearShooter.setInverted(true);
    // mRearShooter.set(ShooterConstants.kShooterSpeed);
  }

  public void deployIntake() {
    mIntakeRoller.set(ShooterConstants.kIntakeRollerSpeed);
    mIntakeLiftPID.setReference(ShooterConstants.kIntakeDownPosition, ControlType.kPosition);
    // mIntakeLift.set(0.2);
  }

  public void stowIntake() {
    mIntakeRoller.stopMotor();
    mIntakeLiftPID.setReference(ShooterConstants.kIntakeUpPosition, ControlType.kPosition);
    // mIntakeLift.set(-0.2);
  }

  public void shoot() {
    mIntakeRoller.set(-ShooterConstants.kIntakeRollerSpeed);
    mFrontShooter.set(1);
    mRearShooter.set(1);
  }

  public void intake() {
    mIntakeRoller.set(ShooterConstants.kIntakeRollerSpeed);
  }

  public void stopAllMotors() {
    mIntakeLift.stopMotor();
    mIntakeRoller.stopMotor();
    mFrontShooter.stopMotor();
    mRearShooter.stopMotor();
  }

  public void getIntakeMostOfTheWayDown() {
    mIntakeLiftPID.setReference(-ShooterConstants.kIntakeUpPosition + 1, ControlType.kPosition);
  }

  public void reset() {
    // mIntakeLift.getEncoder().setPosition(0);

    // while (!mLimitSwitch.get()) {
    //   mIntakeLiftPID.setReference(-0.1, ControlType.kVelocity);
    // }
  }

  public void periodic() {
    SmartDashboard.putBoolean("Limit Switch Value", mLimitSwitch.get());
    SmartDashboard.putNumber("Intake Lift", mIntakeLift.getEncoder().getPosition());
  }
}
