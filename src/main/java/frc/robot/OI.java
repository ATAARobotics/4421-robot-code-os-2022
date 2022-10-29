package frc.robot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

class OI {

    private BetterJoystick driveStick = new BetterJoystick(0, 1);
    private BetterJoystick gunnerStick = new BetterJoystick(1, 0);

    public JoystickButton toggleClaw;

    public JoystickButton ArmUp;
    public JoystickButton ArmDown;
    public JoystickButton ElevatorUp;
    public JoystickButton ElevatorDown;
    public JoystickButton ElevatorOverride;
    public JoystickButton ElevatorReset;
    public JoystickButton ElevatorHalfwayPoint;

    private double xVelocity;
    private double yVelocity;
    private double rotationVelocity;
    private double speed;

    public JoystickButton toggleFieldOriented;

    public OI() {
        // Configure the button bindings
        try (InputStream input = new FileInputStream("/home/lvuser/deploy/bindings.properties")) {
            Properties bindings = new Properties();

            bindings.load(input);

            driveStick.configureBindings(bindings);
            gunnerStick.configureBindings(bindings);
            input.close();
        } catch (FileNotFoundException e) {
            DriverStation.reportError("Button bindings file not found!", false);
        } catch (IOException e) {
            DriverStation.reportError("IOException on button binding file", false);
        }

        toggleFieldOriented = driveStick.getWPIJoystickButton("ToggleFieldOriented");
        // Set up command-based stuff
        ArmUp = gunnerStick.getWPIJoystickButton("ArmUp");
        ArmDown = gunnerStick.getWPIJoystickButton("ArmDown");
        ElevatorUp = gunnerStick.getWPIJoystickButton("ElevatorUp");
        ElevatorDown = gunnerStick.getWPIJoystickButton("ElevatorDown");
        ElevatorOverride = driveStick.getWPIJoystickButton("ElevatorOverride");
        ElevatorReset = driveStick.getWPIJoystickButton("ElevatorReset");
        ElevatorHalfwayPoint = driveStick.getWPIJoystickButton("ElevatorHalfwayPoint");
        toggleClaw = gunnerStick.getWPIJoystickButton("ToggleClaw");
        
    }

    public void checkInputs() {
        xVelocity = driveStick.getAnalog("XVelocity");
        yVelocity = driveStick.getAnalog("YVelocity");
        rotationVelocity = driveStick.getAnalog("RotationVelocity");
        speed = (-driveStick.getAnalog("Speed") + 1) / 4 + 0.5;

        // Dead zones
        if (Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2)) < Constants.JOY_DEAD_ZONE) {
            xVelocity = 0;
            yVelocity = 0;
        }
        if (Math.abs(rotationVelocity) < Constants.JOY_DEAD_ZONE) {
            rotationVelocity = 0;
        }

        xVelocity = Math.signum(xVelocity) * Math.abs(Math.pow(xVelocity, Constants.JOYSTICK_SENSITIVITY));
        yVelocity = Math.signum(yVelocity) * Math.abs(Math.pow(yVelocity, Constants.JOYSTICK_SENSITIVITY));
        rotationVelocity = Math.signum(rotationVelocity)
                * Math.abs(Math.pow(rotationVelocity, Constants.TURNING_SENSITIVITY));
    }

    // Getter functions for controls
    public double getXVelocity() {
        return xVelocity;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRotationVelocity() {
        return rotationVelocity;
    }

    public Trigger getToggleClaw() {
        return toggleClaw;
    }
}
