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
    private VictorSPX ArmMotor2 = new VictorSPX(Constants.ArmMotorID2);
    private CANCoder ArmCANCoder = new CANCoder(Constants.ArmCANCoderID);
    private int armState = 0;
    @Override
    public void periodic() {
        SmartDashboard.putNumber("cancoder position", ArmCANCoder.getAbsolutePosition());
        switch (armState){
            case 1:
                if (ArmCANCoder.getAbsolutePosition() < Constants.ArmMaxAngle) {
                    ArmMotor.set(ControlMode.PercentOutput, 1);
                    ArmMotor2.set(ControlMode.PercentOutput, 1);
                }
                else {
                    ArmMotor.set(ControlMode.PercentOutput, 0);
                    ArmMotor2.set(ControlMode.PercentOutput, 0);
                }
                break;
            case 2:
                if (ArmCANCoder.getAbsolutePosition() > Constants.ArmMinAngle) {
                    ArmMotor.set(ControlMode.PercentOutput, -1);
                    ArmMotor2.set(ControlMode.PercentOutput, -1);
                }
                else {
                    ArmMotor.set(ControlMode.PercentOutput, 0);
                    ArmMotor2.set(ControlMode.PercentOutput, 0);
                }
                break;
            case 3:
                ArmMotor.set(ControlMode.PercentOutput, 1);
                ArmMotor2.set(ControlMode.PercentOutput, 1);
                break;
            default:
                ArmMotor.set(ControlMode.PercentOutput, 0);
                ArmMotor2.set(ControlMode.PercentOutput, 0);
                break;
        }
    }
    public void armUp() {
        armState = 1;
    }
    public void armDown() {
        armState = 2;
    }
    public void forceDown(){
        armState = 3;
    }
    public void stop(){
        armState = 0;
    }
    public void EncoderReset(){
        ArmCANCoder.setPosition(0);
    }
}
