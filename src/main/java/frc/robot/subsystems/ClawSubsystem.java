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
        System.out.println("closed");
        arm.set(Value.kReverse);
        if (!clawClosed) {
            arm.set(Value.kReverse);
        }
        clawClosed = true;
    }
    public void clawForward() {
        System.out.println("open");
        arm.set(Value.kForward);
        if (clawClosed) {
            arm.set(Value.kForward);
        }
        clawClosed = false;
    }
    public void clawExtend() {
        if (clawExtended) {
            armExtender.set(Value.kReverse);
            clawExtended = false;
        }
        else {
            System.out.print("clawforward");
            armExtender.set(Value.kForward);
            clawExtended = true;
        }
    }
}    
