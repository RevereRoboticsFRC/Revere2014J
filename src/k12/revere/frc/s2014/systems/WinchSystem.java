package k12.revere.frc.s2014.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import k12.revere.frc.s2014.systems.System;
import k12.revere.frc.s2014.systems.motor.InvertableVictor;

/**
 *
 * @author Vince
 */
public class WinchSystem implements System {

    //  PWM Channel Constants
    public static final int PWM_WINCH_MOTOR = 0;
    //  Digital IO Channel Constants
    public static final int DIG_LIM_SWITCH_HIGH = 0;
    public static final int DIG_LIM_SWITCH_LOW = 0;
    //  Debug Name Constants
    public static final String WINCH_MOTOR_DBG = "wm";
    public static final String HIGH_SWITCH_DBG = "hs";
    public static final String LOW_SWITCH_DBG = "ls";
    
    private InvertableVictor winch;
    
    private DigitalInput switchHigh;
    private DigitalInput switchLow;

    public WinchSystem() {
        //  Invert motors here by setting the last constructor argument to true. Ensure that driving the winch positive results in the winch going up.
        winch = new InvertableVictor(PWM_WINCH_MOTOR, true);
        
    }
    
    /**
     * Set the winch motor speed. Value is clamped from -1.0 to +1.0. The motor will not run upwards if the high switch is tripped or downwards if the low switch is tripped.
     * @param speed 
     */
    public void setSpeed(double speed) {
        //  Prevent the winch from running if we've hit the switch.
        if((speed > 0D && isHighSwitchTripped()) || (speed < 0D && isLowSwitchTripped())) {
            winch.halt();
        }
        winch.set(speed);
    }
    
    public boolean isHighSwitchTripped() {
        return switchHigh.get();
    }
    public boolean isLowSwitchTripped() {
        return switchLow.get();
    }
    
    public void tick() {
        winch.Feed();
        double speed = winch.getSpeed();
        //  Automatically stop the motor from going if the limit switch for that direction is tripped.
        if(speed > 0D) {
            if(isHighSwitchTripped()) {
                winch.halt();
            }
        } else if(speed < 0D) {
            if(isLowSwitchTripped()) {
                winch.halt();
            }
        }
        //  TODO Add a check for if the switch doesn't reset after driving the motor for a while stop?
        
    }

    public void sendDebugInfo() {
        SmartDashboard.putNumber(WINCH_MOTOR_DBG, winch.getSpeed());
        SmartDashboard.putBoolean(HIGH_SWITCH_DBG, switchHigh.get());
        SmartDashboard.putBoolean(LOW_SWITCH_DBG, switchLow.get());
    }

    public void stopAll() {
        winch.halt();
    }
    
    
    
}
