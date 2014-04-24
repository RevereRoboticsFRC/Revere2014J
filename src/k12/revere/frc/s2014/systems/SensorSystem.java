package k12.revere.frc.s2014.systems;

import edu.wpi.first.wpilibj.Accelerometer;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import k12.revere.frc.s2014.systems.System;

/**
 *
 * @author Vince
 */
public class SensorSystem implements System {

    //  Analog Channel Constants
    public static final int ANALOG_ACCELEROMETER = 0;
    public static final int ANALOG_GYRO = 0;
    //  Debug Name Constants
    public static final String ACCELEROMETER_DBG = "as";
    public static final String GYRO_ANGLE_DBG = "gas";
    public static final String GYRO_RATE_DBG = "grs";
    //  Accelerometer Constants
    public static final double ACCELEROMETER_ZERO_G_VOLTAGE = 2.5D; //  2.5 is the default. Consult the accelerometer documentation for the correct zero-g voltage.
    public static final double ACCELEROMETER_VOLTS_PER_G = 1.0D;    //  1.0 is the default. Consult the accelerometer documentation for the correct volts per g.
    
    private Accelerometer accelerometer;
    private Gyro gyro;
    
    public SensorSystem() {
        accelerometer = new Accelerometer(ANALOG_ACCELEROMETER);
        accelerometer.setZero(ACCELEROMETER_ZERO_G_VOLTAGE);
        accelerometer.setSensitivity(ACCELEROMETER_VOLTS_PER_G);
        
        gyro = new Gyro(ANALOG_GYRO);
    }

    public double getAcceleration() {
        return accelerometer.getAcceleration();
    }
    
    public double getAngle() {
        return gyro.getAngle();
    }
    
    public double getAngle360() {
        double angle = getAngle();
        //  Modulo the angle to 0-360
        angle %= 360D;
        if(angle < 0D) {
            //  Replace negative angles with positive equivilents.
            angle += 360D;
        }
        return angle;
    }
    
    public double getRotationRate() {
        return gyro.getRate();
    }
    
    public void calibrate() {
        gyro.reset();
    }
    
    public void tick() {
        
    }

    public void sendDebugInfo() {
        SmartDashboard.putNumber(ACCELEROMETER_DBG, accelerometer.getAcceleration());
        SmartDashboard.putNumber(GYRO_ANGLE_DBG, getAngle360());
        SmartDashboard.putNumber(GYRO_RATE_DBG, getRotationRate());
    }

    public void stopAll() {
    }
    
}
