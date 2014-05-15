package k12.revere.frc.s2014.util.logging;

/**
 *
 * @author Vince
 */
public class Level {

    public static final Level ALL       = new Level(Integer.MIN_VALUE,  "ALL");
    public static final Level FATAL     = new Level(1000,               "FATAL");
    public static final Level SEVERE    = new Level(800,                "SEVERE");
    public static final Level WARNING   = new Level(700,                "WARNING");
    public static final Level INFO      = new Level(500,                "INFO");
    public static final Level DEBUG     = new Level(400,                "DEBUG");
    public static final Level TRACE     = new Level(100,                "TRACE");
    public static final Level OFF       = new Level(Integer.MAX_VALUE,  "OFF");
    
    private final int level;
    private final String levelName;
    
    public Level(int l, String s) {
        level = l;
        levelName = s;
    }
    
    public int intValue() {
        return level;
    }

    public String toString() {
        return levelName;
    }
    
    /**
     * Returns -, 0, or + if this Level is lower priority, same priority, or higher priority than the argument,
     * @param other
     * @return 
     */
    public int compareTo(Level other) {
        //  Comparing to self; use reference
        if(this == other) {
            return 0;
        }
        //  Compare intValue
        return this.intValue() - other.intValue();
    }
    
}
