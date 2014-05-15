package k12.revere.frc.s2014.systems;

/**
 *
 * @author Vince
 */
public interface SubSystem {
    
    public abstract void tick();
    
    public abstract void sendDebugInfo();
    
    public abstract void stopAll();
    
}
