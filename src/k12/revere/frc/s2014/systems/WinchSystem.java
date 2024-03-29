package k12.revere.frc.s2014.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import k12.revere.frc.s2014.Robot;
import k12.revere.frc.s2014.systems.motor.InvertableVictor;
import k12.revere.frc.s2014.util.QWordPacker;

/**
 *
 * @author Vince
 */
public class WinchSystem implements SubSystem {

    //  PWM Channel Constants
    public static final int PWM_WINCH_MOTOR = 3;
    //  Digital IO Channel Constants
    public static final int DIG_LIM_SWITCH_HIGH = 1;
    public static final int DIG_LIM_SWITCH_LOW = 2;
    //  Debug Name Constants
    public static final String WINCH_DBG = "wn";
    
//    public static final String WINCH_MOTOR_DBG = "wm";
//    public static final String HIGH_SWITCH_DBG = "hs";
//    public static final String LOW_SWITCH_DBG = "ls";
    
    private InvertableVictor winch;
    
    private DigitalInput switchHigh;
    private DigitalInput switchLow;

    public WinchSystem() {
        //  Invert motors here by setting the last constructor argument to true. Ensure that driving the winch positive results in the winch going up.
        winch = new InvertableVictor(PWM_WINCH_MOTOR, true);
        
        switchHigh = new DigitalInput(DIG_LIM_SWITCH_HIGH);
        switchLow = new DigitalInput(DIG_LIM_SWITCH_LOW);
        
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
        //  Combine the float value and the limit switch states on the same value
        //  The highest two bits are the limit switch states and the lowest byte is winch speed
        long l = QWordPacker.packLOctoFloat255((float)winch.getSpeed(), 0, 0, 0, 0, 0, 0, 0) & 0xFFL;
        l |= QWordPacker.setBitFlag(l, 63, switchHigh.get());
        l |= QWordPacker.setBitFlag(l, 62, switchLow.get());
        SmartDashboard.putNumber(WINCH_DBG, QWordPacker.l2d(l));
        
//        SmartDashboard.putNumber(WINCH_MOTOR_DBG, winch.getSpeed());
//        SmartDashboard.putBoolean(HIGH_SWITCH_DBG, switchHigh.get());
//        SmartDashboard.putBoolean(LOW_SWITCH_DBG, switchLow.get());
    }

    public void stopAll() {
        Robot.logger.entering("WinchSystem", "stopAll");
        winch.halt();
        Robot.logger.exiting("WinchSystem", "stopAll");
    }
    
    
    
}
