package k12.revere.frc.s2014.util;

import k12.revere.frc.s2014.Robot;

/**
 *
 * @author Vince
 */
public class Ticker {

    private final int targetTicksPerSecond;
    private final int tickLengthMillis;
    private final float tickLengthSeconds;

    private int globalTickCount;
    private int modeTickCount;
    private long lastTickMillis;
    
    private int cantKeepUpTicks;

    public Ticker(int targetTicksPerSecond) {
        this.targetTicksPerSecond = targetTicksPerSecond;
        tickLengthSeconds = 1F / (float) targetTicksPerSecond;
        tickLengthMillis = 1000 / targetTicksPerSecond;

        if (targetTicksPerSecond <= 0) {
            throw new IllegalArgumentException("Target ticks per second cannot be less than 1.");
        }
        if (targetTicksPerSecond > 1000) {
            throw new IllegalArgumentException("Target ticks per second cannot exceed 1000.");
        }
        if (tickLengthMillis == 0) {
            throw new IllegalStateException("tickLengthMillis cannot be equal to zero!");
        }
        cantKeepUpTicks = 0;
        modeTickCount = 0;
        globalTickCount = 0;
        lastTickMillis = System.currentTimeMillis();
    }

    public void waitAndTick() {
        long currTime = System.currentTimeMillis();
        long diff = currTime - lastTickMillis;
        if (diff < 0) {
            throw new IllegalStateException("TIME SHOULDN'T GO BACKWARDS.");
        }
        diff = tickLengthMillis - diff;
        if (diff > 0) {
            cantKeepUpTicks = 0;
            try {
                Thread.sleep(diff);
            } catch (InterruptedException e) {
                //  Don't care
            }
        } else {
            if(cantKeepUpTicks % secondsToTicks(1F) == 0) {
                Robot.logger.severe("Can't keep up!");
            }
            cantKeepUpTicks++;
        }
        globalTickCount++;
        modeTickCount++;
        lastTickMillis = System.currentTimeMillis();
    }

    public void resetModeTickCount() {
        modeTickCount = 0;
    }

    public int getModeTickCount() {
        return modeTickCount;
    }
    
    public int getGlobalTickCount() {
        return globalTickCount;
    }

    public int getTargetTicksPerSecond() {
        return targetTicksPerSecond;
    }

    public int getTickLengthMillis() {
        return tickLengthMillis;
    }

    public float getTickLengthSeconds() {
        return tickLengthSeconds;
    }
    
    public int secondsToTicks(float sec) {
        return (int)(sec * targetTicksPerSecond);
    }

    public float ticksToSeconds(int ticks) {
        return tickLengthSeconds * ticks;
    }
    
    public int ticksToMillis(int ticks) {
        return tickLengthMillis * ticks;
    }
    
    public boolean hasTimeElapsedSinceTick(int tick, int ticksElapse) {
        return globalTickCount - tick >= ticksElapse;
    }
    
    public boolean hasModeTimeElapsed(int ticksElapse) {
        return modeTickCount >= ticksElapse;
    }
    
    public boolean hasModeTimeElapsedSinceTick(int tick, int ticksElapse) {
        return modeTickCount - tick >= ticksElapse;
    }
}
