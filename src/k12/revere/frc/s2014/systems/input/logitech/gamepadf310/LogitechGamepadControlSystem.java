package k12.revere.frc.s2014.systems.input.logitech.gamepadf310;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import k12.revere.frc.s2014.Robot;
import k12.revere.frc.s2014.systems.ControlSystem;
import k12.revere.frc.s2014.util.MathUtil;
import k12.revere.frc.s2014.util.QWordPacker;

/**
 *
 * @author Vince
 */
public class LogitechGamepadControlSystem extends ControlSystem {

    public static final String BUTTON_DBG = "ctlbs";
    public static final String MOTOR_AXIS_DBG = "ctlf";
    
    private final LogitechGamepadController controller;
    private double leftMotorSpd;
    private double rightMotorSpd;
    private double winchSpeed;

    public LogitechGamepadControlSystem(Robot r, int joystickId) {
        super(r, 10);
        controller = new LogitechGamepadController(joystickId);
    }

    public LogitechGamepadController getController() {
        return controller;
    }

    public boolean getRawButton(int id) {
        return controller.getRawButton(id);
    }

    public void teleopRobot() {
        /*
         LTrigger - Reverse throttle
         RTrigger - Forward throttle
         LJoy - Turning
         RJoy - Winch
         B - Stop winch
         */
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        //  Drive Control
        //  A note on throttle:
        //  The trigger buttons on the Logitech Gamepad are reported in a single axis, 
        //  with the right trigger pulling the value towards -1 and the left trigger
        //  pulling the value towards +1. As a result, you cannot read both trigger values at the same time,
        //  only the result of the sum of the two values. Therefore, a value of 0 simply means that the triggers
        //  are at rest or both pulled the same amount.

        //  2014: Derek (the driver) wants the right trigger to be forward and left trigger be reverse.
        //  Therefore, since the right trigger pulls the value toward -1, invert the value for computing motor power.
        double throttle = -controller.getTriggerSum();

        //  2014: Derek wants the left joystick left/right to control turning. Pushing the joystick RIGHT pulls the value to +1.
        //  The robot's turning behavior depends on the turnFactor and the magnitude of the throttle
        //  The turningFactor's power diminishes as the throttle's absolute magnitude increases to where, when throttle is zero, turningFactor
        //  controls the rate at which the robot spins in place, and when throttle is one and turningFactor is one the robot has one stopped wheel,
        //  with varying amounts between.
        //  
        double turnFactor = controller.getLeftStickX();
        leftMotorSpd = MathUtil.clamp(-1D, 1D, throttle + turnFactor);
        rightMotorSpd = MathUtil.clamp(-1D, 1D, throttle - turnFactor);
        driveSystem.setSpeed(leftMotorSpd, rightMotorSpd);
        winchSpeed = -controller.getRightStickY();
        //  Kill the winch if the B button is down/pressed.
        if (controller.getRawButton(LogitechGamepadController.BUTTON_B)) {
            winchSpeed = 0D;
        }
        winchSystem.setSpeed(winchSpeed);
    }
    
    

    public void sendDebugInfo() {
        SmartDashboard.putNumber(MOTOR_AXIS_DBG, QWordPacker.l2d(QWordPacker.packLOctoFloat255(
                (float)controller.getLeftStickX(),
                (float)controller.getRightStickY(),
                (float)controller.getTriggerSum(),
                0F,
                (float)leftMotorSpd,
                (float)rightMotorSpd,
                (float)winchSpeed,
                0F
        )));
        SmartDashboard.putNumber(BUTTON_DBG, QWordPacker.l2d(QWordPacker.packLBitfield(lastTickButtonState)));
        
//        SmartDashboard.putNumber("ctlJLX", controller.getLeftStickX());
//        SmartDashboard.putNumber("ctlJRY", controller.getRightStickY());
//        SmartDashboard.putNumber("ctlTrig", controller.getTriggerSum());
//        SmartDashboard.putNumber("ctlLMSpd", leftMotorSpd);
//        SmartDashboard.putNumber("ctlRMSpd", rightMotorSpd);
//        SmartDashboard.putNumber("ctlWSpd", winchSpeed);
//        SmartDashboard.putBoolean("ctlBtnB", controller.getRawButton(LogitechGamepadController.BUTTON_B));
    }

}
