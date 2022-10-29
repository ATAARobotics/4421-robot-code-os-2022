package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawSubsystem extends SubsystemBase {
    private DoubleSolenoid arm = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.CLAW_PNEUMATIC[0], Constants.CLAW_PNEUMATIC[1]);

    private boolean clawClosed = false;

    public ClawSubsystem() {

    }
    public void clawReverse() {
        if (!clawClosed) {
            arm.set(Value.kReverse);
        }
        clawClosed = true;
    }
    public void clawForward() {
        if (clawClosed) {
            arm.set(Value.kForward);
        }
        clawClosed = false;
    }
}    
