package k12.revere.frc.s2014.systems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import k12.revere.frc.s2014.systems.System;
import k12.revere.frc.s2014.systems.motor.InvertableVictor;

/**
 *
 * @author Vince
 */
public class DriveSystem implements System {
    
    //  PWM Channel Constants
    public static final int PWM_LEFT_MOTOR = 2;
    public static final int PWM_RIGHT_MOTOR = 1;
    //  Debug Name Constants
    public static final String LEFT_MOTOR_DBG = "tlm";
    public static final String RIGHT_MOTOR_DBG = "trm";
    
    private InvertableVictor tractionLeft;
    private InvertableVictor tractionRight;

    public DriveSystem() {
        //  Invert motors here by setting the last constructor argument to true.
        tractionLeft = new InvertableVictor(PWM_LEFT_MOTOR, false);
        tractionRight = new InvertableVictor(PWM_RIGHT_MOTOR, true);
    }

    /**
     * Set the left and right traction motor speeds. Values are clamped to -1.0 and +1.0, where positive values drive the motors forward.
     * @param left
     * @param right 
     */
    public void setSpeed(double left, double right) {
        adjustAndSetSpeed(left, right);
    }
    
    /**
     * Set both traction motor speeds. Values are clamped to -1.0 and +1.0, where positive values drive the motors forward.
     * @param speed 
     */
    public void setSpeed(double speed) {
        adjustAndSetSpeed(speed, speed);
    }
    
    private void adjustAndSetSpeed(double leftRaw, double rightRaw) {
        double left = leftRaw;
        double right = rightRaw;
        //  Perform adjustments here
        
        //  We don't do the clamping since the set() functions do it for us.
        tractionLeft.set(left);
        tractionRight.set(right);
    }
    
    public void tick() {
        tractionLeft.Feed();
        tractionRight.Feed();
    }
    
    public void sendDebugInfo() {
        SmartDashboard.putNumber(LEFT_MOTOR_DBG, tractionLeft.getSpeed());
        SmartDashboard.putNumber(RIGHT_MOTOR_DBG, tractionRight.getSpeed());
    }

    public void stopAll() {
        //  A note of warning: calling stopMotor() DISABLES the motor (as well as stopping it). Call set(0) or halt() (for InvertableVictors).
        tractionLeft.halt();
        tractionRight.halt();
    }

}
