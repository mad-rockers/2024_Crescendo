package frc.robot.commands;

import frc.robot.subsystems.NeoMotorDriveSystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoDrive extends Command
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private static final double WHEEL_RADIUS_IN = 3; //CHANGE ME//

    private double wheelCircumference;
    private double targetRPM;
    private double targetDistance; //inches
    private int isForward = 1;  //sign value

    private final Timer m_timer = new Timer();
    private NeoMotorDriveSystem m_NeoMotorDriveSystem;

    private double runTime;
    /**
    * Instructs the robot to move a set number of feet with a given RPM
    **/


    public AutoDrive(NeoMotorDriveSystem neoMotorDriveSystem, double rpm, double distance_ft) 
    {
        m_NeoMotorDriveSystem = neoMotorDriveSystem;
        targetRPM = rpm;

        isForward = (int)(Math.abs(targetDistance)/targetDistance);

        targetDistance = Math.abs(distance_ft*12); //Convert from ft -> in
        wheelCircumference = 2*Math.PI*WHEEL_RADIUS_IN;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(neoMotorDriveSystem);
    }

    // Calculates how long we need to move, starts the motors.
    @Override
    public void initialize() 
    {
        m_timer.reset();

        double targetRPS = 60/targetRPM;    //Get revolutions per second
        double distanceRatio = targetDistance/wheelCircumference; //How many rotations we must perform
        runTime = targetRPS*distanceRatio; 

        m_NeoMotorDriveSystem.setMotorRPM(targetRPM * isForward);

        m_timer.start();
    }

    // Outputs distance_ft to SmartDashboard
    @Override
    public void execute() 
    {
        double currentDistance = (m_timer.get()*targetRPM*wheelCircumference)/60;
        SmartDashboard.putNumber("Predicted Current distance_ft", currentDistance);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_timer.hasElapsed(runTime);
    }

}