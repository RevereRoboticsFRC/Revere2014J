package k12.revere.frc.s2014.ticktasks;

import k12.revere.frc.s2014.systems.WinchSystem;
import k12.revere.frc.s2014.util.Ticker;

/**
 *
 * @author Vince
 */
public class WinchOperationTask extends TickTask {

    public static final double UP_SPEED = 1.0D;
    public static final double DOWN_SPEED = -1.0D;
    
    private final WinchSystem winchSystem;
    private final boolean up;
    private final int stepTicks;
    private final String id;
    
    private WinchOperationTask(Ticker ticker, WinchSystem system, boolean goUp, int stepSize, String s) {
        super(ticker);
        winchSystem = system;
        up = goUp;
        stepTicks = stepSize;
        id = s;
    }

    public void runTick() {
        //  If we're done, don't do anything.
        if(isDone()) {
            return;
        }
        //  Someone asked us nicely to stop, so let's stop.
        if(terminationRequested()) {
            finish();
            return;
        }
        //  Time's up.
        if(getElapsedGlobalTicks() > stepTicks) {
            finish();
            return;
        }
        //  Going up.
        if(up) {
            //  High switch triggered when going up
            if(winchSystem.isHighSwitchTripped()) {
                finish();
                return;
            }
            //   Go up
            winchSystem.setSpeed(UP_SPEED);
        } else //   Going down.
        {
            //  Low switch triggered when going down
            if(winchSystem.isLowSwitchTripped()) {
                finish();
                return;
            }
            //  Go down
            winchSystem.setSpeed(DOWN_SPEED);
        }
    }

    protected void forciblyTerminate() {
        finish();
    }
    
    private void finish() {
        setDone();
        winchSystem.stopAll();
    }

    public String getName() {
        return "WinchOp@" + id;
    }
    
    public static WinchOperationTask winchUpFully(Ticker ticker1, WinchSystem winchSystem1) {
        //  Going up, keep going until switch triggered (MAX_VALUE ticks would take quite a long time to reach).
        return new WinchOperationTask(ticker1, winchSystem1, true, Integer.MAX_VALUE, "fullUp");
    }
    
    public static WinchOperationTask winchDownFully(Ticker ticker1, WinchSystem winchSystem1) {
        //  Going down, keep going until switch triggered (MAX_VALUE ticks would take quite a long time to reach).
        return new WinchOperationTask(ticker1, winchSystem1, false, Integer.MAX_VALUE, "fullDown");
    }

    public static WinchOperationTask winchUpStep(Ticker ticker1, WinchSystem winchSystem1) {
        //  Going up, go for 1.0 second.
        return new WinchOperationTask(ticker1, winchSystem1, true, ticker1.secondsToTicks(1.0F), "stepUp");
    }
    
    public static WinchOperationTask winchDownStep(Ticker ticker1, WinchSystem winchSystem1) {
        //  Going down, go for 1.0 second.
        return new WinchOperationTask(ticker1, winchSystem1, false, ticker1.secondsToTicks(1.0F), "stepDown");
    }
    
}
