package k12.revere.frc.s2014.systems;

import com.sun.squawk.util.Arrays;
import k12.revere.frc.s2014.Robot;

/**
 *
 * @author Vince
 */
public abstract class ControlSystem implements SubSystem {
    
    
    protected final Robot robot;
    protected final DriveSystem driveSystem;
    protected final WinchSystem winchSystem;
    protected final boolean[] lastTickButtonState;
    protected final boolean[] currentTickButtonState;
    
    public ControlSystem(Robot r, int numButtons) {
        if(numButtons < 0) {
            throw new IllegalArgumentException("Number of buttons cannot be less than zero!");
        }
        robot = r;
        driveSystem = robot.getDriveSystem();
        winchSystem = robot.getWinchSystem();
        lastTickButtonState = new boolean[numButtons];
        currentTickButtonState = new boolean[numButtons];
    }
    
    public void reset() {
        Robot.logger.entering("ControlSystem", "reset");
        //  Mark all the buttons as not pressed
        Arrays.fill(lastTickButtonState, false);
        Arrays.fill(currentTickButtonState, false);
        Robot.logger.exiting("ControlSystem", "reset");
    }
    
    public void tick() {
        //  Update keystates
        //  Push previously new to last tick
        System.arraycopy(currentTickButtonState, 0, lastTickButtonState, 0, lastTickButtonState.length);
        //  Update current
        for(int i = 0; i < currentTickButtonState.length; i++) {
            currentTickButtonState[i] = getRawButton(i + 1);
        }
    }
    
    public abstract boolean getRawButton(int id);
    
    public abstract void teleopRobot();

    public abstract void sendDebugInfo();

    public void stopAll() {
        Robot.logger.entering("ControlSystem", "stopAll");
        reset();
        Robot.logger.exiting("ControlSystem", "stopAll");
    }
    
    /**
     * Gets whether or not a button is pressed.
     * @param buttonId The button to check. <b>NOTE:</b> The button Id is NOT the same as the button number (on the physical joystick). <code>buttonId = buttonNumber - 1</code>
     * @return 
     */
    public final boolean getButtonState(int buttonId) {
        //  Array safety
        if(buttonId < 0 || buttonId > currentTickButtonState.length) {
            return false;
        }
        return currentTickButtonState[buttonId];
    }

    public final boolean isButtonJustPressed(int buttonId) {
        //  Array safety
        if(buttonId < 0 || buttonId > currentTickButtonState.length) {
            return false;
        }
        boolean isNowDown = currentTickButtonState[buttonId];
        boolean wasDown = lastTickButtonState[buttonId];
        return isNowDown && !wasDown;
    }
    
    public final boolean isButtonJustReleased(int buttonId) {
        //  Array safety
        if(buttonId < 0 || buttonId > currentTickButtonState.length) {
            return false;
        }
        boolean isNowDown = currentTickButtonState[buttonId];
        boolean wasDown = lastTickButtonState[buttonId];
        return !isNowDown && wasDown;
    }
    
    public final boolean isButtonHeld(int buttonId) {
        //  Array safety
        if(buttonId < 0 || buttonId > currentTickButtonState.length) {
            return false;
        }
        boolean isNowDown = currentTickButtonState[buttonId];
        boolean wasDown = lastTickButtonState[buttonId];
        return isNowDown && wasDown;
    }
    
}
