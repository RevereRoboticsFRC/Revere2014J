package k12.revere.frc.s2014.systems.motor;

import edu.wpi.first.wpilibj.Victor;

/**
 * A Victor wrapper to support motor inversion.
 * @author Vince
 */
public class InvertableVictor extends Victor {

    private boolean invert;
    
    public InvertableVictor(int channel, boolean invert) {
        super(channel);
        this.invert = invert;
    }

    public void set(double speed) {
        if(invert)
        {
            speed = -speed;
        }
        super.set(speed);
    }

    public double getSpeed() {
        double speed = super.getSpeed();
        if(invert) {
            speed = -speed;
        }
        return speed;
    }

    public void setInverted(boolean invert) {
        this.invert = invert;
    }

    public boolean isInverted() {
        return invert;
    }
    
    public boolean toggleInvert() {
        invert = !invert;
        return invert;
    }
    
    /**
     * Stops the motor, and unlike stopMotor(), does not disable the motor.
     */
    public void halt() {
        set(0D);
    }
    
}
