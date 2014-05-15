package k12.revere.frc.s2014.systems.input.logitech.extreme3dpro;

import com.sun.squawk.util.MathUtils;
import k12.revere.frc.s2014.systems.input.JoystickRevere;

/**
 *
 * @author Vince
 */
public class Logitech3DProController extends JoystickRevere {

    //  Joystick Button Constants
    public static final int BUTTON_TRIGGER = 1;
    public static final int BUTTON_THUMBREST = 2;
    public static final int BUTTON_TIP_BL = 3;
    public static final int BUTTON_TIP_BR = 4;
    public static final int BUTTON_TIP_TL = 5;
    public static final int BUTTON_TIP_TR = 6;
    public static final int BUTTON_BASE_7 = 7;
    public static final int BUTTON_BASE_8 = 8;
    public static final int BUTTON_BASE_9 = 9;
    public static final int BUTTON_BASE_10 = 10;
    public static final int BUTTON_BASE_11 = 11;
    public static final int BUTTON_BASE_12 = 12;
    //  Joystick Axis Constants
    public static final int AXIS_MAIN_X = 1;
    public static final int AXIS_MAIN_Y = 2;
    public static final int AXIS_MAIN_Z_TWIST = 3;
    public static final int AXIS_THROTTLE = 4;
    public static final int AXIS_TIPSTICK_X = 5;
    public static final int AXIS_TIPSTICK_Y = 6;
    
    public Logitech3DProController(int port) {
        super(port);
    }

    public double getMainStickX() {
        return getRawAxis(AXIS_MAIN_X);
    }
    
    public double getMainStickY() {
        return getRawAxis(AXIS_MAIN_Y);
    }
    
    public double getMainStickZTwist() {
        return getRawAxis(AXIS_MAIN_Z_TWIST);
    }
    
    public double getMainStickMagnitude() {
        double x = getMainStickX();
        double y = getMainStickY();
        return Math.sqrt(x * x + y * y);
    }
    
    public double getMainStickDirectionRadians() {
        return getDirectionRadians();
    }
    
    public double getMainStickDirectionDegrees() {
        return getDirectionDegrees();
    }
    
    public double getThrottle() {
        return getRawAxis(AXIS_THROTTLE);
    }
    
    public double getTipStickX() {
        return getRawAxis(AXIS_TIPSTICK_X);
    }
    
    public double getTipStickY() {
        return getRawAxis(AXIS_TIPSTICK_Y);
    }
    
    public double getTipStickDirectionRadians() {
        return MathUtils.atan2(getTipStickX(), -getTipStickY());
    }
    
    public double getTipStickDirectionDegrees() {
        return Math.toDegrees(getTipStickDirectionRadians());
    }
}
