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

  DigitalInput mAngleLimitSwitch = new DigitalInput(ShooterConstants.kAngleLimitSwitchPort);
  DigitalInput mRecieverLimitSwitchLeft = new DigitalInput(ShooterConstants.kIntakeReceiverLeftID);
  DigitalInput mRecieverLimitSwitchRight =
      new DigitalInput(ShooterConstants.kIntakeReceiverRightID);

  public ShooterSubsystem() {
    mIntakeLift.setInverted(false);
    mIntakeLift.setIdleMode(IdleMode.kBrake);

    mIntakeRoller.setInverted(true);

    mIntakeLiftPID.setP(ShooterConstants.kIntakeLiftP);
    mIntakeLiftPID.setI(0);
    mIntakeLiftPID.setD(0);
    mIntakeLiftPID.setOutputRange(-0.3, 0.3);

    mFrontShooter.setInverted(true);
    mRearShooter.setInverted(true);
  }

  public void deployIntake() {
    mIntakeRoller.set(ShooterConstants.kIntakeRollerSpeed);
    mIntakeLiftPID.setReference(ShooterConstants.kIntakeDownPosition, ControlType.kPosition);
  }

  public void stowIntake() {
    mIntakeRoller.stopMotor();
    mIntakeLiftPID.setReference(ShooterConstants.kIntakeUpPosition, ControlType.kPosition);
  }

  public void shoot() {
    if (mFrontShooter.get() > 0) {
      mIntakeRoller.set(-0.25);
      mRearShooter.set(ShooterConstants.kRearShooterSpeed);
    }
  }

  public void startFrontShooterMotor() {
    mFrontShooter.setVoltage(ShooterConstants.kFrontShooterSpeed * 12.5);
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

  public void stopAllMotors(String except) {
    boolean stopFrontShooter = true;
    boolean stopRearShooter = true;
    boolean stopIntakeLift = true;
    boolean stopIntakeRoller = true;

    if (except.contains("front_shooter_motor")) {
      stopFrontShooter = false;
    }

    if (except.contains("rear_shooter_motor")) {
      stopRearShooter = false;
    }

    if (except.contains("intake_lift_motor")) {
      stopIntakeLift = false;
    }

    if (except.contains("stop_intake_roller")) {
      stopIntakeRoller = false;
    }

    if (stopFrontShooter) {
      mFrontShooter.stopMotor();
    }

    if (stopRearShooter) {
      mRearShooter.stopMotor();
    }

    if (stopIntakeLift) {
      mIntakeLift.stopMotor();
    }

    if (stopIntakeRoller) {
      mIntakeRoller.stopMotor();
    }
  }

  public void goToPositionTen() {
    mIntakeLiftPID.setReference(10, ControlType.kPosition);
  }

  public void setEncoderToZero() {
    mIntakeLift.stopMotor();
    mIntakeLift.getEncoder().setPosition(0);
  }

  public void setEncoderToNegativeFifty() {
    mIntakeLift.stopMotor();
    mIntakeLift.getEncoder().setPosition(-50);
  }

  public void setEncoderToFifty() {
    mIntakeLift.stopMotor();
    mIntakeLift.getEncoder().setPosition(50);
  }

  public void resetIntakeLiftEncoder() {
    /*
     * As of prototyping, the limit switch is set to be "false" when depressed.
     */
    while (mAngleLimitSwitch.get()) {
      mIntakeLiftPID.setReference(0.1, ControlType.kVelocity);
    }

    mIntakeLift.getEncoder().setPosition(0);
  }

  public void liftIntakeForReset() {
    mIntakeLiftPID.setReference(0.1, ControlType.kVelocity);
  }

  public boolean getLeftRecieverSwitch() {
    return !mRecieverLimitSwitchLeft.get();
  }

  public boolean getRightRecieverSwitch() {
    return mRecieverLimitSwitchRight.get();
  }

  public boolean getLimitSwitchPressed() {
    return !mAngleLimitSwitch.get();
  }

  public void decrementIntakeLiftPosition() {
    // Read the current position
    double currentPosition = mIntakeLift.getEncoder().getPosition();

    // Calculate the new position by decrementing the current position by 1
    double newPosition = currentPosition - 1;

    // Use the PID controller to move the intake lift to the new position
    // Assuming you're controlling the position directly, you might use ControlType.kPosition
    mIntakeLiftPID.setReference(newPosition, ControlType.kPosition);
  }

  public void incrementIntakeLiftPosition() {
    // Read the current position
    double currentPosition = mIntakeLift.getEncoder().getPosition();

    // Calculate the new position by decrementing the current position by 1
    double newPosition = currentPosition + 1;

    // Use the PID controller to move the intake lift to the new position
    // Assuming you're controlling the position directly, you might use ControlType.kPosition
    mIntakeLiftPID.setReference(newPosition, ControlType.kPosition);
  }

  public void periodic() {
    SmartDashboard.putBoolean("Left Limit Switch Value (Getter)", getLeftRecieverSwitch());
    SmartDashboard.putBoolean("Right Limit Switch Value (Getter)", getRightRecieverSwitch());
    SmartDashboard.putBoolean("Limit Switch Value", mAngleLimitSwitch.get());
    SmartDashboard.putNumber("Intake Lift", mIntakeLift.getEncoder().getPosition());
  }
}
