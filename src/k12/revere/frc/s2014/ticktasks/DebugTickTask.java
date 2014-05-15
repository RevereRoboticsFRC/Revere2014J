package k12.revere.frc.s2014.ticktasks;

import k12.revere.frc.s2014.Robot;

/**
 *
 * @author Vince
 */
public class DebugTickTask extends TickTask {

    private final Robot robot;
    private final int interval;
    
    public DebugTickTask(Robot r, int interval) {
        super(r.getTicker());
        robot = r;
        if(interval == 0) {
            throw new IllegalArgumentException("Debug interval cannot be zero. Set the interval to some positive value, or a negative value to disable.");
        }
        this.interval = interval;
    }

    public void runTick() {
        if(interval < 0) {
            return;
        }
        if(getElapsedModeTicks() % interval == 0) {
            robot.getControlSystem().sendDebugInfo();
            robot.getDriveSystem().sendDebugInfo();
            robot.getSensorSystem().sendDebugInfo();
            robot.getWinchSystem().sendDebugInfo();
        }
    }

    protected void forciblyTerminate() {
        //  Debug task cannot be forcibly terminated.
        Robot.logger.warning("YOU CAN'T STOP MEH, I'M DEBUG!");
    }

    public String getName() {
        return "Debug@" + interval;
    }
    
    

}
