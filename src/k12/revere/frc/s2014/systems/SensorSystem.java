package k12.revere.frc.s2014.systems;

import k12.revere.frc.s2014.Robot;

/**
 *
 * @author Vince
 */
public class SensorSystem implements SubSystem {

    //  Sensors this year aren't accurate or responsive enough for our use.
//    //  Analog Channel Constants
//    public static final int ANALOG_GYRO = 1;
//    //  I2C Channel Constants
//    public static final int I2C_ADXL_ACCELEROMETER = 1;
//    //  Debug Name Constants
//    public static final String ACCELEROMETER_X_DBG = "axs";
//    public static final String ACCELEROMETER_Y_DBG = "ays";
//    public static final String ACCELEROMETER_Z_DBG = "azs";
//    public static final String ACCELEROMETER_MS_DBG = "amss";
//    public static final String GYRO_ANGLE_DBG = "gas";
//    public static final String GYRO_RATE_DBG = "grs";
//    //  Gyro Constants
//    public static final double GYRO_SENSITIVITY = 0.07D;
//
//    private ADXL345_I2C adxlAccelerometer;
//    private Gyro gyro;

    public SensorSystem() {
//        adxlAccelerometer = new ADXL345_I2C(I2C_ADXL_ACCELEROMETER, ADXL345_I2C.DataFormat_Range.k2G);
//        gyro = new Gyro(ANALOG_GYRO);
//        gyro.setSensitivity(GYRO_SENSITIVITY);
    }

//    /**
//     * Returns the robot's x-axis acceleration. Positive X indicates acceleration to the right.
//     *
//     * @return
//     */
//    public double getAccelerationX() {
//        return adxlAccelerometer.getAcceleration(ADXL345_I2C.Axes.kX);
//    }
//
//    /**
//     * Returns the robot's y-axis acceleration. Positive Y indicates acceleration forward.
//     *
//     * @return
//     */
//    public double getAccelerationY() {
//        return adxlAccelerometer.getAcceleration(ADXL345_I2C.Axes.kY);
//    }
//
//    /**
//     * Returns the robot's z-axis acceleration. Positive Z indicates acceleration upward.
//     *
//     * @return
//     */
//    public double getAccelerationZ() {
//        return adxlAccelerometer.getAcceleration(ADXL345_I2C.Axes.kZ);
//    }
//
//    public double getAccelerationMagSq() {
//        double x = getAccelerationX();
//        double y = getAccelerationY();
//        double z = getAccelerationZ();
//        return x * x + y * y + z * z;
//    }
//
//    public double getAccelerationMag() {
//        return Math.sqrt(getAccelerationMagSq());
//    }
//
//    public double getAngle() {
//        return gyro.getAngle();
//    }
//
//    public double getAngle360() {
//        double angle = getAngle();
//        //  Modulo the angle to 0-360
//        angle %= 360D;
//        if (angle < 0D) {
//            //  Replace negative angles with positive equivilents.
//            angle += 360D;
//        }
//        return angle;
//    }
//
//    public double getRotationRate() {
//        return gyro.getRate();
//    }

    public void calibrate() {
        
        Robot.logger.trace("Sensors calibrated.");
    }

    public void tick() {

    }

    public void sendDebugInfo() {
        
    }

    public void stopAll() {
    }

}
