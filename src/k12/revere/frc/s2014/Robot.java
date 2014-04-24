package k12.revere.frc.s2014;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import k12.revere.frc.s2014.systems.ControlSystem;
import k12.revere.frc.s2014.systems.DriveSystem;
import k12.revere.frc.s2014.systems.SensorSystem;
import k12.revere.frc.s2014.systems.WinchSystem;
import k12.revere.frc.s2014.ticktasks.Auton2014Task;
import k12.revere.frc.s2014.ticktasks.DebugTickTask;
import k12.revere.frc.s2014.ticktasks.TickTask;
import k12.revere.frc.s2014.util.Ticker;
import k12.revere.frc.s2014.util.logging.Level;
import k12.revere.frc.s2014.util.logging.Logger;
import k12.revere.frc.s2014.util.logging.LoggerImpl;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as described in the SimpleRobot documentation. If you change the name of this class or the package after creating this project, you must also update the manifest file in the resource directory.
 */
public class Robot extends RobotBase {

    public static final Logger logger = new LoggerImpl("Revere");
    public static final String VERSION = "0.1.0 alpha";
    public static final float DEBUG_INTERVAL_SEC = 0.125F;

    public static final int MODE_INIT = -1;
    public static final int MODE_DISABLED = 0;
    public static final int MODE_AUTONOMOUS = 1;
    public static final int MODE_TELEOP = 2;
    public static final int MODE_TEST = 3;

    private static final int INIT_NUM_TICK_TASKS = 2;

    private final Ticker ticker;

    private ControlSystem controlSystem;
    private DriveSystem driveSystem;
    private WinchSystem winchSystem;
    private SensorSystem sensorSystem;

    private int lastReceivedPacketTick;
    private int currentMode;
    /**
     * An array of TickTasks that are currently active. YOU MUST MAINTAIN YOURSELF WHICH SLOT IS IN USE OR
     */
    private TickTask[] tickTasks;

    public Robot() {
        super();
        ticker = new Ticker(50);
        logger.setLoggingLevel(Level.ALL);
        currentMode = MODE_INIT;
        lastReceivedPacketTick = 0;
        tickTasks = new TickTask[INIT_NUM_TICK_TASKS];
    }

    public void startCompetition() {
        logger.entering("Robot", "startCompetition");
        UsageReporting.report(UsageReporting.kResourceType_Framework, UsageReporting.kFramework_Simple);

        // first and one-time initialization
        LiveWindow.setEnabled(false);
        init();

        while (true) {
            if (isDisabled()) {
                if (currentMode != MODE_DISABLED) {
                    currentMode = MODE_DISABLED;
                    robotIdleInit();
                }
                robotIdleTick();
            } else if (isAutonomous()) {
                if (currentMode != MODE_AUTONOMOUS) {
                    currentMode = MODE_AUTONOMOUS;
                    robotAutonomousInit();
                }
                robotAutonomousTick();
            } else if (isOperatorControl()) {
                if (currentMode != MODE_TELEOP) {
                    currentMode = MODE_TELEOP;
                    robotTeleopInit();
                }
                robotTeleopTick();
            } else if (isTest()) {
                if (currentMode != MODE_TEST) {
                    currentMode = MODE_TEST;
                    robotTestInit();
                }
                robotTestTick();
            }
            onTick();

            //  Dummy code to keep compiler happy
            if (false == true) {
                logger.fatal("YOU'RE NOT SUPPOSED TO DO THAT!");
                break;
            }
        }
        logger.exiting("Robot", "startCompetition");
        logger.fatal("Exited main loop!");
    }

    /**
     * Performs robot power-on startup.
     */
    public void init() {
        logger.entering("Robot", "init");
        //  Versioning
        logger.log(Level.ALL, "Starting Revere Robot 2014");
        logger.log(Level.ALL, "Version " + VERSION);
        logger.log(Level.ALL, "Logging level set to " + logger.getLoggingLevel().toString());
        logger.log(Level.ALL, "Debug interval set to " + (DEBUG_INTERVAL_SEC * 1000F) + " milliseconds.");

        //  System init
        driveSystem = new DriveSystem();
        winchSystem = new WinchSystem();
        sensorSystem = new SensorSystem();
        controlSystem = new ControlSystem(driveSystem, winchSystem);

        //  Debug init
        addTickTask(new DebugTickTask(this, ticker.secondsToTicks(DEBUG_INTERVAL_SEC)));

        logger.exiting("Robot", "init");
    }

