package k12.revere.frc.s2014.systems.input;

import edu.wpi.first.wpilibj.Joystick;
import k12.revere.frc.s2014.util.MathUtil;

/**
 *
 * @author Vince
 */
public class JoystickRevere extends Joystick {

    public JoystickRevere(int port) {
        super(port);
    }
    
    public double getMagnitude() {
        return  Math.sqrt(getMagnitudeSq());
    }

    public double getMagnitudeSq() {
        double x = getX();
        double y = getY();
        //  Clamp as well
        //  A more sensible magnitude. Math.pow() is not efficient for low powers (2 is a low power).
        return MathUtil.clamp(0D, 1D, x * x + y * y);
    }
}
