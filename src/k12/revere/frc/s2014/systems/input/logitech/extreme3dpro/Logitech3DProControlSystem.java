package k12.revere.frc.s2014.systems.input.logitech.extreme3dpro;

import k12.revere.frc.s2014.Robot;
import k12.revere.frc.s2014.systems.ControlSystem;

/**
 *
 * @author Vince
 */
public class Logitech3DProControlSystem extends ControlSystem {

    private final Logitech3DProController controller;

    public Logitech3DProControlSystem(Robot r, int joystickId) {
        super(r, 12);
        controller = new Logitech3DProController(joystickId);
    }

    public Logitech3DProController getController() {
        return controller;
    }

    public boolean getRawButton(int id) {
        return controller.getRawButton(id);
    }

    public void teleopRobot() {

    }

    public void sendDebugInfo() {

    }

}
