package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
    private VictorSPX PrimaryArmMotor = new VictorSPX(Constants.PrimaryArmMotorID);
    private VictorSPX SecondaryArmMotor = new VictorSPX(Constants.SecondaryArmMotorID);
    private CANCoder ArmCANCoder = new CANCoder(Constants.ArmCANCoderID);
    private int armState = 0;
    @Override
    public void periodic() {
        SmartDashboard.putNumber("cancoder position", ArmCANCoder.getAbsolutePosition());
        switch (armState){
            case 1:
                armMotorSet(ControlMode.PercentOutput, 1);
                /*if (ArmCANCoder.getAbsolutePosition() < Constants.ArmMaxAngle) {
                    armMotorSet(ControlMode.PercentOutput, 1);
                }
                else {
                    armMotorSet(ControlMode.PercentOutput, 0);
                }*/
                break;
            case 2:
                if (ArmCANCoder.getAbsolutePosition() > Constants.ArmMinAngle) {
                    armMotorSet(ControlMode.PercentOutput, -1);
                }
                else {
                    armMotorSet(ControlMode.PercentOutput, 0);
                }
                break;
            case 3:
                armMotorSet(ControlMode.PercentOutput, -1);
                break;
            default:
                armMotorSet(ControlMode.PercentOutput, 0);
                break;
        }
    }

    public void armMotorSet(ControlMode controlMode, double speed) {
        PrimaryArmMotor.set(controlMode, speed);
        SecondaryArmMotor.set(controlMode, speed);
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
