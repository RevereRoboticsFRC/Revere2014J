package k12.revere.frc.s2014.systems.input;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Vince
 */
public class JoystickRevere extends Joystick {

    public JoystickRevere(int port) {
        super(port);
    }
    
    public double getMagnitude() {
        //  A more sensible magnitude. Math.pow() is not efficient for low powers (2 is a low power).
        return  Math.sqrt(getMagnitudeSq());
    }

    public double getMagnitudeSq() {
        double x = getX();
        double y = getY();
        //  Clamp as well
        return Math.max(0D, Math.min(x * x + y * y, 1.0D));
    }
}
