package k12.revere.frc.s2014.ticktasks;

import k12.revere.frc.s2014.util.Ticker;

/**
 *
 * @author Vince
 */
public abstract class TickTask {

    protected final Ticker ticker;
    protected final int startingGlobalTick;
    protected final int startingModeTick;
    private boolean done;
    private boolean terminationRequested;
    
    public TickTask(Ticker ticker) {
        this.ticker = ticker;
        startingGlobalTick = ticker.getGlobalTickCount();
        startingModeTick = ticker.getModeTickCount();
        done = false;
    }
    
    /**
     * Perform on-tick actions. Called once per robot tick.
     */
    public abstract void runTick();
    
    /**
     * Gets the name of this TickTask. Default implementation returns Object.toString() for this task.
     * @return The name of this TickTask.
    */
    public String getName() {
        return toString();
    }
    
    /**
     * Gets the number of global ticks that have elapsed since this TickTask was created. Values can be expected to be zero or positive, never negative.
     * @return The number of global ticks that have elapsed.
     */
    public final int getElapsedGlobalTicks() {
        return ticker.getGlobalTickCount() - startingGlobalTick;
    }
    
    /**
     * Gets the number of mode ticks that have elapsed since this TickTask was created. Values can be expected to be zero or positive if the robot has not changed mode; if the robot has changed mode the result is undefined.
     * @return The number of mode ticks that have elapsed, or an undefined value if the robot has changed modes since the creation of this TickTask.
     */
    public final int getElapsedModeTicks() {
        //  NOTE! Can return negative values if the robot mode is changed, since the following can occur:
        //  Mode A: Create task @ mode tick #400
        //  ...tick tick tick...
        //  CHANGE MODE: Mode B, mode tick # is now 0
        //  0 - 400 = -400
        return ticker.getModeTickCount() - startingModeTick;
    }

    /**
     * Returns whether or not this TickTask has completed or not. This is primarily for signalling to the TickTask pool manager that this TickTask may be removed or replaced safely.
     * @return Whether or not this TickTask has finished and can be disposed of.
     */
    public final boolean isDone() {
        return done;
    }

    /**
     * Sets this TickTask as completed. Only classes that extend TickTask should call this function, as completion implies that all cleanup activity (such as stopping motors) have already occurred.
     * Others may call <code>requestTermination()</code> to signal to the TickTask that it should clean up and stop, or <code>forceTermination()</code> to have the TickTask immediately stop and clean up.
     */
    protected final void setDone() {
        done = true;
    }
    
    /**
     * Requests that this TickTask should stop as soon as possible and clean up. Note that it may take at most one more tick (or another call to <code>runTick()</code>) for the TickTask to perform cleanup.
     * It is highly recommended that you call <code>isDone()</code> to verify that the TickerTask has indeed finished before disposing.
     */
    public final void requestTermination() {
        terminationRequested = true;
    }
    
    protected final boolean terminationRequested() {
        return terminationRequested;
    }
    
    /**
     * Forcibly terminates this TickTask. It is preferable to call <code>requestTermination()</code> as the TickerTask may handle it more gracefully.
     */
    public final void forceTermination() {
        requestTermination();
        forciblyTerminate();
    }
    
    /**
     * Called when the TickTask is forcibly terminated. Cleanup should occur here, preferably by simply calling an internal <code>cleanup()</code> function or the like.
     */
    protected abstract void forciblyTerminate();
}
