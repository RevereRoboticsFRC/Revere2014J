package k12.revere.frc.s2014.ticktasks;

import k12.revere.frc.s2014.Robot;

/**
 *
 * @author Vince
 */
public class Auton2014Task extends TickTask {

    private final Robot robot;
    private double left;
    private double right;
    private int ticks;
    
    public Auton2014Task(Robot rob, double l, double r, int ticks) {
        super(rob.getTicker());
        robot = rob;
        left = l;
        right = r;
        this.ticks = ticks;
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
        if(getElapsedGlobalTicks() > ticks) {
            finish();
            return;
        }
        if(robot.getCurrentMode() != Robot.MODE_AUTONOMOUS) {
            finish();
            return;
        }
        robot.getDriveSystem().setSpeed(left, right);
    }
    
    private void finish() {
        setDone();
        robot.getDriveSystem().stopAll();
    }

    protected void forciblyTerminate() {
        finish();
    }

    public String getName() {
        return "Auton2014";
    }

    
}
