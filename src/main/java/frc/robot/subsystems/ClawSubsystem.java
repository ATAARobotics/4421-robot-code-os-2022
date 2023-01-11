package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawSubsystem extends SubsystemBase {
    private DoubleSolenoid arm = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.CLAW_PNEUMATIC[0], Constants.CLAW_PNEUMATIC[1]);
    private DoubleSolenoid armExtender = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.CLAW_PNEUMATIC[2], Constants.CLAW_PNEUMATIC[3]);
    private boolean clawClosed = false;
    private boolean clawExtended = false;

    public ClawSubsystem() {

    }
    public void clawReverse() {
        arm.set(Value.kReverse);
    }
    public void clawForward() {
        arm.set(Value.kForward);
    }
    public void clawExtend() {
        System.out.println("in");
        armExtender.set(Value.kReverse);
    }
}    
