package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
    private VictorSPX ArmMotor = new VictorSPX(Constants.ArmMotor);

    @Override
    public void periodic() {

    }

    public void armUp() {
        ArmMotor.set(ControlMode.PercentOutput, 0.5);
    }
    public void armDown() {
        ArmMotor.set(ControlMode.PercentOutput, -0.5);
    }
    public void stop(){
        ArmMotor.set(ControlMode.PercentOutput, 0);
    }
}
