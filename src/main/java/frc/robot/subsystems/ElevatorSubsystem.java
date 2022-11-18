package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants; 

public class ElevatorSubsystem extends SubsystemBase {
    private CANSparkMax ElevatorMotor = new CANSparkMax(Constants.ElevatorMotor, MotorType.kBrushless);
    private RelativeEncoder ElevatorEncoder = ElevatorMotor.getEncoder();
    private DigitalInput ElevatorLowerStop = new DigitalInput(0);
    private DigitalInput ElevatorUpperStop = new DigitalInput(1);
    private int elevatorState = 0;
    private double elevatorSetPoint;
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Elevator Encoder", ElevatorEncoder.getPosition());
        SmartDashboard.putBoolean("Elevator Lower Stop", ElevatorLowerStop.get());
        SmartDashboard.putBoolean("Elevator Upper Stop", ElevatorUpperStop.get());
        if (elevatorState == 1) {    
            if (ElevatorEncoder.getPosition() >= Constants.ElevatorTop || ElevatorUpperStop.get() == false) {
                ElevatorMotor.stopMotor();
            }else {
                ElevatorMotor.set(1.0);
            }
        } else if(elevatorState == 2) {
            if ((ElevatorEncoder.getPosition() <= 0 || ElevatorEncoder.getPosition() > Constants.ElevatorTop + 30) || ElevatorLowerStop.get() == false) {
                ElevatorMotor.stopMotor();
            } else {
                ElevatorMotor.set(-1.0);
            }
        } else if(elevatorState == 3) {
            if(ElevatorLowerStop.get() == false){
                ElevatorMotor.set(0);
            }{
                ElevatorMotor.set(-1.0);
            }
        } else if (elevatorState == 4){
            if (ElevatorEncoder.getPosition() >= (Constants.ElevatorTop * elevatorSetPoint) + 0.5){
                if (ElevatorEncoder.getPosition() <= (Constants.ElevatorTop * elevatorSetPoint) + 3){
                    ElevatorMotor.set(-1.0);
                }else{
                    ElevatorMotor.set(-1);
                }
            }else if(ElevatorEncoder.getPosition() <= (Constants.ElevatorTop * elevatorSetPoint) - 0.5){
                if (ElevatorEncoder.getPosition() >= (Constants.ElevatorTop * elevatorSetPoint) - 3){
                    ElevatorMotor.set(1.0);
                }else{
                    ElevatorMotor.set(1);
                }
            } else{
                ElevatorMotor.set(0);
                elevatorState = 0;
            }
        } else
        {
            ElevatorMotor.set(0);
        }
    }
    public void setElevatorPostion(double elevatorSetPoint){
        elevatorState = 4;
        this.elevatorSetPoint = elevatorSetPoint;
    }

    public void elevatorUp() {
        elevatorState = 1;
    }
    public void elevatorDown() {
        elevatorState = 2;
    }
    public void stop(){
        elevatorState = 0;
        ElevatorMotor.set(0);
    }
    public void elevatorOverride() {
        elevatorState = 3;
    }
    public void elevatorReset() {
        ElevatorEncoder.setPosition(0);
    }

    
}