    /**
     * Initializes the robot for idle/disabled mode.
     */
    public void robotIdleInit() {
        logger.entering("Robot", "robotIdleInit");
        stopAllSystems();
        sensorSystem.calibrate();
        logger.exiting("Robot", "robotIdleInit");
    }

    /**
     * Run a single tick during idle/disabled mode.
     */
    public void robotIdleTick() {

    }

    /**
     * Initializes the robot for autonomous mode.
     */
    public void robotAutonomousInit() {
        logger.entering("Robot", "robotAutonomousInit");
        stopAllSystems();
        sensorSystem.calibrate();
        //  Set task
        addTickTask(new Auton2014Task(this, 1.0D, 1.0D, ticker.secondsToTicks(5.0F)));
        logger.exiting("Robot", "robotAutonomousInit");
    }

    /**
     * Runs a single tick during autonomous mode.
     */
    public void robotAutonomousTick() {
        //  AutonTask is running
    }

    /**
     * Initializes the robot for teleop mode.
     */
    public void robotTeleopInit() {
        logger.entering("Robot", "robotTeleopInit");
        stopAllSystems();
        sensorSystem.calibrate();
        logger.exiting("Robot", "robotTeleopInit");
    }

    /**
     * Runs a single tick during teleop mode.
     */
    public void robotTeleopTick() {
        controlSystem.teleopRobot();
    }

    /**
     * Initializes the robot for test mode.
     */
    public void robotTestInit() {
        logger.entering("Robot", "robotTestInit");
        stopAllSystems();
        sensorSystem.calibrate();
        logger.exiting("Robot", "robotTestInit");
    }

    /**
     * Runs a single tick during test mode.
     */
    public void robotTestTick() {

    }

    /**
     * Runs a single tick. Any operations that don't depend on what mode the robot is in should go here.
     */
    private void onTick() {
        for (int i = 0; i < tickTasks.length; i++) {
            TickTask tickTask = tickTasks[i];
            if (tickTask == null) {
                continue;
            }
            if (tickTask.isDone()) {
                tickTasks[i] = null;
                continue;
            }
            tickTask.runTick();
        }

        ticker.waitAndTick();

        int tickDiff = ticker.getGlobalTickCount() - lastReceivedPacketTick;
        if (m_ds.isNewControlData()) {
            lastReceivedPacketTick = ticker.getGlobalTickCount();
        } else if (tickDiff > ticker.secondsToTicks(0.5F) && tickDiff % ticker.secondsToTicks(2F) == 0) {
            //  Warn once every two seconds if we don't get a packet for half a second or more.
            logger.warning("No new data packet for " + ticker.ticksToSeconds(tickDiff) + " seconds.");
        }
    }

    /**
     * Adds a task to the running tasks and returns the slot number that it was inserted into (so that the task may be retrieved later).
     *
     * @param task
     * @return
     */
    public int addTickTask(TickTask task) {
        //  Find an open slot
        for (int i = 0; i < tickTasks.length; i++) {
            TickTask tickTask = tickTasks[i];
            if (tickTask == null || tickTask.isDone()) {
                tickTasks[i] = task;
                return i;
            }
        }
        //  No open slot, time to expand
        int newSize = tickTasks.length + 4;
        TickTask[] newArray = new TickTask[newSize];
        System.arraycopy(tickTasks, 0, newArray, 0, tickTasks.length);
        tickTasks = newArray;
        //  Try to add again
        return addTickTask(task);
    }

    public void stopAllSystems() {
        driveSystem.stopAll();
        winchSystem.stopAll();
        sensorSystem.stopAll();
        controlSystem.stopAll();
    }

    public ControlSystem getControlSystem() {
        return controlSystem;
    }

    public DriveSystem getDriveSystem() {
        return driveSystem;
    }

    public SensorSystem getSensorSystem() {
        return sensorSystem;
    }

    public WinchSystem getWinchSystem() {
        return winchSystem;
    }

    public int getCurrentMode() {
        return currentMode;
    }

    public DriverStation getDriverStation() {
        return m_ds;
    }

    public Ticker getTicker() {
        return ticker;
    }

}
