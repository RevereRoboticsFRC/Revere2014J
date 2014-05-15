package k12.revere.frc.s2014.systems.input.logitech.gamepadf310;

import com.sun.squawk.util.MathUtils;
import k12.revere.frc.s2014.systems.input.JoystickRevere;

/**
 *
 * @author Vince
 */
public class LogitechGamepadController extends JoystickRevere {
    
    //  Joystick Button Constants
    public static final int BUTTON_A = 1;
    public static final int BUTTON_B = 2;
    public static final int BUTTON_X = 3;
    public static final int BUTTON_Y = 4;
    public static final int BUTTON_LB = 5;
    public static final int BUTTON_RB = 6;
    public static final int BUTTON_BACK = 7;
    public static final int BUTTON_START = 8;
    //  Joystick Axis Constants
    public static final int AXIS_LEFT_JOY_X = 1;
    public static final int AXIS_LEFT_JOY_Y = 2;
    public static final int AXIS_TRIGGERS = 3;
    public static final int AXIS_RIGHT_JOY_X = 4;
    public static final int AXIS_RIGHT_JOY_Y = 5;

    public LogitechGamepadController(int port) {
        super(port);
    }

    public double getLeftStickX() {
        return getRawAxis(AXIS_LEFT_JOY_X);
    }

    public double getLeftStickY() {
        return getRawAxis(AXIS_LEFT_JOY_Y);
    }

    public double getLeftStickMagnitude() {
        double x = getLeftStickX();
        double y = getLeftStickY();
        return Math.sqrt(x * x + y * y);
    }
    
    public double getLeftStickDirectionRadians() {
        return MathUtils.atan2(getLeftStickX(), -getLeftStickY());
    }
    
    public double getLeftStickDirectionDegrees() {
        return Math.toDegrees(getLeftStickDirectionRadians());
    }
    
    public double getRightStickX() {
        return getRawAxis(AXIS_RIGHT_JOY_X);
    }

    public double getRightStickY() {
        return getRawAxis(AXIS_RIGHT_JOY_Y);
    }

    public double getRightStickMagnitude() {
        double x = getRightStickX();
        double y = getRightStickY();
        return Math.sqrt(x * x + y * y);
    }
    
    public double getRightStickDirectionRadians() {
        return MathUtils.atan2(getRightStickX(), -getRightStickY());
    }
    
    public double getRightStickDirectionDegrees() {
        return Math.toDegrees(getRightStickDirectionRadians());
    }
    
    public double getTriggerSum() {
        return getRawAxis(AXIS_TRIGGERS);
    }
}
