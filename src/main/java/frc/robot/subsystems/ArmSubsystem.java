package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
    private VictorSPX ArmMotor = new VictorSPX(Constants.ArmMotorID);
    private CANCoder ArmCANCoder = new CANCoder(Constants.ArmCANCoderID);

    @Override
    public void periodic() {

    }
    
    public void armUp() {
        SmartDashboard.putNumber("cancoder position", ArmCANCoder.getAbsolutePosition());
        if (ArmCANCoder.getAbsolutePosition() < Constants.ArmMaxAngle) {
            ArmMotor.set(ControlMode.PercentOutput, -0.125);
            
        }
        else {
            ArmMotor.set(ControlMode.PercentOutput, 0);
        }
    }
    public void armDown() {
        SmartDashboard.putNumber("cancoder position", ArmCANCoder.getAbsolutePosition());
        if (ArmCANCoder.getAbsolutePosition() > Constants.ArmMinAngle) {
            ArmMotor.set(ControlMode.PercentOutput, 0.125);
        }
        else {
            ArmMotor.set(ControlMode.PercentOutput, 0);
        }
    }
    public void stop(){
        SmartDashboard.putNumber("cancoder position", ArmCANCoder.getAbsolutePosition());
        ArmMotor.set(ControlMode.PercentOutput, 0);
    }
}
