package k12.revere.frc.s2014.util.logging;

/**
 *
 * @author Vince
 */
public class LoggerImpl implements Logger {

    private Level minLevel;
    private final String name;

    public LoggerImpl(String name) {
        this(Level.INFO, name);
    }

    public LoggerImpl(Level level, String name) {
        this.name = name;
        minLevel = level;
    }

    public void log(Level level, String message) {
        if (minLevel.compareTo(level) <= 0) {
            System.out.println(format(level, message));
        }
    }

    public void log(Level level, String pattern, Object[] args) {
        //  tl;dr no formatting right now.
        log(level, "DON'T USE LOG WITH ARGS! Pattern: " + pattern);
    }

    public void log(Level level, String message, Throwable t) {
        if (minLevel.compareTo(level) <= 0) {
            log(level, message + "\nException: " + t.getMessage());
            t.printStackTrace();
        }
    }

    private String format(Level level, String message) {
        return '[' + name + "] [" + level.toString() + "] @ " + System.currentTimeMillis() + ": " + message;
    }

    public void fatal(String message) {
        log(Level.FATAL, message);
    }

    public void fatal(String pattern, Object[] args) {
        log(Level.FATAL, pattern, args);
    }

    public void severe(String message) {
        log(Level.SEVERE, message);
    }

    public void severe(String pattern, Object[] args) {
        log(Level.SEVERE, pattern, args);
    }

    public void warning(String message) {
        log(Level.WARNING, message);
    }

    public void warning(String pattern, Object[] args) {
        log(Level.WARNING, pattern, args);
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void info(String pattern, Object[] args) {
        log(Level.INFO, pattern, args);
    }

    public void debug(String message) {
        log(Level.DEBUG, message);
    }

    public void debug(String pattern, Object[] args) {
        log(Level.DEBUG, pattern, args);
    }

    public void trace(String message) {
        log(Level.TRACE, message);
    }

    public void trace(String pattern, Object[] args) {
        log(Level.TRACE, pattern, args);
    }

    public void entering(String className, String method) {
        if (minLevel.compareTo(Level.TRACE) >= 0) {
            return;
        }
        log(Level.TRACE, "enter " + className + "." + method + "()");
    }

    public void exiting(String className, String method) {
        if (minLevel.compareTo(Level.TRACE) >= 0) {
            return;
        }
        log(Level.TRACE, "exit " + className + "." + method + "()");
    }

    public void throwing(Level level, String className, String methodName, Throwable t) throws Throwable {
        if (minLevel.compareTo(level) <= 0) {
            log(level, "Throwing " + className + "." + methodName, t);
        }
        throw t;
    }

    public void setLoggingLevel(Level level) {
        minLevel = level;
    }

    public Level getLoggingLevel() {
        return minLevel;
    }

}
