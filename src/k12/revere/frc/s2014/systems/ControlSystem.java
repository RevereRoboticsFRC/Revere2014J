package k12.revere.frc.s2014.systems;

import com.sun.squawk.util.Arrays;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import k12.revere.frc.s2014.systems.System;
import k12.revere.frc.s2014.systems.input.JoystickRevere;

/**
 *
 * @author Vince
 */
public class ControlSystem implements System {
    
    //  Control Port Constants
    public static final int INPUT_JOYSTICK = 0;
    //  Debug Name Constants
    public static final String JOYSTICK_MAGNITUDE_DBG = "jmi";
    public static final String JOYSTICK_DIRECTION_DBG = "jdi";
    //  Joystick Constants
    public static final int JOYSTICK_NUM_BUTTONS = 12;
    
    private final DriveSystem driveSystem;
    private final WinchSystem winchSystem;
    private final JoystickRevere joystick;
    private final boolean[] lastTickButtonState;
    private final boolean[] currentTickButtonState;
    
    public ControlSystem(DriveSystem ds, WinchSystem ws) {
        driveSystem = ds;
        winchSystem = ws;
        joystick = new JoystickRevere(INPUT_JOYSTICK);
        lastTickButtonState = new boolean[JOYSTICK_NUM_BUTTONS];
        currentTickButtonState = new boolean[JOYSTICK_NUM_BUTTONS];
    }
    
    public void reset() {
        //  Mark all the buttons as not pressed
        Arrays.fill(lastTickButtonState, false);
        Arrays.fill(currentTickButtonState, false);
    }
    
    public void tick() {
        //  Update keystates
        //  Push previously new to last tick
        java.lang.System.arraycopy(currentTickButtonState, 0, lastTickButtonState, 0, lastTickButtonState.length);
        //  Update current
        for(int i = 0; i < JOYSTICK_NUM_BUTTONS; i++) {
            currentTickButtonState[i] = joystick.getRawButton(i + 1);
        }
    }
    
    public void teleopRobot() {
        //  TODO
        
    }
    
    public double getJoyMagnitude() {
        return joystick.getMagnitude();
    }
    
    public double getJoyDegrees() {
        return joystick.getDirectionDegrees();
    }

    public void sendDebugInfo() {
        SmartDashboard.putNumber(JOYSTICK_DIRECTION_DBG, joystick.getDirectionDegrees());
        SmartDashboard.putNumber(JOYSTICK_MAGNITUDE_DBG, getJoyMagnitude());
    }

    public void stopAll() {
        reset();
    }
    
    /**
     * Gets whether or not a button is pressed.
     * @param buttonId The button to check. <b>NOTE:</b> The button Id is NOT the same as the button number (on the physical joystick). <code>buttonId = buttonNumber - 1</code>
     * @return 
     */
    public boolean getButtonState(int buttonId) {
        //  Array safety
        if(buttonId < 0 || buttonId > currentTickButtonState.length) {
            return false;
        }
        return currentTickButtonState[buttonId];
    }

    public boolean isButtonJustPressed(int buttonId) {
        //  Array safety
        if(buttonId < 0 || buttonId > currentTickButtonState.length) {
            return false;
        }
        boolean isNowDown = currentTickButtonState[buttonId];
        boolean wasDown = lastTickButtonState[buttonId];
        return isNowDown && !wasDown;
    }
    
    public boolean isButtonJustReleased(int buttonId) {
        //  Array safety
        if(buttonId < 0 || buttonId > currentTickButtonState.length) {
            return false;
        }
        boolean isNowDown = currentTickButtonState[buttonId];
        boolean wasDown = lastTickButtonState[buttonId];
        return !isNowDown && wasDown;
    }
    
    public boolean isButtonHeld(int buttonId) {
        //  Array safety
        if(buttonId < 0 || buttonId > currentTickButtonState.length) {
            return false;
        }
        boolean isNowDown = currentTickButtonState[buttonId];
        boolean wasDown = lastTickButtonState[buttonId];
        return isNowDown && wasDown;
    }
    
}
