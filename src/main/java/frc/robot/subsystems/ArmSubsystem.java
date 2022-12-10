package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BetterJoystick;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
    private VictorSPX ArmMotor = new VictorSPX(Constants.ArmMotorID);
    private VictorSPX ArmMotor2 = new VictorSPX(Constants.ArmMotorID2);
    private CANCoder ArmCANCoder = new CANCoder(Constants.ArmCANCoderID);
    private int armState = 0;
    private DigitalInput ArmLowerStop = new DigitalInput(2);
    private BetterJoystick gunnerStick = new BetterJoystick(1, 0);
    
    private DigitalInput ArmUpperStop = new DigitalInput(3);
    @Override
    public void periodic() {
        SmartDashboard.putNumber("cancoder position", ArmCANCoder.getPosition());
        SmartDashboard.putBoolean("Arm Lower Stop", ArmLowerStop.get());
        SmartDashboard.putBoolean("Arm Upper Stop", ArmUpperStop.get());
        switch (armState){
            case 1:
                System.out.println("armup");
                if (ArmCANCoder.getPosition() < Constants.ArmMaxAngle && ArmUpperStop.get() == true) {
                    ArmMotor.set(ControlMode.PercentOutput, 1);
                    ArmMotor2.set(ControlMode.PercentOutput, 1);
                    gunnerStick.setRumble(RumbleType.kLeftRumble, 1);
                }
                else {
                    ArmMotor.set(ControlMode.PercentOutput, 0);
                    ArmMotor2.set(ControlMode.PercentOutput, 0);
                    gunnerStick.setRumble(RumbleType.kLeftRumble, 0);
                }
                break;
            case 2:
                if (ArmCANCoder.getPosition() > Constants.ArmMinAngle && ArmLowerStop.get() == true) {
                    ArmMotor.set(ControlMode.PercentOutput, -1);
                    ArmMotor2.set(ControlMode.PercentOutput, -1);
                    gunnerStick.setRumble(RumbleType.kLeftRumble, 1);
                }
                else {
                    ArmMotor.set(ControlMode.PercentOutput, 0);
                    ArmMotor2.set(ControlMode.PercentOutput, 0);
                    gunnerStick.setRumble(RumbleType.kLeftRumble, 0);
                }
                break;
            case 3:
                ArmMotor.set(ControlMode.PercentOutput, -1);
                ArmMotor2.set(ControlMode.PercentOutput, -1);
                gunnerStick.setRumble(RumbleType.kLeftRumble, 1);
                break;
            default:
                ArmMotor.set(ControlMode.PercentOutput, 0);
                ArmMotor2.set(ControlMode.PercentOutput, 0);
                gunnerStick.setRumble(RumbleType.kLeftRumble, 0);
                break;
        }
    }

    public void armMotorSet(ControlMode controlMode, double speed) {
        ArmMotor.set(controlMode, speed);
        ArmMotor2.set(controlMode, speed);
    }
    public void armUp() {
        armState = 1;
        System.out.println("armup");
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
        ArmCANCoder.setPosition(5);
    }
}
