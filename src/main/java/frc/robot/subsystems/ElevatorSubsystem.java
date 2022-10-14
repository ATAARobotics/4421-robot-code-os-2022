package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants; 

public class ElevatorSubsystem extends SubsystemBase {
    private CANSparkMax ElevatorMotor = new CANSparkMax(Constants.ElevatorMotor, MotorType.kBrushless);
    private RelativeEncoder ElevatorEncoder = ElevatorMotor.getEncoder();
    private int elevatorState = 0;
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Elevator Encoder", ElevatorEncoder.getPosition());
        if(elevatorState == 0){
            ElevatorMotor.set(0);
        }
        else if (elevatorState == 1){
            System.out.println("hello");
            if(ElevatorEncoder.getPosition() <= 297){
                ElevatorMotor.set(0.2);
            }
            else{
                ElevatorMotor.set(0);
                elevatorState = 0;
            }
        }
        else if (elevatorState == 2){
            if(ElevatorEncoder.getPosition() >= 10){
                ElevatorMotor.set(-0.2);
            }
            else{
                elevatorState = 0;
            }
        }
        else {
            ElevatorMotor.set(0);
        }
    }

    public void elevatorUp() {
        ElevatorMotor.set(0.25);
    }
    public void elevatorDown() {
        ElevatorMotor.set(-0.25);
    }
    public void stop(){
        elevatorState = 0;
        ElevatorMotor.set(0);
    }

    public void zeroElevator(){
        ElevatorEncoder.setPosition(300);
    }
    
}
