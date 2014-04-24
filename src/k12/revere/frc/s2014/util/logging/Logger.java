package k12.revere.frc.s2014.util.logging;

/**
 * 
 * @author Vince
 */
public interface Logger {
    
    public void log(Level level, String message);
    
    public void log(Level level, String message, Throwable t);
    
    public void log(Level level, String pattern, Object[] args);
    
    public void fatal(String message);
    
    public void fatal(String pattern, Object[] args);
    
    public void severe(String message);
    
    public void severe(String pattern, Object[] args);
    
    public void warning(String message);
    
    public void warning(String pattern, Object[] args);
    
    public void info(String message);
    
    public void info(String pattern, Object[] args);
    
    public void debug(String message);
    
    public void debug(String pattern, Object[] args);
    
    public void trace(String message);
    
    public void trace(String pattern, Object[] args);
    
    public void entering(String className, String method);
    
    public void exiting(String className, String method);
    
    public void throwing(Level level, String className, String methodName, Throwable t) throws Throwable;
    
    public void setLoggingLevel(Level level);
    
    public Level getLoggingLevel();
    
}
