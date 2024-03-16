package frc;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.ShooterConstants;

public class IntakeLimitSwitchTrigger extends Trigger {
  private static DigitalInput leftLimitSwitch;
  private static DigitalInput rightLimitSwitch;

  public IntakeLimitSwitchTrigger() {
    super(() -> false);
    leftLimitSwitch = new DigitalInput(ShooterConstants.kLeftIntakeLimitSwitchPort);
    rightLimitSwitch = new DigitalInput(ShooterConstants.kRightIntakeLimitSwitchPort);
  }

  public boolean get() {
    return leftLimitSwitch.get() && rightLimitSwitch.get();
  }
}
