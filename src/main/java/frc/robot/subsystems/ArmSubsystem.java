package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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
    private CANSparkMax ArmMotor = new CANSparkMax(Constants.ArmMotorID, MotorType.kBrushless);
    //private CANCoder ArmCANCoder = new CANCoder(Constants.ArmCANCoderID);
    private int armState = 0;
    private DigitalInput ArmLowerStop = new DigitalInput(2);
    
    private DigitalInput ArmUpperStop = new DigitalInput(3);
    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Arm Lower Stop", ArmLowerStop.get());
        SmartDashboard.putBoolean("Arm Upper Stop", ArmUpperStop.get());
        switch (armState){
            case 1:
                if ( ArmUpperStop.get() == true) {
                    ArmMotor.set(0.20);

                }
                else {
                    ArmMotor.set(0);
                }
                break;
            case 2:
                if (ArmLowerStop.get() == true) {
                    ArmMotor.set(-0.20);
                }
                else {
                    ArmMotor.set(0);
                }
                break;
            case 3:
                ArmMotor.set(-0.20);
                break;
            default:
                ArmMotor.set(0);
                break;
        }
    }
    public void armUp() {
        armState = 1;
    }
    public void armDown() {
        armState = 2;
    }
    public void stop(){
        armState = 0;
    }

}
